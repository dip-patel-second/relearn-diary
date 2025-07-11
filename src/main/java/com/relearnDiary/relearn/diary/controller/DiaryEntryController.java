package com.relearnDiary.relearn.diary.controller;


import com.relearnDiary.relearn.diary.entity.DiaryEntry;
import com.relearnDiary.relearn.diary.entity.User;
import com.relearnDiary.relearn.diary.service.DiaryEntryService;
import com.relearnDiary.relearn.diary.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RestController
@RequestMapping("/diary")
public class DiaryEntryController {


    @Autowired
    private DiaryEntryService diaryEntryService;

    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<?> getAllDiaryEntriesOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<DiaryEntry> all = user.getDiaryEntries();
        if (all != null && !all.isEmpty()){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody DiaryEntry myEntry){
       try {
           Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
           String userName = authentication.getName();
           myEntry.setDate(LocalDateTime.now());
           diaryEntryService.saveEntry(myEntry,userName);
           return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
       }catch (Exception e){
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<?> getDiaryEntryById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<DiaryEntry> collect = user.getDiaryEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if (!collect.isEmpty()){
            Optional<DiaryEntry> diaryEntry = diaryEntryService.findById(myId);
            if (diaryEntry.isPresent()){
                return new ResponseEntity<>(diaryEntry.get(),HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteDiaryEntryById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean removed = diaryEntryService.deleteById(myId, userName);
        if (removed){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("id/{myId}")
    public ResponseEntity<?> updateDiaryEntryById(@PathVariable ObjectId myId, @RequestBody DiaryEntry newEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<DiaryEntry> collect = user.getDiaryEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if (!collect.isEmpty()){
            Optional<DiaryEntry> diaryEntry = diaryEntryService.findById(myId);
            if (diaryEntry.isPresent()){
                DiaryEntry old=diaryEntry.get();
                old.setTitle(newEntry.getTitle() !=null && !newEntry.getTitle().equals(" ")? newEntry.getTitle(): old.getTitle());
                old.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals(" ")? newEntry.getContent():old.getContent());
                diaryEntryService.saveEntry(old);
                return new ResponseEntity<>(old,HttpStatus.OK);
            }
        }




        DiaryEntry old = diaryEntryService.findById(myId).orElse(null);
        if (old!=null){


        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

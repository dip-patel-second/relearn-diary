package com.relearnDiary.relearn.diary.service;

import com.relearnDiary.relearn.diary.entity.DiaryEntry;
import com.relearnDiary.relearn.diary.entity.User;
import com.relearnDiary.relearn.diary.diaryEntryRepo.DiaryEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DiaryEntryService {


    @Autowired
    private DiaryEntryRepository diaryEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(DiaryEntry diaryEntry, String userName){
        try {
            User user = userService.findByUserName(userName);
            diaryEntry.setDate(LocalDateTime.now());
            DiaryEntry saved = diaryEntryRepository.save(diaryEntry);
            user.getDiaryEntries().add(saved);
            userService.saveUser(user);
        }catch (Exception e){
            throw new RuntimeException("an error ocuured while saving the entry");
        }

    }
    public void saveEntry(DiaryEntry diaryEntry){
      diaryEntryRepository.save(diaryEntry);

    }

    public List<DiaryEntry > getAll(){
        return diaryEntryRepository.findAll();
    }

    public Optional <DiaryEntry> findById(ObjectId id){
        return diaryEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String userName){
        boolean removed=false;
        try {
            User user=userService.findByUserName(userName);
             removed = user.getDiaryEntries().removeIf(x -> x.getId().equals(id));
            if (removed){
                userService.saveUser(user );
                diaryEntryRepository.deleteById(id);
            }
        }catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("An error occurred while deleting the entry ",e);
        }
        return removed;
    }

    public List<DiaryEntry> findByUserName(String userName){
        return new ArrayList<>();
    }
}

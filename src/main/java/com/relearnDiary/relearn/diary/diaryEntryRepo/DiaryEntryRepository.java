package com.relearnDiary.relearn.diary.diaryEntryRepo;

import com.relearnDiary.relearn.diary.entity.DiaryEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface DiaryEntryRepository extends MongoRepository<DiaryEntry,ObjectId > {

}

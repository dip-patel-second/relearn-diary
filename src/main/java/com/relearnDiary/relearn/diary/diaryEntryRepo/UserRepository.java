package com.relearnDiary.relearn.diary.diaryEntryRepo;

import com.relearnDiary.relearn.diary.entity.DiaryEntry;
import com.relearnDiary.relearn.diary.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepository extends MongoRepository<User,ObjectId > {

    User findByUserName(String userName);

    void deleteByUserName(String userName);
}

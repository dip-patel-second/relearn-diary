package com.relearnDiary.relearn.diary.entity;


import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Data
@Document(collection = "diary_entries")
public class DiaryEntry {

    @Id
    private ObjectId id;
    private String title;
    private String content;
    private LocalDateTime date;

}

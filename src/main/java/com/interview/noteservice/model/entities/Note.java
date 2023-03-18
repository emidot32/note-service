package com.interview.noteservice.model.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Document(collection = "notes")
public class Note {
    @Id
    private String id;

    private String value;

    private Integer likes;

    // Need for correct sorting
    @Field("created_at")
    private LocalDateTime createdAt;

    public Note(String value, Integer likes) {
        this.value = value;
        this.likes = likes;
        this.createdAt = LocalDateTime.now();
    }
}

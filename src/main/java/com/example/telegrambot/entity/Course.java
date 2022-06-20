package com.example.telegrambot.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String courseName;
    private String fileId;
    private String courseDefinition;


    public Course(String courseName, String fileId, String courseDefinition) {
        this.courseName = courseName;
        this.fileId = fileId;
        this.courseDefinition = courseDefinition;
    }
}

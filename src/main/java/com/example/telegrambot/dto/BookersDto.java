package com.example.telegrambot.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BookersDto {
    private Long student_id;
    private String fullName;
    private List<String> courses;
}

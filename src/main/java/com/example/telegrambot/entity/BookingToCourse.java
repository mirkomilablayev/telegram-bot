package com.example.telegrambot.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BookingToCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User student;
    @ManyToOne
    private Course course;
    private LocalDateTime bookedAt;
    private boolean isResponsed = false;
}

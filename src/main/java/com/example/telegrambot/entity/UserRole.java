package com.example.telegrambot.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserRole {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    public UserRole(String name){
        this.name = name;
    }
}

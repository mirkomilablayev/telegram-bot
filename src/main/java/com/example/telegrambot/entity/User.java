package com.example.telegrambot.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fistName;
    private String lastName;
    private String chatId;
    private String realId;
    private String phoneNumber = " ";
    private String status;
    @ManyToMany
    private List<UserRole>userRoles;
    private Boolean isUser = true;
    
}

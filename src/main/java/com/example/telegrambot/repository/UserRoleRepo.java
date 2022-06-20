package com.example.telegrambot.repository;

import com.example.telegrambot.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepo extends JpaRepository<UserRole,Long> {
    Optional<UserRole> findByName(String name);
}

package com.example.telegrambot.repository;

import com.example.telegrambot.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepo extends JpaRepository<Link,Long> {
}

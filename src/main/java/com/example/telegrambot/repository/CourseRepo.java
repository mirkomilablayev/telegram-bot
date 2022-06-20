package com.example.telegrambot.repository;

import com.example.telegrambot.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepo extends JpaRepository<Course, Long> {
    Optional<Course> findByCourseName(String courseName);
}

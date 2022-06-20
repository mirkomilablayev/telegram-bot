package com.example.telegrambot.repository;

import com.example.telegrambot.entity.BookingToCourse;
import com.example.telegrambot.entity.Course;
import com.example.telegrambot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingToCourseRepo extends JpaRepository<BookingToCourse,Long> {
Boolean existsByStudentAndCourse(User student, Course course);
List<BookingToCourse> findAllByCourse(Course course);
int countAllByCourse(Course course);
}

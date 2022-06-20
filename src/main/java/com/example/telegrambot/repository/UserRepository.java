package com.example.telegrambot.repository;

import com.example.telegrambot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByChatId(String chatId);

    @Query(nativeQuery = true, value = "select count(*) from users st where st.id not in (select btc.student_id from booking_to_course btc)")
    int getNonFollowersCount();
}

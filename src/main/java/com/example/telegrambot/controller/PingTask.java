package com.example.telegrambot.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@Slf4j
@Setter
@Getter
public class PingTask {

    @Value("${pingtask.url}")
    private String url1;

    @Scheduled(fixedRate = 1000 * 50)
    public void pingMe() {
        System.out.println(url1);
        try {
            URL url = new URL("https://www.google.com");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            log.info("Ping {},OK: response code{}", url.getHost(), connection.getResponseCode());
            connection.disconnect();
        } catch (IOException e) {
            log.error("Ping FAILED");
            e.printStackTrace();
        }
    }
}

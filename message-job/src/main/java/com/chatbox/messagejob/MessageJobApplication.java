package com.chatbox.messagejob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.chatbox.messagejob", "com.chatbox.publisher"})
public class MessageJobApplication {
    public static void main(String[] args) {
        SpringApplication.run(MessageJobApplication.class, args);
    }
}
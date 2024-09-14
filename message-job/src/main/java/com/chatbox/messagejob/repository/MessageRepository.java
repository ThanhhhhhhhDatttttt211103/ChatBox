package com.chatbox.messagejob.repository;

import com.chatbox.messagejob.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}

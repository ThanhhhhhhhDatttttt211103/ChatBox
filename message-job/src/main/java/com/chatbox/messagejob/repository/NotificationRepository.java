package com.chatbox.messagejob.repository;

import com.chatbox.messagejob.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}

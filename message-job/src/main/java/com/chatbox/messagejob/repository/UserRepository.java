package com.chatbox.messagejob.repository;

import com.chatbox.messagejob.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    public User findUserById(String id);
}

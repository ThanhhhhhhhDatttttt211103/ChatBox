package com.chatbox.repository;

import com.chatbox.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findUserById(String id);
}

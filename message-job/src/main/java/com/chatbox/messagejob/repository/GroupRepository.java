package com.chatbox.messagejob.repository;

import com.chatbox.messagejob.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, String> {
    public Group findGroupById(String id);
}

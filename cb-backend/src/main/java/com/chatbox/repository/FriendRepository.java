package com.chatbox.repository;

import com.chatbox.entity.Friend;
import com.chatbox.entity.component.FriendId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, FriendId> {
    List<Friend> findFriendByIdUser(String idUser);
}

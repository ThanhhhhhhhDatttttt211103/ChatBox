package com.chatbox.service;

import com.chatbox.dto.FriendDto;
import com.chatbox.dto.component.FriendRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IFriendService {
    ResponseEntity<List<FriendDto>> findFriendByIdUser(String id);

    List<FriendDto> save(FriendRequest friendRequest);
}

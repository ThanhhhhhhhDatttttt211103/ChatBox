package com.chatbox.controller;

import com.chatbox.dto.FriendDto;
import com.chatbox.dto.component.FriendRequest;
import com.chatbox.service.impl.FriendService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/friend")
@AllArgsConstructor
@Slf4j
public class FriendController {

    private FriendService friendService;

    @GetMapping("/{idUser}")
    public ResponseEntity<List<FriendDto>> getFriendsByIdUser(@PathVariable String idUser) {
        return friendService.findFriendByIdUser(idUser);
    }

    @PostMapping("/add-friend")
    public ResponseEntity<List<FriendDto>> addFriend(@RequestBody FriendRequest friendRequest) {
        log.info("Begin addFriend API with friendRequest: {}", friendRequest);
        long startTime = System.currentTimeMillis();
        List<FriendDto> listFriendSaved = friendService.save(friendRequest);
        log.info("End addFriend API with count save = {} in {} ms", listFriendSaved.size(), System.currentTimeMillis() - startTime);
        return ResponseEntity.ok(listFriendSaved);
    }
}

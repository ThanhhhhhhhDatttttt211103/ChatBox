package com.chatbox.controller;

import com.chatbox.dto.component.ChatRoom;
import com.chatbox.mapper.ChatRoomMapper;
import com.chatbox.service.impl.ChatRoomService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/chatRoom")
@AllArgsConstructor
@Slf4j
public class ChatRoomController {
    private ChatRoomService chatRoomService;

    @GetMapping("/getAll/{idUser}")
    public ResponseEntity<List<ChatRoom>> listChatRoom(@PathVariable String idUser) {
        log.info("Begin listChatRoom API with idUser = {}", idUser);
        long startTime = System.currentTimeMillis();
        List<com.chatbox.entity.ChatRoom> chatRooms = chatRoomService.listChatRooms(idUser);
        log.info("End listChatroom API in {} ms", System.currentTimeMillis() - startTime);
        return ResponseEntity.ok(chatRooms.stream().map(ChatRoomMapper::mapToChatRoom).toList());
    }

    @PostMapping("/getListFriend")
    public ResponseEntity<List<ChatRoom>> listFriend(@RequestParam("idUser") String idUser, @RequestParam("name") String name) {
        log.info("Begin listFriend API with idUser = {} and name = {}", idUser, name);
        long startTime = System.currentTimeMillis();
        List<com.chatbox.entity.ChatRoom> chatRooms = chatRoomService.listFriend(idUser, name);
        log.info("End listFriend API in {} ms", System.currentTimeMillis() - startTime);
        return ResponseEntity.ok(chatRooms.stream().map(ChatRoomMapper::mapToChatRoom).toList());
    }

    @PostMapping("/createChatRoomUI")
    public ResponseEntity<ChatRoom> createChatRoomUI(@RequestParam("idUser") String idUser, @RequestParam("idFriend") String idFriend) {
        log.info("Begin createChatRoomUI API with idUser = {} and idFriend = {}", idUser, idFriend);
        long startTime = System.currentTimeMillis();
        com.chatbox.entity.ChatRoom chatRoom = chatRoomService.createChatRoomUI(idUser, idFriend);
        log.info("End createChatRoomUI API in {} ms", System.currentTimeMillis() - startTime);
        return ResponseEntity.ok(ChatRoomMapper.mapToChatRoom(chatRoom));
    }
}
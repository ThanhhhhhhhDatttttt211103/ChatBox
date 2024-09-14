package com.chatbox.controller;

import com.chatbox.dto.UserDto;
import com.chatbox.dto.component.Profile;
import com.chatbox.entity.ProfileCore;
import com.chatbox.entity.UserCore;
import com.chatbox.mapper.ProfileMapper;
import com.chatbox.mapper.UserMapper;
import com.chatbox.service.impl.UserCoreService;
import com.chatbox.service.impl.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final UserCoreService userCoreService;

    @MessageMapping("/user.connectUser")
    public void connectUser(@Payload UserDto user) {
        userService.connectUser(user);
    }

    @MessageMapping("/user.disconnectUser")
    public void disconnectUser(@Payload UserDto user) {
        userService.disconnectUser(user);
    }

    @GetMapping("/find/{idUser}")
    public ResponseEntity<UserDto> findUserById(@PathVariable String idUser) {
        log.info("Begin findUserById with idUser: {}", idUser);
        long startTime = System.currentTimeMillis();
        UserDto userDto = userService.findUserById(idUser);
        log.info("End findUserById SUCCESS with {} in {} ms", userDto, System.currentTimeMillis() - startTime);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/search-user-add")
    public ResponseEntity<List<UserDto>> searchUserAdd(@RequestParam("idUser") String idUser, @RequestParam("idGroup") String idGroup, @RequestParam("name") String name) {
        log.info("Begin searchUserAdd with idUser = {}, idGroup = {}, name = {}", idUser, idGroup, name);
        long startTime = System.currentTimeMillis();
        List<UserCore> listUserCore = userCoreService.searchUserAddGroup(idUser, idGroup, name);
        List<UserDto> listUserDto = listUserCore.stream().map(UserMapper::mapToUserDto).toList();
        log.info("End searchUserAdd with count user = {} in {} ms", listUserCore.size(), System.currentTimeMillis() - startTime);
        return ResponseEntity.ok(listUserDto);
    }

    @PostMapping("/search-member-group")
    public ResponseEntity<List<UserDto>> searchMemberGroup(@RequestParam("idGroup") String idGroup) {
        log.info("Begin searchMemberGroup with idGroup = {}", idGroup);
        long startTime = System.currentTimeMillis();
        List<UserCore> listUserCore = userCoreService.searchMemberGroup(idGroup);
        List<UserDto> listUserDto = listUserCore.stream().map(UserMapper::mapToUserDto).toList();
        log.info("End searchMemberGroup with count user = {} in {} ms", listUserCore.size(), System.currentTimeMillis() - startTime);
        return ResponseEntity.ok(listUserDto);
    }

    @PostMapping("/get-profile")
    public ResponseEntity<Profile> getProfile(@RequestParam("idUser") String idUser, @RequestParam("idFriend") String idFriend) {
        log.info("Begin getProfile with idUser = {}, idFriend = {}", idUser, idFriend);
        long startTime = System.currentTimeMillis();
        ProfileCore profileCore = userCoreService.getProfile(idUser, idFriend);
        log.info("End getProfile with {} in {} ms", profileCore, System.currentTimeMillis() - startTime);
        return ResponseEntity.ok(ProfileMapper.mapToProfile(profileCore));
    }
}
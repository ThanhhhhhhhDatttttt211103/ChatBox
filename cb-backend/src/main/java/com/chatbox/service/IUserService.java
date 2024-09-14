package com.chatbox.service;

import com.chatbox.dto.UserDto;

public interface IUserService {
    UserDto findUserById(String idUser);

    void connectUser(UserDto userDto);

    void disconnectUser(UserDto userDto);
}
package com.chatbox.mapper;

import com.chatbox.dto.UserDto;
import com.chatbox.entity.User;
import com.chatbox.entity.UserCore;

public class UserMapper {
    public static UserDto mapToUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFullname(),
                user.getStatus()
        );
    }

    public static User mapToUser(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getFullname(),
                userDto.getStatus()
        );
    }

    public static UserDto mapToUserDto(UserCore user) {
        return new UserDto(
                user.getId(),
                user.getFullname(),
                user.getStatus()
        );
    }

}

package com.chatbox.messagejob.mapper;

import com.chatbox.messagejob.dto.UserDto;
import com.chatbox.messagejob.entity.User;

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
}

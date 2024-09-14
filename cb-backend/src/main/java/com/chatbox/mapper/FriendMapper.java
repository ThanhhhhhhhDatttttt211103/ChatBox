package com.chatbox.mapper;

import com.chatbox.dto.FriendDto;
import com.chatbox.entity.Friend;

public class FriendMapper {
    public static FriendDto mapToFriendDto(Friend friend) {
        return new FriendDto(
                friend.getIdUser(),
                friend.getFriend(),
                friend.getCode(),
                friend.getIsEnable()
        );
    }

    public static Friend mapToFriend(FriendDto friendDto) {
        return new Friend(
                friendDto.getIdUser(),
                friendDto.getFriend(),
                friendDto.getCode(),
                friendDto.getIsEnable()
        );
    }
}

package com.chatbox.mapper;

import com.chatbox.dto.GroupDto;
import com.chatbox.entity.Group;

public class GroupMapper {
    public static GroupDto mapToGroupDto(Group group) {
        return new GroupDto(
                group.getId(),
                group.getGroupName()
        );
    }

    public static Group mapToGroup(GroupDto groupDto) {
        return new Group(
                groupDto.getId(),
                groupDto.getGroupName()
        );
    }
}

package com.chatbox.mapper;

import com.chatbox.dto.GroupDetailDto;
import com.chatbox.entity.GroupDetail;

public class GroupDetailMapper {
    public static GroupDetailDto mapToGroupDetailDto(GroupDetail groupDetail) {
        return new GroupDetailDto(
                groupDetail.getIdGroup(),
                groupDetail.getIdUser(),
                groupDetail.getIsEnable()
        );
    }

    public static GroupDetail mapToGroupDetail(GroupDetailDto groupDetailDto) {
        return new GroupDetail(
                groupDetailDto.getIdGroup(),
                groupDetailDto.getIdUser(),
                groupDetailDto.getIsEnable()
        );
    }
}

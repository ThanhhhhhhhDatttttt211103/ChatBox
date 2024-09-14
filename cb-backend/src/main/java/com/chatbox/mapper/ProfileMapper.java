package com.chatbox.mapper;

import com.chatbox.dto.component.Profile;
import com.chatbox.entity.ProfileCore;

public class ProfileMapper {
    public static Profile mapToProfile(ProfileCore profileCore) {
        return Profile.builder()
                .id(profileCore.getId())
                .fullName(profileCore.getFullName())
                .status(profileCore.getStatus())
                .isFriend(profileCore.getIsFriend())
                .code(profileCore.getCode())
                .build();
    }
}

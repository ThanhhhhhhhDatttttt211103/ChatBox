package com.chatbox.mapper;

import com.chatbox.entity.ProfileCore;

import java.sql.ResultSet;

public class ProfileCoreMapper implements RowMapper<ProfileCore>{

    @Override
    public ProfileCore mapRow(ResultSet resultSet) {
        try {
            return ProfileCore.builder()
                    .id(resultSet.getString("id"))
                    .fullName(resultSet.getString("fullName"))
                    .status(resultSet.getString("status"))
                    .isFriend(resultSet.getInt("isFriend"))
                    .code(resultSet.getString("code"))
                    .build();
        } catch (Exception e) {
            return null;

        }
    }
}

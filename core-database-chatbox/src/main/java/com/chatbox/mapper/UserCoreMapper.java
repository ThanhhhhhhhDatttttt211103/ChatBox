package com.chatbox.mapper;

import com.chatbox.entity.UserCore;

import java.sql.ResultSet;

public class UserCoreMapper implements RowMapper<UserCore>{
    @Override
    public UserCore mapRow(ResultSet resultSet) {
        try {
            return UserCore.builder()
                    .id(resultSet.getString("id"))
                    .fullname(resultSet.getString("fullname"))
                    .status(resultSet.getString("status"))
                    .build();
        } catch (Exception e) {
            return null;
        }
    }
}

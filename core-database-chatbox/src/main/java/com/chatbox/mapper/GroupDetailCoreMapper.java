package com.chatbox.mapper;

import com.chatbox.entity.GroupDetailCore;

import java.sql.ResultSet;

public class GroupDetailCoreMapper implements RowMapper<GroupDetailCore>{

    @Override
    public GroupDetailCore mapRow(ResultSet resultSet) {
        try {
            return GroupDetailCore.builder()
                    .idGroup(resultSet.getString("idGroup"))
                    .idUser(resultSet.getString("idUser"))
                    .build();
        } catch (Exception e) {
            return null;
        }
    }
}

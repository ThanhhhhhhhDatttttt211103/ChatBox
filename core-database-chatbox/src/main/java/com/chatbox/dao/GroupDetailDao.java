package com.chatbox.dao;

import com.chatbox.entity.GroupDetailCore;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class GroupDetailDao extends AbstractDao<GroupDetailCore> {

    public GroupDetailDao(DataSource dataSource) {
        super(dataSource);
    }

    public List<String> getIdMemberGroup(String id, String idGroup) {
        String procName = "{call proc_GetIdMemberGroup(?, ?, ?)}";
        List<String> listUserInGroup = query(procName, id, idGroup);
        return listUserInGroup;
    }
}

package com.chatbox.dao;

import com.chatbox.entity.UserCore;
import com.chatbox.mapper.UserCoreMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDao extends AbstractDao<UserCore> {
    public UserDao(DataSource dataSource) {
        super(dataSource);
    }

    public List<UserCore> searchUserAddGroup(String idUser, String idGroup, String name) {
        String procName = "{call proc_SearchFriendAdd(?, ?, ?, ?)}";
        return query(procName, new UserCoreMapper(), idUser, idGroup, name);
    }

    public List<UserCore> searchMemberGroup(String idGroup) {
        String procName = "{call proc_GetMemberGroup(?, ?)}";
        List<UserCore> listUser= query(procName, new UserCoreMapper(), idGroup);
        if (listUser.isEmpty()) {
            return null;
        }
        return listUser;
    }
}

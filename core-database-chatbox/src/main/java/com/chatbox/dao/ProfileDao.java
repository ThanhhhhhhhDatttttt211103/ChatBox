package com.chatbox.dao;

import com.chatbox.entity.ProfileCore;
import com.chatbox.mapper.ProfileCoreMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ProfileDao extends AbstractDao<ProfileCore> {
    public ProfileDao(DataSource dataSource) {
        super(dataSource);
    }

    public ProfileCore getProfileCore(String idUser, String idFriend) {
        String procName = "{call proc_GetProfile(?, ?, ?)}";
        List<ProfileCore> listProfile = query(procName, new ProfileCoreMapper(), idUser, idFriend);
        if (listProfile.isEmpty())
            return null;
        return listProfile.get(0);
    }
}

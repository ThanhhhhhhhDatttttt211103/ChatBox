package com.chatbox.service;

import com.chatbox.entity.ProfileCore;
import com.chatbox.entity.UserCore;

import java.util.List;

public interface IUserCoreService {
    public List<UserCore> searchUserAddGroup(String idUser, String idGroup, String name);

    public List<UserCore> searchMemberGroup(String idGroup);

    public ProfileCore getProfile(String idUser, String idFriend);
}

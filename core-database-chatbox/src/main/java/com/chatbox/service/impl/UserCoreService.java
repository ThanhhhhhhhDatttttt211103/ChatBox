package com.chatbox.service.impl;

import com.chatbox.entity.ProfileCore;
import com.chatbox.entity.UserCore;
import com.chatbox.dao.ProfileDao;
import com.chatbox.dao.UserDao;
import com.chatbox.service.IUserCoreService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UserCoreService implements IUserCoreService {


    private UserDao userCoreRepository;

    private ProfileDao profileCoreRepository;

    @Override
    public List<UserCore> searchUserAddGroup(String idUser, String idGroup, String name) {
        try {
            log.info("Begin searchUserAddGroup with idUser = {}, idGroup = {}, name = {}", idUser, idGroup, name);
            List<UserCore> listUserCore = userCoreRepository.searchUserAddGroup(idUser, idGroup, name);
            if (listUserCore.isEmpty()) {
                log.info("Can't find any user");
                return new ArrayList<>();
            }
            log.info("End searchUserAddGroup SUCCESS with count user = {}", listUserCore.size());
            return listUserCore;
        } catch (Exception e) {
            log.info("Exception in searchUserAddGroup");
            log.error("Exception in searchUserAddGroup", e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<UserCore> searchMemberGroup(String idGroup) {
        try {
            log.info("Begin searchMemberGroup with idGroup = {}", idGroup);
            List<UserCore> listUserCore = userCoreRepository.searchMemberGroup(idGroup);
            if (listUserCore.isEmpty()) {
                log.info("Can't find any user in group");
                return new ArrayList<>();
            }
            log.info("End searchMemberGroup SUCCESS with count user = {}", listUserCore.size());
            return listUserCore;
        } catch (Exception e) {
            log.info("Exception in searchMemberGroup");
            log.error("Exception in searchMemberGroup", e);
            return new ArrayList<>();
        }
    }

    @Override
    public ProfileCore getProfile(String idUser, String idFriend) {
        try{
            log.info("Begin getProfile with idUser = {}, idFriend = {}", idUser, idFriend);
            ProfileCore profileCore = profileCoreRepository.getProfileCore(idUser, idFriend);
            if (profileCore == null) {
                log.info("Can't find any profile");
                return new ProfileCore();
            }
            log.info("End getProfile SUCCESS with profileCore = {}", profileCore);
            return profileCore;
        } catch (Exception e) {
            log.info("Exception in getProfile");
            log.error("Exception in getProfile", e);
            return new ProfileCore();
        }
    }
}
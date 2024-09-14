package com.chatbox.service;

import java.util.List;

public interface IGroupDetailCoreService {
    List<String> getIdMemberGroup(String idUser, String idGroup);
}

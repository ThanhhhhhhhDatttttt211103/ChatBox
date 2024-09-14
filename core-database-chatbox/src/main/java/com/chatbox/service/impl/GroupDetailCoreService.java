package com.chatbox.service.impl;

import com.chatbox.dao.GroupDetailDao;
import com.chatbox.service.IGroupDetailCoreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GroupDetailCoreService implements IGroupDetailCoreService {

    private GroupDetailDao groupDetailCoreRepository;

    @Override
    public List<String> getIdMemberGroup(String idUser, String idGroup) {
        List<String> memberGroup = groupDetailCoreRepository.getIdMemberGroup(idUser, idGroup);
        if (memberGroup == null) {
            return null;
        }
        return memberGroup;
    }
}

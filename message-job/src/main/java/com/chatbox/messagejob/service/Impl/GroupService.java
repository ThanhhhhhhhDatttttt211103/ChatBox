package com.chatbox.messagejob.service.Impl;

import com.chatbox.messagejob.entity.Group;
import com.chatbox.messagejob.repository.GroupRepository;
import com.chatbox.messagejob.service.IGroupService;
import com.chatbox.messagejob.util.GroupUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class GroupService implements IGroupService {

    private GroupRepository groupRepository;

    @Override
    public String getGroupName(String idGroup) {
        try {
            log.info("Begin getGroupName with idGroup = {}", idGroup);
            Group group = groupRepository.findGroupById(idGroup);
            if (group == null) {
                log.info("Can't find any group name");
                return GroupUtil.NAME_ISEMPTY;
            }
            log.info("End getGroupName SUCCESS with groupName = {}", group.getGroupName());
            return group.getGroupName();
        } catch (Exception e) {
            log.info("Exception in getGroupName");
            log.error("Exception in getGroupName", e);
            return GroupUtil.NAME_ISEMPTY;
        }

    }
}

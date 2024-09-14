package com.chatbox.service.impl;

import com.chatbox.dto.GroupDetailDto;
import com.chatbox.entity.GroupDetail;
import com.chatbox.mapper.GroupDetailMapper;
import com.chatbox.repository.GroupDetailRepository;
import com.chatbox.service.IGroupDetailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class GroupDetailService implements IGroupDetailService {

    private GroupDetailRepository groupDetailRepository;

    @Override
    public GroupDetailDto save(GroupDetailDto groupDetailDto) {
        try {
            log.info("Begin save GroupDetail with {}", groupDetailDto);
            Thread.sleep(150);
            GroupDetail groupDetail = GroupDetailMapper.mapToGroupDetail(groupDetailDto);
            GroupDetail groupDetailSaved = groupDetailRepository.save(groupDetail);
            log.info("End save GroupDetail SUCCESS");
            return GroupDetailMapper.mapToGroupDetailDto(groupDetailSaved);
        } catch (Exception e) {
            log.info("Exception in save GroupDetail");
            log.error("Exception in save GroupDetail", e);
            return new GroupDetailDto();
        }
    }
}

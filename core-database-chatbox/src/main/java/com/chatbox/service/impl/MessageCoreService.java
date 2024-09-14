package com.chatbox.service.impl;

import com.chatbox.entity.MessageDisplay;
import com.chatbox.dao.MessageDisplayDao;
import com.chatbox.service.IMessageCoreService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class MessageCoreService implements IMessageCoreService {

    private MessageDisplayDao messageRepository;
    @Override
    public List<MessageDisplay> getMessageDisplay(String code, String id, int index) {
        try {
            log.info("Begin getMessageDisplay with code = {}, id = {}, index = {}", code, id, index);
            List<MessageDisplay> listMsgCore = messageRepository.getMessage(code, id, index);
            if (listMsgCore.isEmpty()) {
                log.info("Can't find any message");
                return new ArrayList<>();
            }
            log.info("End getMessageDisplay SUCCESS with count message = {}", listMsgCore.size());
            return listMsgCore;
        } catch (Exception e) {
            log.info("Exception in getMessageDisplay");
            log.error("Exception in getMessageDisplay", e);
            return new ArrayList<>();
        }
    }
}

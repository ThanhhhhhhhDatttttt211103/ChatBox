package com.chatbox.messagejob.service.Impl;

import com.chatbox.messagejob.entity.Message;
import com.chatbox.messagejob.repository.MessageRepository;
import com.chatbox.messagejob.service.IMessageService;
import com.chatbox.messagejob.util.IDUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MessageService implements IMessageService {

    private final MessageRepository messageRepository;

    @Override
    public Long saveMessage(Message message) {
        try {
            log.info("Begin saveMessage with {}", message);
            Message msgEntity = messageRepository.save(message);
            if (msgEntity.getId() == null) {
                log.info("Can't save message in DB");
                return IDUtil.ID_NULL;
            }
            log.info("Save message successfully with id = {}", msgEntity.getId());
            return msgEntity.getId();
        } catch (Exception e) {
            log.info("Exception in saveMessage");
            log.error("Exception in saveMessage", e);
            return IDUtil.ID_NULL;
        }
    }
}

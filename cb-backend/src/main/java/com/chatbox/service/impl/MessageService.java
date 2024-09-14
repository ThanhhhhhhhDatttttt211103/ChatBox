package com.chatbox.service.impl;

import com.chatbox.dto.MessageDto;
import com.chatbox.entity.Message;
import com.chatbox.mapper.MessageMapper;
import com.chatbox.repository.MessageRepository;
import com.chatbox.service.IMessageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessageService implements IMessageService {
    private MessageRepository messageRepository;

    @Override
    public MessageDto save(Message message) {
        Message messageData = messageRepository.save(message);
        return MessageMapper.mapToMessageDto(messageData);
    }
}

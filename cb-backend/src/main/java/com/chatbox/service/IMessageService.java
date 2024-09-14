package com.chatbox.service;

import com.chatbox.dto.MessageDto;
import com.chatbox.entity.Message;

public interface IMessageService {
    MessageDto save(Message message);
}

package com.chatbox.messagejob.service;

import com.chatbox.messagejob.dto.MessageDto;
import com.chatbox.messagejob.entity.Message;

public interface IMessageService {
    Long saveMessage(Message message);
}

package com.chatbox.service;

import com.chatbox.entity.MessageDisplay;

import java.util.List;

public interface IMessageCoreService {
    List<MessageDisplay> getMessageDisplay(String code, String id, int index);
}

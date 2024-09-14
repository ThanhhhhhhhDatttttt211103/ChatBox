package com.chatbox.messagejob.mapper;

import com.chatbox.messagejob.dto.MessageDto;
import com.chatbox.messagejob.dto.component.MessageDisplayDto;
import com.chatbox.messagejob.entity.Message;

public class MessageMapper {
    public static Message mapToMessage(MessageDisplayDto message) {
        return new Message(
                message.getId(),
                message.getSender(),
                message.getReceived(),
                message.getContent(),
                message.getCode(),
                message.getSendTime(),
                message.getIsEnable(),
                message.getType(),
                message.getMessageEdt()
        );
    }

    public static MessageDto mapToMessageDTO(Message message) {
        return new MessageDto(
                message.getId(),
                message.getSender(),
                message.getReceived(),
                message.getContent(),
                message.getCode(),
                message.getSendTime(),
                message.getIsEnable(),
                message.getType(),
                message.getMessageEdt()
        );
    }
}

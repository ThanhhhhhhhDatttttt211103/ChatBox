package com.chatbox.mapper;

import com.chatbox.dto.MessageDto;
import com.chatbox.dto.component.MessageDisplayDto;
import com.chatbox.entity.Message;
import com.chatbox.entity.MessageCore;
import com.chatbox.entity.MessageDisplay;

public class MessageMapper {
    public static MessageDto mapToMessageDto(Message message) {
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

    public static MessageDto mapToMessageDto(MessageCore messageCore) {
        return new MessageDto(
                messageCore.getId(),
                messageCore.getSender(),
                messageCore.getReceived(),
                messageCore.getContent(),
                messageCore.getCode(),
                messageCore.getSendTime(),
                messageCore.getIsEnable(),
                messageCore.getType(),
                messageCore.getMessageEdt()
        );
    }

    public static Message mapToMessage(MessageDto messageDto) {
        return new Message(
                messageDto.getId(),
                messageDto.getSender(),
                messageDto.getReceived(),
                messageDto.getContent(),
                messageDto.getCode(),
                messageDto.getSendTime(),
                messageDto.getIsEnable(),
                messageDto.getType(),
                messageDto.getMessageEdt()
        );
    }

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

    public static MessageDisplayDto mapToMessageDisplayDto(MessageDisplay message) {
        return MessageDisplayDto.builder()
                .id(message.getId())
                .sender(message.getSender())
                .received(message.getReceived())
                .content(message.getContent())
                .code(message.getCode())
                .sendTime(message.getSendTime())
                .isEnable(message.getIsEnable())
                .type(message.getType())
                .messageEdt(message.getMessageEdt())
                .senderName(message.getSenderName())
                .build();
    }
}
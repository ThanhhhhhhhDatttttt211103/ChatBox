package com.chatbox.controller;

import com.chatbox.dto.component.MessageDisplayDto;
import com.chatbox.publisher.RabbitMQProducer;
import com.chatbox.util.RabbitMQ;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@Slf4j
public class ChatController {
    private RabbitMQProducer rabbitMQService;

    @MessageMapping("/chat")
    public void processMessage(@Payload MessageDisplayDto message) {
        try {
            log.info("Begin processMessage");
            log.info("send {} to RabbitMQ SUCCESS", message);
            rabbitMQService.sendMessage(RabbitMQ.ROUTING_KEY_MSG_REQUEST, message);
        } catch (Exception e) {
            log.info("Exception in  processMessage");
            log.error("Exception in  processMessage", e);
        }
    }

    @MessageMapping("/chatFile")
    public void processMessageFile(@Payload MessageDisplayDto message) {
        try {
            log.info("Begin processMessageFile");
            log.info("send file {} to RabbitMQ SUCCESS", message);
            rabbitMQService.sendMessage(RabbitMQ.ROUTING_KEY_MSG_FILE_REQUEST, message);
        }catch (Exception e) {
            log.info("Exception in  processMessageFile");
            log.error("Exception in  processMessageFile", e);
        }
    }
}
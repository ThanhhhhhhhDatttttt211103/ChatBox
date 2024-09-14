package com.chatbox.consumer;

import com.chatbox.constant.ChatRoomConstant;
import com.chatbox.dto.component.MessageDisplayDto;
import com.chatbox.service.impl.GroupDetailCoreService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@AllArgsConstructor
@Slf4j
public class MessageConsumer {
    private final SimpMessagingTemplate messagingTemplate;
    private GroupDetailCoreService groupDetailCoreService;

    @RabbitListener(queues = "${rabbitmq.queue.message.response}")
    public void receiveResponse(MessageDisplayDto message) {
        try {
            log.info("Begin receiveResponse from queue with {}", message);
            if (message.getReceived().contains(ChatRoomConstant.USER)) {
                log.info("sent message to sender {}", message);
                messagingTemplate.convertAndSendToUser(
                        message.getSender(), "/queue/messages", message);
                log.info("sent message to received {}", message);
                messagingTemplate.convertAndSendToUser(
                        message.getReceived(), "/queue/messages", message);
            } else {
                log.info("sent message to member in group {}", message);
                List<String> members = groupDetailCoreService.getIdMemberGroup(message.getSender(), message.getReceived());
                members.parallelStream()
                        .forEach(member -> CompletableFuture.runAsync(() -> {
                            messagingTemplate.convertAndSendToUser(member, "/queue/messages", message);
                        }));
            }
            log.info("End receiveResponse");
        } catch (Exception e) {
            log.info("Exception in receiveResponse");
            log.error("Exception in receiveResponse",e);
        }
    }
}

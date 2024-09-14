package com.chatbox.messagejob.comsumer;

import com.chatbox.messagejob.dto.component.MessageDisplayDto;
import com.chatbox.messagejob.entity.Message;
import com.chatbox.messagejob.mapper.MessageMapper;
import com.chatbox.messagejob.service.IMessageService;
import com.chatbox.messagejob.util.MessageUtil;
import com.chatbox.messagejob.util.RabbitUtil;
import com.chatbox.publisher.RabbitMQProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageConsumer {
    @Autowired
    private IMessageService messageService;

    @Autowired
    private RabbitMQProducer rabbitMQProducer;

    @RabbitListener(queues = "${rabbitmq.queue.message.request}")
    public void receive(MessageDisplayDto message) {
        log.info("Begin receive message from queue {}", message);
        responseMessage(message);
        log.info("End receive message from queue");
    }

    @RabbitListener(queues = "${rabbitmq.queue.messageFile.request}")
    public void receiveFile(MessageDisplayDto message) {
        log.info("Begin receive message file from queue {}", message);
        String contentFile = message.getCode() + MessageUtil.SLASH + getContentType(message.getContent()) + MessageUtil.SLASH + message.getContent();
        message.setContent(contentFile);
        responseMessage(message);
        log.info("End receive message file from queue");
    }

    private void responseMessage(MessageDisplayDto message) {
        try {
            log.info("Begin responseMessage from queue {}", message);
            Message msgEntity = MessageMapper.mapToMessage(message);
            Long idMsgSaved = messageService.saveMessage(msgEntity);
            if (idMsgSaved == null) {
                log.info("Can't save message");
                return;
            }
            message.setId(idMsgSaved);
            rabbitMQProducer.sendMessage(RabbitUtil.ROUTING_KEY_MSG_RESPONSE, message);
            log.info("Send message to queue response {}", RabbitUtil.ROUTING_KEY_MSG_RESPONSE);
        } catch (Exception e) {
            log.info("Exception in responseMessage");
            log.error("Exception in responseMessage", e);
        }
    }

    private String getContentType(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        int dotIndex = fileName.lastIndexOf('.');
        String fileExtension = (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
        return getContentTypeFromExtension(fileExtension);
    }

    private String getContentTypeFromExtension(String extension) {
        return switch (extension.toLowerCase()) {
            case "png", "jpg", "jpeg" -> "images";
            default -> "files";
        };
    }
}
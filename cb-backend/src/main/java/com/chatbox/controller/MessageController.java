package com.chatbox.controller;

import com.chatbox.dto.component.MessageDisplayDto;
import com.chatbox.dto.component.MessageRequest;
import com.chatbox.entity.MessageDisplay;
import com.chatbox.mapper.MessageMapper;
import com.chatbox.service.impl.MessageCoreService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/message")
@AllArgsConstructor
@Slf4j
public class MessageController {
    private MessageCoreService msgCoreService;

    @PostMapping("/getMessage")
    public ResponseEntity<List<MessageDisplayDto>> getMessage(@RequestBody MessageRequest messageRequest) {
        log.info("Begin getMessage API with {}", messageRequest);
        long startTime = System.currentTimeMillis();
        List<MessageDisplay> listMsgCore = msgCoreService.getMessageDisplay(messageRequest.getCode(), messageRequest.getIdUser(), messageRequest.getIndex());
        List<MessageDisplayDto> listMsgDto = listMsgCore.stream().map(MessageMapper::mapToMessageDisplayDto).toList();
        log.info("End getMessage API with count message = {} in {} ms", listMsgCore.size(), System.currentTimeMillis() - startTime);
        return ResponseEntity.ok(listMsgDto);
    }
}
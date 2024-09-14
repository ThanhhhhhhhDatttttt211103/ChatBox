package com.chatbox.controller;

import com.chatbox.dto.GroupDetailDto;
import com.chatbox.service.IGroupDetailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/group")
@AllArgsConstructor
@Slf4j
public class GroupController {

    private IGroupDetailService groupDetailService;

    @PostMapping("/save-group-detail")
    public ResponseEntity<GroupDetailDto> save(@RequestBody GroupDetailDto groupDetailDto) {
       log.info("Begin save GroupDetail API with {}", groupDetailDto);
       long startTime = System.currentTimeMillis();
       GroupDetailDto groupDetailDtoSaved = groupDetailService.save(groupDetailDto);
       log.info("End save GroupDetail API with {} ms", System.currentTimeMillis() - startTime);
       return ResponseEntity.ok(groupDetailDtoSaved);
    }
}
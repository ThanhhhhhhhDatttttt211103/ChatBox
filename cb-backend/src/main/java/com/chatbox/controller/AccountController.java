package com.chatbox.controller;

import com.chatbox.dto.AccountDto;
import com.chatbox.service.impl.AccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/account")
@AllArgsConstructor
@Slf4j
public class AccountController {

    private AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountDto> login(@RequestBody AccountDto accountDto) {
        log.info("Begin login API with AccountDto = {}", accountDto);
        long startTime = System.currentTimeMillis();
        ResponseEntity<AccountDto> response = accountService.findAccountByUsernameAndPassword(accountDto.getUsername(), accountDto.getPassword());
        log.info("End login API successfully for username: {} in {} ms", accountDto.getUsername(), System.currentTimeMillis() - startTime);
        return response;
    }
}
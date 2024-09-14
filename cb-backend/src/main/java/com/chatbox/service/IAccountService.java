package com.chatbox.service;

import com.chatbox.dto.AccountDto;
import org.springframework.http.ResponseEntity;

public interface IAccountService {
    ResponseEntity<AccountDto> findAccountByUsernameAndPassword(String username, String password);
}
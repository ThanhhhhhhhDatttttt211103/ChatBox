package com.chatbox.service.impl;

import com.chatbox.dto.AccountDto;
import com.chatbox.entity.Account;
import com.chatbox.mapper.AccountMapper;
import com.chatbox.repository.AccountRepository;
import com.chatbox.service.IAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@Slf4j
public class AccountService implements IAccountService {

    private AccountRepository repository;

    @Override
    public ResponseEntity<AccountDto> findAccountByUsernameAndPassword(String username, String password) {
        try {
            log.info("Begin findAccountByUsernameAndPassword with username = {}", username);
            Account account = repository.findAccountByUsernameAndPassword(username, password);
            if (account == null) {
                log.info("Can't find any account");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            AccountDto accountDto = AccountMapper.mapToAccountDto(account);
            log.info("End findAccountByUsernameAndPassword SUCCESS with accountDto = {}", accountDto);
            return ResponseEntity.ok(accountDto);
        } catch (Exception e) {
            log.info("Exception in findAccountByUsernameAndPassword");
            log.error("Exception in findAccountByUsernameAndPassword", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
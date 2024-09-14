package com.chatbox.mapper;

import com.chatbox.dto.AccountDto;
import com.chatbox.entity.Account;

public class AccountMapper {

    public static AccountDto mapToAccountDto(Account account) {
        return new AccountDto(
                account.getIdUser(),
                account.getUsername(),
                account.getPassword()
        );
    }

    public static Account mapToAccount(AccountDto accountDto) {
        return new Account(
                accountDto.getIdUser(),
                accountDto.getUsername(),
                accountDto.getPassword()
        );
    }
}

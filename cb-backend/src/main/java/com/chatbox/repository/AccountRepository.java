package com.chatbox.repository;

import com.chatbox.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
    Account findAccountByUsernameAndPassword(String username, String password);
}

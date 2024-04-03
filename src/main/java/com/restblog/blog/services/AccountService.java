package com.restblog.blog.services;

import com.restblog.blog.models.Account;
import com.restblog.blog.repositories.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepo accountRepo;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public AccountService(AccountRepo accountRepo, PasswordEncoder passwordEncoder) {
        this.accountRepo = accountRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public Account saveAccount(Account account){
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepo.saveAndFlush(account);
    }

    public Optional<Account> findByEmail(String email) {
        return accountRepo.findByEmail(email);
    }
}

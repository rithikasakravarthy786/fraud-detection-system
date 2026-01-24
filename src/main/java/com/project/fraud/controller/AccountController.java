package com.project.fraud.controller;

import com.project.fraud.entity.*;
import com.project.fraud.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @PostMapping("/create/{userId}")
    public Account create(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        Account acc = Account.builder()
                .accountNumber("ACC" + System.currentTimeMillis())
                .balance(0)
                .blocked(false)
                .user(user)
                .build();
        return accountRepository.save(acc);
    }
}

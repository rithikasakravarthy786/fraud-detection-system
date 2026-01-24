package com.project.fraud.controller;

import com.project.fraud.entity.Transaction;
import com.project.fraud.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService service;

    @PostMapping("/deposit")
    public Transaction deposit(@RequestParam String acc,
                               @RequestParam double amount) {
        return service.deposit(acc, amount);
    }

    @PostMapping("/withdraw")
    public Transaction withdraw(@RequestParam String acc,
                                @RequestParam double amount) {
        return service.withdraw(acc, amount);
    }

    @PostMapping("/transfer")
    public Transaction transfer(@RequestParam String from,
                                @RequestParam String to,
                                @RequestParam double amount) {
        return service.transfer(from, to, amount);
    }
}

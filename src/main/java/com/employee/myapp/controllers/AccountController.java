package com.employee.myapp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @PostMapping("/{accountId}/debit")
    public ResponseEntity<String> debitAmount(@PathVariable Long accountId, @RequestParam Double amount) {
        return ResponseEntity.ok("Amount debited successfully");
    }

    @PostMapping("/{accountId}/credit")
    public ResponseEntity<String> creditAmount(@PathVariable Long accountId, @RequestParam Double amount) {
        return ResponseEntity.ok("Amount credited successfully");
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<String> getAccountDetails(@PathVariable Long accountId) {
        return ResponseEntity.ok("Account details retrieved");
    }

    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<String> getTransactions(@PathVariable Long accountId) {
        return ResponseEntity.ok("Transactions retrieved");
    }
}

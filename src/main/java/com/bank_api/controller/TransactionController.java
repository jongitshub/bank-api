package com.bank_api.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank_api.model.Transaction;
import com.bank_api.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/history/{accountId}")
    public ResponseEntity<List<Transaction>> getTransactionHistory(@PathVariable Long accountId) {
        List<Transaction> transactions = transactionService.getTransactionHistory(accountId);
        return ResponseEntity.ok(transactions);
    }

    @PostMapping("/transfer")
    public ResponseEntity<Transaction> transfer(
            @RequestParam Long fromAccountId,
            @RequestParam Long toAccountId,
            @RequestParam BigDecimal amount,
            Principal principal
    ) {
        // Optionally check if the logged-in user owns the fromAccountId
        Transaction transaction = transactionService.transfer(fromAccountId, toAccountId, amount);
        return ResponseEntity.ok(transaction);
    }
}

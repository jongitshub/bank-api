package com.bank_api.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank_api.model.Account;
import com.bank_api.model.Transaction;
import com.bank_api.model.TransactionType;
import com.bank_api.repository.AccountRepository;
import com.bank_api.repository.TransactionRepository;

import jakarta.transaction.Transactional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    public List<Transaction> getTransactionHistory(Long accountId) {
        return transactionRepository.findByFromAccount_IdOrToAccount_Id(accountId, accountId);
    }

    @Transactional
    public Transaction transfer(Long fromAccountId, Long toAccountId, BigDecimal amount) {
        Optional<Account> fromAccountOpt = accountRepository.findById(fromAccountId);
        Optional<Account> toAccountOpt = accountRepository.findById(toAccountId);

        if (fromAccountOpt.isEmpty() || toAccountOpt.isEmpty()) {
            throw new IllegalArgumentException("One or both accounts not found.");
        }

        Account fromAccount = fromAccountOpt.get();
        Account toAccount = toAccountOpt.get();

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds in source account.");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transaction transaction = Transaction.builder()
                .fromAccount(fromAccount)
                .toAccount(toAccount)
                .amount(amount)
                .type(TransactionType.TRANSFER)
                .timestamp(LocalDateTime.now())
                .build();

        return transactionRepository.save(transaction);
    }
}

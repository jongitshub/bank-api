package com.bank_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank_api.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByFromAccount_IdOrToAccount_Id(Long fromId, Long toId);
}

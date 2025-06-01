package com.bank_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank_api.model.BankReserve;

public interface BankReserveRepository extends JpaRepository<BankReserve, Long> {
}

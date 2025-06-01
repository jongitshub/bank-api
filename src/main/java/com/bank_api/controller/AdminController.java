package com.bank_api.controller;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank_api.model.BankReserve;
import com.bank_api.repository.BankReserveRepository;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')") // applies to all methods in this controller
public class AdminController {

    private final BankReserveRepository bankReserveRepository;

    public AdminController(BankReserveRepository bankReserveRepository) {
        this.bankReserveRepository = bankReserveRepository;
    }

    @GetMapping("/reserve")
    public ResponseEntity<?> getReserveBalance() {
        // Load reserve record by ID (assumes 1 record)
        BankReserve reserve = bankReserveRepository.findById(1L)
            .orElseGet(() -> bankReserveRepository.save(new BankReserve(new BigDecimal("10000000.00")))); // default reserve

        return ResponseEntity.ok(Map.of("reserve", reserve.getTotalFunds()));
    }
}

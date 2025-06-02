package com.bank_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank_api.model.LoanRequest;
import com.bank_api.model.LoanStatus;
import com.bank_api.model.User;

public interface LoanRequestRepository extends JpaRepository<LoanRequest, Long> {
    List<LoanRequest> findByUser(User user);
    List<LoanRequest> findByStatus(LoanStatus status); // ✅ Correct type
}

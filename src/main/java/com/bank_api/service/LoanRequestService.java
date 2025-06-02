package com.bank_api.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bank_api.dto.LoanRequestDTO;
import com.bank_api.model.LoanRequest;
import com.bank_api.model.LoanStatus;
import com.bank_api.model.User;
import com.bank_api.repository.LoanRequestRepository;

@Service
public class LoanRequestService {

    private final LoanRequestRepository loanRequestRepository;

    public LoanRequestService(LoanRequestRepository loanRequestRepository) {
        this.loanRequestRepository = loanRequestRepository;
    }

    public LoanRequest submitRequest(LoanRequestDTO dto, User user) {
        LoanRequest request = new LoanRequest(
                dto.getAmount(),
                LoanStatus.PENDING,
                user,
                LocalDateTime.now()
        );
        return loanRequestRepository.save(request);
    }

    public List<LoanRequest> getPendingRequests() {
        return loanRequestRepository.findAll()
                .stream()
                .filter(req -> req.getStatus() == LoanStatus.PENDING)
                .toList();
    }

    public List<LoanRequest> getApprovedLoans() {
        return loanRequestRepository.findByStatus(LoanStatus.APPROVED);
    }

    public LoanRequest approveRequest(Long id) {
        LoanRequest request = loanRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));
        request.setStatus(LoanStatus.APPROVED);
        request.setApprovedAt(LocalDateTime.now());
        return loanRequestRepository.save(request);
    }

    public void rejectRequest(Long id) {
        LoanRequest request = loanRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));
        request.setStatus(LoanStatus.REJECTED);
        loanRequestRepository.save(request);
    }
}

package com.bank_api.controller;

import com.bank_api.dto.LoanRequestDTO;
import com.bank_api.model.Account;
import com.bank_api.model.LoanRequest;
import com.bank_api.model.User;
import com.bank_api.repository.AccountRepository;
import com.bank_api.repository.UserRepository;
import com.bank_api.service.LoanRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanRequestController {

    private final LoanRequestService loanRequestService;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public LoanRequestController(
            LoanRequestService loanRequestService,
            UserRepository userRepository,
            AccountRepository accountRepository) {
        this.loanRequestService = loanRequestService;
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @PostMapping("/request")
    public ResponseEntity<?> requestLoan(@RequestBody LoanRequestDTO dto, Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        LoanRequest request = loanRequestService.submitRequest(dto, user);
        return ResponseEntity.ok("Loan request submitted with ID: " + request.getId());
    }

    @GetMapping("/pending")
    public ResponseEntity<List<LoanRequest>> getPendingRequests() {
        List<LoanRequest> pending = loanRequestService.getPendingRequests();
        return ResponseEntity.ok(pending);
    }

    @PostMapping("/approve/{id}")
    public ResponseEntity<?> approveLoan(@PathVariable Long id) {
        LoanRequest request = loanRequestService.approveRequest(id);

        Account account = accountRepository.findByUser(request.getUser())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setBalance(account.getBalance().add(request.getAmount()));
        accountRepository.save(account);

        return ResponseEntity.ok("Loan approved and funds deposited.");
    }

    @PostMapping("/reject/{id}")
    public ResponseEntity<?> rejectLoan(@PathVariable Long id) {
        loanRequestService.rejectRequest(id);
        return ResponseEntity.ok("Loan rejected.");
    }
}

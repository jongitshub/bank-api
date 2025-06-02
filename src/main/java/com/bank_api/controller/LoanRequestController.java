package com.bank_api.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank_api.dto.LoanRequestDTO;
import com.bank_api.model.Account;
import com.bank_api.model.BankReserve;
import com.bank_api.model.LoanRequest;
import com.bank_api.model.User;
import com.bank_api.repository.AccountRepository;
import com.bank_api.repository.BankReserveRepository;
import com.bank_api.repository.UserRepository;
import com.bank_api.service.LoanRequestService;

@RestController
@RequestMapping("/api/loans")
public class LoanRequestController {

    private final LoanRequestService loanRequestService;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    private final BankReserveRepository bankReserveRepository;

public LoanRequestController(
    LoanRequestService loanRequestService,
    UserRepository userRepository,
    AccountRepository accountRepository,
    BankReserveRepository bankReserveRepository // ✅ add this
) {
    this.loanRequestService = loanRequestService;
    this.userRepository = userRepository;
    this.accountRepository = accountRepository;
    this.bankReserveRepository = bankReserveRepository; // ✅ assign it
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
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<?> approveLoan(@PathVariable Long id) {
    LoanRequest request = loanRequestService.approveRequest(id);

    // Credit the user's account
    Account account = accountRepository.findByUser(request.getUser())
            .orElseThrow(() -> new RuntimeException("Account not found"));

    account.setBalance(account.getBalance().add(request.getAmount()));
    accountRepository.save(account);

    // Subtract from bank reserve
    BankReserve reserve = bankReserveRepository.findById(1L)
            .orElseThrow(() -> new RuntimeException("Reserve not initialized"));

    BigDecimal newBalance = reserve.getTotalFunds().subtract(request.getAmount());

    if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
        return ResponseEntity.badRequest().body("Not enough funds in reserve to approve this loan.");
    }

    reserve.setTotalFunds(newBalance);
    bankReserveRepository.save(reserve);

    return ResponseEntity.ok("Loan approved and funds deposited.");
}


    @PostMapping("/reject/{id}")
    public ResponseEntity<?> rejectLoan(@PathVariable Long id) {
        loanRequestService.rejectRequest(id);
        return ResponseEntity.ok("Loan rejected.");
    }
    @GetMapping("/approved")
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<List<LoanRequestDTO>> getApprovedLoans() {
    List<LoanRequest> approvedLoans = loanRequestService.getApprovedLoans();

    List<LoanRequestDTO> result = approvedLoans.stream()
        .map(loan -> new LoanRequestDTO(
            loan.getAmount(),
            loan.getUser().getUsername(), // or use getAccount().getAccountNumber()
            loan.getApprovedAt()
        ))
        .collect(Collectors.toList());

    return ResponseEntity.ok(result);
}



}

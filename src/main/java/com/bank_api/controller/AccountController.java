package com.bank_api.controller;

import com.bank_api.model.Account;
import com.bank_api.model.User;
import com.bank_api.repository.AccountRepository;
import com.bank_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<String> createAccount(Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
        .orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        Account account = new Account();
        account.setAccountNumber(UUID.randomUUID().toString());
        account.setAccountType("CHECKING"); // Set default or adjust as needed
        account.setBalance(BigDecimal.ZERO);
        account.setUser(user);

        accountRepository.save(account);

        return ResponseEntity.ok("Account created successfully");
    }
}

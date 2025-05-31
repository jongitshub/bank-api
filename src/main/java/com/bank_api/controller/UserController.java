package com.bank_api.controller;

import com.bank_api.model.User;
import com.bank_api.model.Account;
import com.bank_api.repository.UserRepository;
import com.bank_api.repository.AccountRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public UserController(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUserInfo(Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Account account = accountRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        Map<String, Object> response = new HashMap<>();
        response.put("username", user.getUsername());
        response.put("role", user.getRole().name());
        response.put("accountNumber", account.getAccountNumber());
        response.put("accountType", account.getAccountType());
        response.put("balance", account.getBalance());

        return ResponseEntity.ok(response);
    }
}

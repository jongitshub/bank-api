package com.bank_api.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank_api.dto.RegisterRequest;
import com.bank_api.model.Account;
import com.bank_api.model.Role;
import com.bank_api.model.User;
import com.bank_api.repository.AccountRepository;
import com.bank_api.repository.UserRepository;
import com.bank_api.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("User already exists");
        }

        // Create and save user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER); // Assign default role
        userRepository.save(user);

        // Create and save account
        Account account = new Account();
        account.setAccountNumber(UUID.randomUUID().toString()); // Generated automatically
        account.setAccountType(request.getAccountType());
        account.setBalance(BigDecimal.ZERO);
        account.setCreatedAt(LocalDateTime.now());
        account.setUser(user);
        accountRepository.save(account);

        // Generate JWT token
        String jwtToken = jwtUtil.generateToken(user.getUsername());

        Map<String, String> response = new HashMap<>();
        response.put("token", jwtToken);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        body.get("username"),
                        body.get("password")
                )
        );

        String token = jwtUtil.generateToken(body.get("username"));
        return ResponseEntity.ok(Map.of("token", token));
    }
}

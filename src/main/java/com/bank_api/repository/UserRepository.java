package com.bank_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank_api.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}

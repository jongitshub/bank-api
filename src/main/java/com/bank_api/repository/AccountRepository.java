// File: src/main/java/com/bank_api/repository/AccountRepository.java
package com.bank_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank_api.model.Account;
import com.bank_api.model.User;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByUser(User user);
}

package com.bank_api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class LoanRequestDTO {
    private BigDecimal amount;
    private String username;
    private LocalDateTime approvedAt;

    public LoanRequestDTO(BigDecimal amount, String username, LocalDateTime approvedAt) {
        this.amount = amount;
        this.username = username;
        this.approvedAt = approvedAt;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getUsername() {
        return username;
    }

    public LocalDateTime getApprovedAt() {
        return approvedAt;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setApprovedAt(LocalDateTime approvedAt) {
        this.approvedAt = approvedAt;
    }
}

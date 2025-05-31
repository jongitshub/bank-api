package com.bank_api.dto;

import java.math.BigDecimal;

public class LoanRequestDTO {
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}

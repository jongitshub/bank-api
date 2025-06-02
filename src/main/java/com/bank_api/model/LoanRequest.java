package com.bank_api.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "loan_request")
public class LoanRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime requestedAt;

    public LoanRequest() {}

    public LoanRequest(BigDecimal amount, LoanStatus status, User user, LocalDateTime requestedAt) {
        this.amount = amount;
        this.status = status;
        this.user = user;
        this.requestedAt = requestedAt;
    }

    private LocalDateTime approvedAt;


    // Getters and setters
    // ...

    public LocalDateTime getApprovedAt() {
    return approvedAt;
}

public void setApprovedAt(LocalDateTime approvedAt) {
    this.approvedAt = approvedAt;
}

    public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public BigDecimal getAmount() {
    return amount;
}

public void setAmount(BigDecimal amount) {
    this.amount = amount;
}

public LoanStatus getStatus() {
    return status;
}

public void setStatus(LoanStatus status) {
    this.status = status;
}

public User getUser() {
    return user;
}

public void setUser(User user) {
    this.user = user;
}

public LocalDateTime getRequestedAt() {
    return requestedAt;
}

public void setRequestedAt(LocalDateTime requestedAt) {
    this.requestedAt = requestedAt;
}


}

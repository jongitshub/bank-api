package com.bank_api.dto;

public class RegisterRequest {

    private String username;
    private String password;
    private String role; // Accepts values like "USER", "ADMIN"
    private String accountNumber;
    private String accountType;

    public RegisterRequest() {
    }

    public RegisterRequest(String username, String password, String role, String accountNumber, String accountType) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}

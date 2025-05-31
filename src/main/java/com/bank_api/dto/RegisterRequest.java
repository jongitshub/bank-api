package com.bank_api.dto;

public class RegisterRequest {

    private String username;
    private String password;
    private String role;          // e.g., "USER", "ADMIN"
    private String accountType;   // e.g., "CHECKING", "SAVINGS"

    public RegisterRequest() {
    }

    public RegisterRequest(String username, String password, String role, String accountType) {
        this.username = username;
        this.password = password;
        this.role = role;
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

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}

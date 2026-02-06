package com.revshop.model;

import java.time.LocalDateTime;

public class User {

    private int userId;
    private String name;
    private String email;
    private String password;
    private String role;

    // 🔐 Security fields
    private String securityQuestion;
    private String securityAnswer;

    private LocalDateTime createdAt;

    public User() {}

    // Used for login / fetch
    public User(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Used for registration
    public User(String name, String email, String password,
                String role, String securityQuestion, String securityAnswer) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
    }

    // ---------- getters & setters ----------

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getSecurityQuestion() { return securityQuestion; }
    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() { return securityAnswer; }
    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

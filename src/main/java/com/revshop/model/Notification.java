package com.revshop.notification;

import java.time.LocalDateTime;

public class Notification {

    private final int sellerId;
    private final String message;
    private final LocalDateTime createdAt;

    public Notification(int sellerId, String message) {
        this.sellerId = sellerId;
        this.message = message;
        this.createdAt = LocalDateTime.now();
    }

    public int getSellerId() {
        return sellerId;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}

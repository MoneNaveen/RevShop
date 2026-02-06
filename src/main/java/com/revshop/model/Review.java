package com.revshop.model;

import java.time.LocalDateTime;

public class Review {

    private int reviewId;
    private int productId;
    private int buyerId;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;

    // Constructor for INSERT
    public Review(int productId, int buyerId, int rating, String comment) {
        this.productId = productId;
        this.buyerId = buyerId;
        this.rating = rating;
        this.comment = comment;
    }

    // Constructor for FETCH
    public Review(int reviewId, int productId, int buyerId,
                  int rating, String comment, LocalDateTime createdAt) {
        this.reviewId = reviewId;
        this.productId = productId;
        this.buyerId = buyerId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    // ================= GETTERS =================
    public int getReviewId() {
        return reviewId;
    }

    public int getProductId() {
        return productId;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}

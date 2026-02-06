package com.revshop.model;

public class Order {

    private int buyerId;
    private String buyerEmail;
    private double totalAmount;

    // For placing order
    public Order(int buyerId, String buyerEmail, double totalAmount) {
        this.buyerId = buyerId;
        this.buyerEmail = buyerEmail;
        this.totalAmount = totalAmount;
    }

    // For seller order view
    public Order(String buyerEmail, double totalAmount) {
        this.buyerEmail = buyerEmail;
        this.totalAmount = totalAmount;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}

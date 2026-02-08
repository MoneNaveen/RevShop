package com.revshop.model;

import java.time.LocalDateTime;

public class Order {

    private int orderId;
    private int buyerId;
    private String buyerEmail;
    private double totalAmount;

    private LocalDateTime orderDate;
    private OrderStatus status;
    private PaymentMode paymentMode;

    // ================= CONSTRUCTORS =================

    // For placing order (buyer checkout)
    public Order(int buyerId, String buyerEmail, double totalAmount) {
        this.buyerId = buyerId;
        this.buyerEmail = buyerEmail;
        this.totalAmount = totalAmount;
    }

    // For seller order view (summary)
    public Order(String buyerEmail, double totalAmount) {
        this.buyerEmail = buyerEmail;
        this.totalAmount = totalAmount;
    }

    // ================= GETTERS & SETTERS =================

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }
}

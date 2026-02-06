package com.revshop.model;

import java.time.LocalDateTime;

public class Product {

    private int productId;
    private int sellerId;
    private String name;
    private String description;
    private String category;
    private double price;
    private double mrp;
    private double discountPrice;
    private int stockQuantity;
    private LocalDateTime createdAt;

    public Product() {}

    public Product(
            int productId,
            int sellerId,
            String name,
            String description,
            String category,
            double price,
            double mrp,
            double discountPrice,
            int stockQuantity
    ) {
        this.productId = productId;
        this.sellerId = sellerId;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.mrp = mrp;
        this.discountPrice = discountPrice;
        this.stockQuantity = stockQuantity;
    }

    // ---------- Getters ----------

    public int getProductId() {
        return productId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public double getMrp() {
        return mrp;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

// ---------- Setters ----------

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setMrp(double mrp) {
        this.mrp = mrp;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}

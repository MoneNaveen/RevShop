package com.revshop.model;

public class Favorite {

    private int favId;
    private int buyerId;
    private int productId;

    public Favorite(int buyerId, int productId) {
        this.buyerId = buyerId;
        this.productId = productId;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public int getProductId() {
        return productId;
    }
}

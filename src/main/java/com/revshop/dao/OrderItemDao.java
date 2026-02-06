package com.revshop.dao;

import com.revshop.model.CartItem;

import java.util.List;

public interface OrderItemDao {
    void saveOrderItems(int orderId, List<CartItem> items);
}

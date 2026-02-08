package com.revshop.dao;

import com.revshop.model.Order;
import com.revshop.model.OrderStatus;

import java.util.List;

public interface OrderDao {

    int saveOrder(Order order);

    List<Order> getOrdersByBuyer(int buyerId);

    List<Order> getOrdersBySeller(int sellerId);

    void updateOrderStatus(int orderId, OrderStatus status);
}

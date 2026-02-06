package com.revshop.dao;

import com.revshop.model.Order;
import java.util.List;

public interface OrderDao {

    int saveOrder(Order order);   // ✅ return orderId

    List<Order> getOrdersBySeller(int sellerId);

    List<Order> getOrdersByBuyer(int buyerId);

}

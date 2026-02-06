package com.revshop.service;

import com.revshop.dao.*;
import com.revshop.model.CartItem;
import com.revshop.model.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class OrderService {

    private static final Logger logger =
            LogManager.getLogger(OrderService.class);

    private final OrderDao orderDao = new OrderDaoImpl();
    private final OrderItemDao orderItemDao = new OrderItemDaoImpl();
    private final ProductService productService = new ProductService();

    // BUYER: place order
    public void placeOrder(int buyerId,
                           String buyerEmail,
                           List<CartItem> items,
                           double totalAmount) {

        Order order = new Order(buyerId, buyerEmail, totalAmount);

        int orderId = orderDao.saveOrder(order); // ✅ FIXED

        orderItemDao.saveOrderItems(orderId, items);

        for (CartItem item : items) {
            productService.reduceStock(
                    item.getProductId(),
                    item.getQuantity()
            );
        }

        logger.info("Order placed successfully for {}", buyerEmail);
    }

    // SELLER: view orders
    public void viewOrdersBySeller(int sellerId) {
        var orders = orderDao.getOrdersBySeller(sellerId);

        if (orders.isEmpty()) {
            System.out.println("📭 No orders found");
            return;
        }

        System.out.println("\n--- ORDERS ---");
        for (Order o : orders) {
            System.out.println(
                    "Buyer: " + o.getBuyerEmail() +
                            " | Total: ₹" + o.getTotalAmount()
            );
        }
    }

    public void viewOrdersByBuyer(int buyerId) {

        List<Order> orders = orderDao.getOrdersByBuyer(buyerId);

        if (orders.isEmpty()) {
            System.out.println("📭 No orders found");
            return;
        }

        System.out.println("\n--- YOUR ORDERS ---");
        for (Order o : orders) {
            System.out.println("Total: ₹" + o.getTotalAmount());
        }
    }

}

package com.revshop.service;

import com.revshop.dao.*;
import com.revshop.model.CartItem;
import com.revshop.model.Order;
import com.revshop.model.OrderStatus;
import com.revshop.model.PaymentMode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.List;

public class OrderService {

    private static final Logger logger =
            LogManager.getLogger(OrderService.class);

    private final OrderDao orderDao = new OrderDaoImpl();
    private final OrderItemDao orderItemDao = new OrderItemDaoImpl();
    private final ProductService productService = new ProductService();

    // ================= BUYER =================

    public void placeOrder(int buyerId,
                           String buyerEmail,
                           List<CartItem> items,
                           double totalAmount) {

        Order order = new Order(buyerId, buyerEmail, totalAmount);

        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PLACED);
        order.setPaymentMode(PaymentMode.COD);

        int orderId = orderDao.saveOrder(order);

        orderItemDao.saveOrderItems(orderId, items);

        for (CartItem item : items) {
            productService.reduceStock(
                    item.getProductId(),
                    item.getQuantity()
            );

            // 🔔 low stock notification
            productService.checkLowStockAndNotify(item.getProductId());
        }

        logger.info("Order placed successfully for {}", buyerEmail);
    }

    public void viewOrdersByBuyer(int buyerId) {

        List<Order> orders = orderDao.getOrdersByBuyer(buyerId);

        if (orders.isEmpty()) {
            System.out.println("📭 No orders found");
            return;
        }

        System.out.println("\n--- YOUR ORDERS ---");
        System.out.println("OrderID | Date | Total | Payment | Status");

        for (Order o : orders) {
            System.out.printf(
                    "%d | %s | ₹%.2f | %s | %s%n",
                    o.getOrderId(),
                    o.getOrderDate(),
                    o.getTotalAmount(),
                    o.getPaymentMode(),
                    o.getStatus()
            );
        }
    }

    // ================= SELLER =================

    public void viewOrdersBySeller(int sellerId) {

        List<Order> orders = orderDao.getOrdersBySeller(sellerId);

        if (orders.isEmpty()) {
            System.out.println("📭 No orders found");
            return;
        }

        System.out.println("\n--- ORDERS ---");
        for (Order o : orders) {
            System.out.println(
                    "OrderID: " + o.getOrderId() +
                            " | Buyer: " + o.getBuyerEmail() +
                            " | Total: ₹" + o.getTotalAmount() +
                            " | Status: " + o.getStatus()
            );
        }
    }

    public void updateOrderStatus(int orderId, OrderStatus status) {
        orderDao.updateOrderStatus(orderId, status);
        logger.info("Order {} status updated to {}", orderId, status);
    }

    // ================= COMMON =================

    // Used by ReviewService
    public boolean hasPurchased(int buyerId, int productId) {
        return orderItemDao.existsByBuyerAndProduct(buyerId, productId);
    }
}

package com.revshop.notification;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NotificationService {

    private static final Logger logger =
            LogManager.getLogger(NotificationService.class);

    // ✅ In-memory notification store (console app style)
    private static final List<com.revshop.notification.Notification> notifications = new ArrayList<>();

    // ================= BUYER =================
    public void notifyOrderPlaced(String email) {
        logger.info("Notification sent to {}: Order placed successfully", email);
        System.out.println("🔔 Notification: Order placed successfully for " + email);
    }

    // ================= SELLER =================
    public void notifySeller(int sellerId, String message) {
        logger.info("Seller notification for sellerId={}: {}", sellerId, message);
        notifications.add(new com.revshop.notification.Notification(sellerId, message));
    }

    // ================= LOW STOCK =================
    public void notifyLowStock(int sellerId, int productId, int remainingStock) {

        String message =
                "⚠ Low stock for Product ID " +
                        productId + " | Remaining: " +
                        remainingStock;

        logger.warn("{}", message);
        notifications.add(new com.revshop.notification.Notification(sellerId, message));
    }

    // ================= VIEW SELLER NOTIFICATIONS =================
    public void viewSellerNotifications(int sellerId) {

        List<com.revshop.notification.Notification> sellerNotifications =
                notifications.stream()
                        .filter(n -> n.getSellerId() == sellerId)
                        .collect(Collectors.toList());

        if (sellerNotifications.isEmpty()) {
            System.out.println("📭 No notifications");
            return;
        }

        System.out.println("\n--- 🔔 NOTIFICATIONS ---");

        for (com.revshop.notification.Notification n : sellerNotifications) {
            System.out.println(
                    "• " + n.getMessage() +
                            " | " + n.getCreatedAt()
            );
        }
    }

    // (optional – keep for compatibility)
    public void notifyUser(String message) {
        logger.info("User notification: {}", message);
        System.out.println("🔔 " + message);
    }
}

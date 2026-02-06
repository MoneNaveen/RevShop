package com.revshop.notification;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NotificationService {

    private static final Logger logger =
            LogManager.getLogger(NotificationService.class);

    // ================= ORDER NOTIFICATION =================
    public void notifyOrderPlaced(String email) {
        logger.info("Notification sent to {}: Order placed successfully", email);
        System.out.println("🔔 Notification: Order placed successfully for " + email);
    }

    // ================= SELLER ALERT =================
    public void notifySeller(String message) {
        logger.info("Seller notification: {}", message);
        System.out.println("🔔 Seller Alert: " + message);
    }

    // ================= LOW STOCK ALERT =================
    public void notifyLowStock(int productId, int remainingStock) {
        logger.warn("Low stock alert for product {}. Remaining: {}",
                productId, remainingStock);
        System.out.println(
                "⚠️ Low Stock Alert: Product ID " +
                        productId + " has only " +
                        remainingStock + " items left"
        );
    }

    public void notifyUser(String s) {
    }
}

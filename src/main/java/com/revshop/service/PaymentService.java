package com.revshop.service;

import com.revshop.exception.PaymentFailedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

public class PaymentService {

    private static final Logger logger =
            LogManager.getLogger(PaymentService.class);

    private static final Set<String> ALLOWED_METHODS =
            Set.of("UPI", "CARD", "COD");

    public boolean makePayment(double amount, String method) {

        if (amount <= 0) {
            logger.error("Invalid payment amount: {}", amount);
            throw new PaymentFailedException("Invalid payment amount");
        }

        if (method == null || method.isBlank()) {
            logger.error("Payment method not provided");
            throw new PaymentFailedException("Payment method is required");
        }

        String paymentMethod = method.toUpperCase();

        if (!ALLOWED_METHODS.contains(paymentMethod)) {
            logger.error("Unsupported payment method: {}", method);
            throw new PaymentFailedException(
                    "Unsupported payment method. Allowed: UPI / CARD / COD"
            );
        }

        logger.info("Processing {} payment for ₹{}", paymentMethod, amount);

        // Simulated payment processing
        logger.info("Payment successful via {}", paymentMethod);
        return true;
    }
}

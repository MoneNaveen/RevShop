package com.revshop.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PaymentService {

    private static final Logger logger =
            LogManager.getLogger(PaymentService.class);

    public boolean makePayment(double amount, String method) {
        logger.info("Processing {} payment for ₹{}", method, amount);

        // Simulated payment
        logger.info("Payment successful via {}", method);
        return true;
    }
}

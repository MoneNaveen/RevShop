package com.revshop.service;

import com.revshop.exception.CartEmptyException;
import com.revshop.exception.InsufficientStockException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartServiceTest {

    @Test
    void shouldCalculateTotalCorrectly() {
        CartService cartService = new CartService();

        cartService.addToCart(1, 2); // productId=1 must exist
        cartService.addToCart(2, 1); // productId=2 must exist

        double total = cartService.getTotal();

        assertTrue(total > 0);
    }

    @Test
    void shouldThrowExceptionWhenCartIsEmpty() {
        CartService cartService = new CartService();

        assertThrows(CartEmptyException.class, cartService::getTotal);
    }

    @Test
    void shouldThrowExceptionForInvalidQuantity() {
        CartService cartService = new CartService();

        assertThrows(
                InsufficientStockException.class,
                () -> cartService.addToCart(1, 0)
        );
    }
}

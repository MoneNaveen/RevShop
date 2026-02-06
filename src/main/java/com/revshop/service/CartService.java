package com.revshop.service;

import com.revshop.dao.ProductDao;
import com.revshop.dao.ProductDaoImpl;
import com.revshop.exception.CartEmptyException;
import com.revshop.exception.InsufficientStockException;
import com.revshop.exception.ProductNotFoundException;
import com.revshop.model.CartItem;
import com.revshop.model.Product;

import java.util.*;

public class CartService {

    private final ProductDao productDao = new ProductDaoImpl();

    // productId -> CartItem
    private final Map<Integer, CartItem> cart = new HashMap<>();

    // ================= ADD TO CART =================
    public void addToCart(int productId, int quantity) {

        if (quantity <= 0) {
            throw new InsufficientStockException("Quantity must be greater than zero");
        }

        Product product = productDao.findById(productId);

        if (product == null) {
            throw new ProductNotFoundException("Product not found");
        }

        if (quantity > product.getStockQuantity()) {
            throw new InsufficientStockException(
                    "Only " + product.getStockQuantity() + " items available"
            );
        }

        // If already in cart → update quantity
        CartItem existing = cart.get(productId);
        if (existing != null) {
            int newQty = existing.getQuantity() + quantity;

            if (newQty > product.getStockQuantity()) {
                throw new InsufficientStockException(
                        "Only " + product.getStockQuantity() + " items available"
                );
            }

            cart.put(productId,
                    new CartItem(productId, newQty, product.getPrice()));
        } else {
            cart.put(productId,
                    new CartItem(productId, quantity, product.getPrice()));
        }
    }

    // ================= TOTAL =================
    public double getTotal() {
        if (cart.isEmpty()) {
            throw new CartEmptyException("Cart is empty");
        }

        return cart.values()
                .stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    // ================= RETURN AS LIST (IMPORTANT FIX) =================
    public List<CartItem> getItems() {
        if (cart.isEmpty()) {
            throw new CartEmptyException("Cart is empty");
        }
        return new ArrayList<>(cart.values());
    }

    // ================= CLEAR =================
    public void clearCart() {
        cart.clear();
    }

    // ================= VIEW CART (OPTIONAL) =================
    public void viewCart() {
        if (cart.isEmpty()) {
            System.out.println("🛒 Cart is empty");
            return;
        }

        System.out.println("\n--- CART ITEMS ---");
        for (CartItem item : cart.values()) {
            System.out.println(
                    "Product ID: " + item.getProductId() +
                            " | Qty: " + item.getQuantity() +
                            " | Price: ₹" + item.getPrice()
            );
        }
    }
}

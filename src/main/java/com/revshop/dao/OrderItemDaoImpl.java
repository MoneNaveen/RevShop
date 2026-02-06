package com.revshop.dao;

import com.revshop.model.CartItem;
import com.revshop.util.DBConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class OrderItemDaoImpl implements OrderItemDao {

    private static final String INSERT_ITEM =
            "INSERT INTO order_items (order_id, product_id, quantity, price_at_purchase) VALUES (?, ?, ?, ?)";

    @Override
    public void saveOrderItems(int orderId, List<CartItem> items) {

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_ITEM)) {

            for (CartItem item : items) {
                ps.setInt(1, orderId);
                ps.setInt(2, item.getProductId());
                ps.setInt(3, item.getQuantity());
                ps.setDouble(4, item.getPrice());
                ps.addBatch();
            }

            ps.executeBatch();

        } catch (Exception e) {
            throw new RuntimeException("Failed to save order items", e);
        }
    }
}

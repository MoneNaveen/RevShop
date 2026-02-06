package com.revshop.dao;

import com.revshop.model.Order;
import com.revshop.util.DBConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {

    private static final String INSERT_ORDER =
            "INSERT INTO orders (buyer_id, buyer_email, total_amount, status) VALUES (?, ?, ?, ?)";

    private static final String BY_BUYER =
            "SELECT * FROM orders WHERE buyer_id = ? ORDER BY created_at DESC";

    private static final String BY_SELLER = """
        SELECT DISTINCT o.order_id, o.buyer_email, o.total_amount
        FROM orders o
        JOIN order_items oi ON o.order_id = oi.order_id
        JOIN products p ON oi.product_id = p.product_id
        WHERE p.seller_id = ?
    """;

    // ================= SAVE ORDER =================
    @Override
    public int saveOrder(Order order) {

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, order.getBuyerId());
            ps.setString(2, order.getBuyerEmail());
            ps.setDouble(3, order.getTotalAmount());
            ps.setString(4, "PLACED");

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // order_id
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to save order", e);
        }

        return -1;
    }

    // ================= BUYER: VIEW ORDERS =================
    @Override
    public List<Order> getOrdersByBuyer(int buyerId) {

        List<Order> orders = new ArrayList<>();

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(BY_BUYER)) {

            ps.setInt(1, buyerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt("buyer_id"),
                        rs.getString("buyer_email"),
                        rs.getDouble("total_amount")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }

    // ================= SELLER: VIEW ORDERS =================
    @Override
    public List<Order> getOrdersBySeller(int sellerId) {

        List<Order> orders = new ArrayList<>();

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(BY_SELLER)) {

            ps.setInt(1, sellerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                orders.add(new Order(
                        rs.getString("buyer_email"),
                        rs.getDouble("total_amount")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }
}

package com.revshop.dao;

import com.revshop.model.Product;
import com.revshop.util.DBConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FavoriteDaoImpl implements FavoriteDao {

    private static final String ADD_FAV =
            "INSERT INTO favorites (buyer_id, product_id) VALUES (?, ?)";

    @Override
    public void addToFavorites(int buyerId, int productId) {

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(ADD_FAV)) {

            ps.setInt(1, buyerId);
            ps.setInt(2, productId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Already added to favorites");
        }
    }

    private static final String GET_FAVS =
            """
            SELECT p.product_id, p.name, p.category, p.price, p.stock_quantity
            FROM favorites f
            JOIN products p ON f.product_id = p.product_id
            WHERE f.buyer_id = ?
            """;

    @Override
    public List<Product> getFavoritesByBuyer(int buyerId) {

        List<Product> products = new ArrayList<>();

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_FAVS)) {

            ps.setInt(1, buyerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product p = new Product();
                p.setProductId(rs.getInt("product_id"));
                p.setName(rs.getString("name"));
                p.setCategory(rs.getString("category"));
                p.setPrice(rs.getDouble("price"));
                p.setStockQuantity(rs.getInt("stock_quantity"));
                products.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    private static final String REMOVE =
            "DELETE FROM favorites WHERE buyer_id=? AND product_id=?";

    @Override
    public void removeFromFavorites(int buyerId, int productId) {
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(REMOVE)) {

            ps.setInt(1, buyerId);
            ps.setInt(2, productId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

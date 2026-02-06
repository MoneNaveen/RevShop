package com.revshop.dao;

import com.revshop.model.Product;
import com.revshop.util.DBConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {

    private static final String INSERT_PRODUCT =
            "INSERT INTO products (seller_id, name, description, category, price, mrp, discount_price, stock_quantity) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_ALL =
            "SELECT * FROM products";

    private static final String SELECT_BY_CATEGORY =
            "SELECT * FROM products WHERE category = ?";

    private static final String SELECT_BY_ID =
            "SELECT * FROM products WHERE product_id = ?";

    private static final String SELECT_BY_SELLER =
            "SELECT * FROM products WHERE seller_id = ?";

    private static final String UPDATE_STOCK =
            "UPDATE products SET stock_quantity = ? WHERE product_id = ?";

    private static final String REDUCE_STOCK =
            "UPDATE products SET stock_quantity = stock_quantity - ? " +
                    "WHERE product_id = ? AND stock_quantity >= ?";


    private static final String DELETE_PRODUCT =
            "DELETE FROM products WHERE product_id = ?";

    // ================= ADD PRODUCT =================
    @Override
    public boolean addProduct(Product product) {
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_PRODUCT)) {

            ps.setInt(1, product.getSellerId());
            ps.setString(2, product.getName());
            ps.setString(3, product.getDescription());
            ps.setString(4, product.getCategory());
            ps.setDouble(5, product.getPrice());
            ps.setDouble(6, product.getMrp());
            ps.setDouble(7, product.getDiscountPrice());
            ps.setInt(8, product.getStockQuantity());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ================= UPDATE STOCK =================
    @Override
    public boolean updateStock(int productId, int quantity) {
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_STOCK)) {

            ps.setInt(1, quantity);
            ps.setInt(2, productId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ================= FIND BY ID =================
    @Override
    public Product findById(int productId) {
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_ID)) {

            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapProduct(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ================= ALL PRODUCTS =================
    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ALL)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                products.add(mapProduct(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    // ================= BY CATEGORY =================
    @Override
    public List<Product> getProductsByCategory(String category) {
        List<Product> products = new ArrayList<>();

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_CATEGORY)) {

            ps.setString(1, category);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                products.add(mapProduct(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    // ================= BY SELLER =================
    @Override
    public List<Product> getProductsBySeller(int sellerId) {
        List<Product> products = new ArrayList<>();

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_SELLER)) {

            ps.setInt(1, sellerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                products.add(mapProduct(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    // ================== reduce product ================
    @Override
    public boolean reduceStock(int productId, int quantity) {
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(REDUCE_STOCK)) {

            ps.setInt(1, quantity);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    // ================= DELETE PRODUCT =================
    @Override
    public boolean deleteProduct(int productId) {
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE_PRODUCT)) {

            ps.setInt(1, productId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ================= RESULTSET → PRODUCT =================
    private Product mapProduct(ResultSet rs) throws Exception {
        return new Product(
                rs.getInt("product_id"),
                rs.getInt("seller_id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getString("category"),
                rs.getDouble("price"),
                rs.getDouble("mrp"),
                rs.getDouble("discount_price"),
                rs.getInt("stock_quantity")
        );
    }
}

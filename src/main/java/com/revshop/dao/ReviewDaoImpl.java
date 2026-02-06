package com.revshop.dao;

import com.revshop.model.Review;
import com.revshop.util.DBConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDaoImpl implements ReviewDao {

    private static final String INSERT =
            "INSERT INTO reviews (product_id, buyer_id, rating, comment) VALUES (?, ?, ?, ?)";

    private static final String FETCH_BY_PRODUCT =
            "SELECT * FROM reviews WHERE product_id = ? ORDER BY created_at DESC";

    private static final String AVG_RATING =
            "SELECT AVG(rating) FROM reviews WHERE product_id = ?";

    // ================= ADD REVIEW =================
    @Override
    public void addReview(Review review) {

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT)) {

            ps.setInt(1, review.getProductId());
            ps.setInt(2, review.getBuyerId());
            ps.setInt(3, review.getRating());
            ps.setString(4, review.getComment());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= GET REVIEWS BY PRODUCT =================
    @Override
    public List<Review> getReviewsByProduct(int productId) {

        List<Review> reviews = new ArrayList<>();

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(FETCH_BY_PRODUCT)) {

            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                reviews.add(new Review(
                        rs.getInt("product_id"),
                        rs.getInt("buyer_id"),
                        rs.getInt("rating"),
                        rs.getString("comment")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return reviews;
    }

    // ================= GET AVERAGE RATING =================
    @Override
    public double getAverageRating(int productId) {

        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(AVG_RATING)) {

            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}

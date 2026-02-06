package com.revshop.service;

import com.revshop.dao.ReviewDao;
import com.revshop.dao.ReviewDaoImpl;
import com.revshop.model.Review;

import java.util.List;

public class ReviewService {

    private final ReviewDao reviewDao = new ReviewDaoImpl();

    public void addReview(int productId, int buyerId, int rating, String comment) {

        if (rating < 1 || rating > 5) {
            System.out.println("❌ Rating must be between 1 and 5");
            return;
        }

        reviewDao.addReview(new Review(productId, buyerId, rating, comment));
        System.out.println("⭐ Review added successfully");
    }

    public void viewReviews(int productId) {

        List<Review> reviews = reviewDao.getReviewsByProduct(productId);

        if (reviews.isEmpty()) {
            System.out.println("📭 No reviews yet");
            return;
        }

        System.out.println("\n--- REVIEWS ---");
        for (Review r : reviews) {
            System.out.println(
                    "⭐ " + r.getRating() +
                            " | Buyer ID: " + r.getBuyerId() +
                            " | " + r.getComment()
            );
        }
    }

    public double getAverageRating(int productId) {
        return reviewDao.getAverageRating(productId);
    }

}

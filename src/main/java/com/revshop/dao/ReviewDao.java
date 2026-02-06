package com.revshop.dao;

import com.revshop.model.Review;
import java.util.List;

public interface ReviewDao {

    void addReview(Review review);

    List<Review> getReviewsByProduct(int productId);

    double getAverageRating(int productId);
}

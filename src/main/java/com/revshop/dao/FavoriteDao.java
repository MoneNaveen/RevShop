package com.revshop.dao;

import com.revshop.model.Product;
import java.util.List;

public interface FavoriteDao {

    void addToFavorites(int buyerId, int productId);

    List<Product> getFavoritesByBuyer(int buyerId);

    void removeFromFavorites(int buyerId, int productId);

}

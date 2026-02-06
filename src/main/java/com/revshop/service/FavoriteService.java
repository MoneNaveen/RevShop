package com.revshop.service;

import com.revshop.dao.FavoriteDao;
import com.revshop.dao.FavoriteDaoImpl;
import com.revshop.model.Product;

import java.util.List;

public class FavoriteService {

    private final FavoriteDao favoriteDao = new FavoriteDaoImpl();

    public void addToFavorites(int buyerId, int productId) {
        favoriteDao.addToFavorites(buyerId, productId);
        System.out.println("❤️ Added to favorites");
    }

    public List<Product> viewFavorites(int buyerId) {
        return favoriteDao.getFavoritesByBuyer(buyerId);
    }

    public void removeFromFavorites(int buyerId, int productId) {
        favoriteDao.removeFromFavorites(buyerId, productId);
        System.out.println("❌ Removed from favorites");
    }

}

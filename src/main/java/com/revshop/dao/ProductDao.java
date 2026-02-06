package com.revshop.dao;

import com.revshop.model.Product;
import java.util.List;

public interface ProductDao {

    // ================= SELLER OPERATIONS =================

    boolean addProduct(Product product);

    boolean updateStock(int productId, int quantity);

    boolean deleteProduct(int productId);

    List<Product> getProductsBySeller(int sellerId);

    // ================= BUYER OPERATIONS =================

    List<Product> getAllProducts();

    List<Product> getProductsByCategory(String category);

    Product findById(int productId);

    boolean reduceStock(int productId, int quantity);

}

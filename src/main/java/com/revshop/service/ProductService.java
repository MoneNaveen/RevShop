package com.revshop.service;

import com.revshop.dao.ProductDao;
import com.revshop.dao.ProductDaoImpl;
import com.revshop.model.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ProductService {

    private static final Logger logger =
            LogManager.getLogger(ProductService.class);

    private final ProductDao productDao;

    // Production constructor
    public ProductService() {
        this.productDao = new ProductDaoImpl();
    }

    // Constructor for testing / mocking
    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    // ================= SELLER OPERATIONS =================

    public void addProduct(Product product) {
        logger.info("Adding product: {}", product.getName());

        boolean success = productDao.addProduct(product);

        if (success) {
            logger.info("Product added successfully");
            System.out.println("✅ Product added successfully");
        } else {
            logger.error("Failed to add product");
            System.out.println("❌ Failed to add product");
        }
    }

    public void updateStock(int productId, int quantity) {
        logger.info("Updating stock for productId={}, quantity={}", productId, quantity);

        boolean success = productDao.updateStock(productId, quantity);

        if (success) {
            logger.info("Stock updated successfully");
            System.out.println("✅ Stock updated successfully");
        } else {
            logger.error("Failed to update stock");
            System.out.println("❌ Failed to update stock");
        }
    }

    public void deleteProduct(int productId) {
        logger.info("Deleting productId={}", productId);

        boolean success = productDao.deleteProduct(productId);

        if (success) {
            logger.info("Product deleted successfully");
            System.out.println("🗑 Product deleted successfully");
        } else {
            logger.error("Failed to delete product");
            System.out.println("❌ Failed to delete product");
        }
    }

    public List<Product> getProductsBySeller(int sellerId) {
        logger.info("Fetching products for sellerId={}", sellerId);
        return productDao.getProductsBySeller(sellerId);
    }

    // ================= BUYER OPERATIONS =================

    public List<Product> browseAllProducts() {
        logger.info("Browsing all products");
        return productDao.getAllProducts();
    }

    public List<Product> browseByCategory(String category) {
        logger.info("Browsing products by category={}", category);
        return productDao.getProductsByCategory(category);
    }

    public void reduceStock(int productId, int quantity) {
        boolean success = productDao.reduceStock(productId, quantity);

        if (!success) {
            throw new RuntimeException("❌ Insufficient stock for product ID " + productId);
        }
    }

}

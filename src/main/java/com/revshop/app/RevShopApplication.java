package com.revshop.app;

import com.revshop.model.Product;
import com.revshop.model.User;
import com.revshop.notification.NotificationService;
import com.revshop.service.*;

import java.util.List;
import java.util.Scanner;

public class RevShopApplication {

    private static final Scanner sc = new Scanner(System.in);
    private static final UserService userService = new UserService();
    private static final ReviewService reviewService = new ReviewService();

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n===== REVSHOP =====");
            System.out.println("1. Register Buyer");
            System.out.println("2. Register Seller");
            System.out.println("3. Login");
            System.out.println("4. Forgot Password");
            System.out.println("5. Exit");
            System.out.print("Choose: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1 -> {
                    System.out.print("Name: ");
                    String name = sc.nextLine();

                    System.out.print("Email: ");
                    String email = sc.nextLine();

                    System.out.print("Password: ");
                    String pass = sc.nextLine();

                    System.out.println("Choose Security Question:");
                    System.out.println("1. What is your pet name?");
                    System.out.println("2. What is your favorite color?");
                    System.out.println("3. Who was your class 5 teacher?");
                    System.out.print("Choice: ");

                    int q = sc.nextInt();
                    sc.nextLine();

                    String question = switch (q) {
                        case 1 -> "What is your pet name?";
                        case 2 -> "What is your favorite color?";
                        case 3 -> "Who was your class 5 teacher?";
                        default -> "What is your pet name?";
                    };

                    System.out.print("Answer: ");
                    String answer = sc.nextLine();

                    userService.registerUser(
                            name,
                            email,
                            pass,
                            "BUYER",
                            question,
                            answer
                    );
                }


                case 2 -> {
                    System.out.print("Business Name: ");
                    String name = sc.nextLine();

                    System.out.print("Email: ");
                    String email = sc.nextLine();

                    System.out.print("Password: ");
                    String pass = sc.nextLine();

                    System.out.println("Choose Security Question:");
                    System.out.println("1. What is your pet name?");
                    System.out.println("2. What is your favorite color?");
                    System.out.println("3. Who was your class 5 teacher?");
                    System.out.print("Choice: ");

                    int q = sc.nextInt();
                    sc.nextLine();

                    String question = switch (q) {
                        case 1 -> "What is your pet name?";
                        case 2 -> "What is your favorite color?";
                        case 3 -> "Who was your class 5 teacher?";
                        default -> "What is your favorite color?";
                    };

                    System.out.print("Answer: ");
                    String answer = sc.nextLine();

                    userService.registerUser(
                            name,
                            email,
                            pass,
                            "SELLER",
                            question,
                            answer
                    );
                }


                case 3 -> {
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    System.out.print("Password: ");
                    String pass = sc.nextLine();

                    User user = userService.loginUser(email, pass);

                    if (user == null) {
                        System.out.println("❌ Invalid credentials");
                        break;
                    }

                    System.out.println("✅ Login successful");

                    if (user.getRole().equalsIgnoreCase("SELLER")) {
                        sellerMenu(sc, user.getUserId());
                    } else {
                        buyerMenu(sc, user.getEmail());
                    }
                }

                case 4 -> {
                    System.out.print("Email: ");
                    String email = sc.nextLine();

                    User user = userService.getUserByEmail(email);
                    if (user == null) {
                        System.out.println("❌ User not found");
                        break;
                    }

                    System.out.println("🔐 Security Question:");
                    System.out.println(user.getSecurityQuestion());

                    System.out.print("Answer: ");
                    String answer = sc.nextLine();

                    System.out.print("New Password: ");
                    String newPass = sc.nextLine();

                    userService.forgotPassword(email, answer, newPass);
                }


                case 5 -> {
                    System.out.println("👋 Exiting RevShop");
                    System.exit(0);
                }

                default -> System.out.println("❌ Invalid option");
            }
        }
    }

    // ================= BUYER MENU =================
    private static void buyerMenu(Scanner sc, String email) {

        CartService cartService = new CartService();
        OrderService orderService = new OrderService();
        PaymentService paymentService = new PaymentService();
        NotificationService notificationService = new NotificationService();
        ProductService productService = new ProductService();
        FavoriteService favoriteService = new FavoriteService();
        ReviewService reviewService = new ReviewService();

        int buyerId = userService.getUserIdByEmail(email);

        while (true) {
            System.out.println("\n--- BUYER MENU ---");
            System.out.println("1. Browse All Products");
            System.out.println("2. Browse Products by Category");
            System.out.println("3. Add Product to Cart");
            System.out.println("4. View Cart & Checkout");
            System.out.println("5. Add Review & Rating");
            System.out.println("6. View Product Reviews");
            System.out.println("7. Add to Favorites");
            System.out.println("8. View Favorites");
            System.out.println("9. View My Orders");
            System.out.println("10. Logout");
            System.out.print("Choose: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1 -> displayProducts(productService.browseAllProducts());

                case 2 -> {
                    System.out.print("Enter category: ");
                    String category = sc.nextLine();
                    displayProducts(productService.browseByCategory(category));
                }

                case 3 -> {
                    System.out.print("Product ID: ");
                    int pid = sc.nextInt();
                    System.out.print("Quantity: ");
                    int qty = sc.nextInt();
                    sc.nextLine();

                    cartService.addToCart(pid, qty);
                    System.out.println("✅ Added to cart");
                }

                case 4 -> {
                    double total = cartService.getTotal();
                    System.out.println("🛒 Total = ₹" + total);

                    System.out.print("Payment (UPI / CARD / COD): ");
                    String mode = sc.nextLine();

                    paymentService.makePayment(total, mode);

                    orderService.placeOrder(
                            buyerId,
                            email,
                            cartService.getItems(),
                            total
                    );

                    notificationService.notifyOrderPlaced(email);
                    cartService.clearCart();

                    System.out.println("✅ Order completed");
                }

                case 5 -> {
                    System.out.print("Product ID: ");
                    int pid = sc.nextInt();

                    System.out.print("Rating (1–5): ");
                    int rating = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Comment: ");
                    String comment = sc.nextLine();

                    reviewService.addReview(pid, buyerId, rating, comment);
                    System.out.println("✅ Review added");
                }

                case 6 -> {
                    System.out.print("Product ID: ");
                    int pid = sc.nextInt();
                    sc.nextLine();

                    reviewService.viewReviews(pid);
                }

                case 7 -> {
                    System.out.print("Product ID: ");
                    int pid = sc.nextInt();
                    sc.nextLine();

                    try {
                        favoriteService.addToFavorites(buyerId, pid);
                    } catch (Exception e) {
                        System.out.println("❌ " + e.getMessage());
                    }
                }

                case 8 -> {

                    List<Product> favs =
                            favoriteService.viewFavorites(buyerId);

                    if (favs.isEmpty()) {
                        System.out.println("💔 No favorites yet");
                        break;
                    }

                    displayProducts(favs);

                    while (true) {
                        System.out.println("\n--- FAVORITES MENU ---");
                        System.out.println("1. Add to Cart");
                        System.out.println("2. Remove from Favorites");
                        System.out.println("3. Back");
                        System.out.print("Choose: ");

                        int opt = sc.nextInt();
                        sc.nextLine();

                        if (opt == 1) {
                            System.out.print("Product ID: ");
                            int pid = sc.nextInt();
                            System.out.print("Quantity: ");
                            int qty = sc.nextInt();
                            sc.nextLine();

                            cartService.addToCart(pid, qty);
                            System.out.println("✅ Added to cart");

                        } else if (opt == 2) {
                            System.out.print("Product ID: ");
                            int pid = sc.nextInt();
                            sc.nextLine();

                            favoriteService.removeFromFavorites(buyerId, pid);

                        } else {
                            break;
                        }
                    }
                }

                case 9 -> orderService.viewOrdersByBuyer(buyerId);

                case 10 -> {
                    System.out.println("👋 Buyer logged out");
                    return;
                }
            }
        }
    }

    // ================= SELLER MENU =================
    private static void sellerMenu(Scanner sc, int sellerId) {

        ProductService productService = new ProductService();
        OrderService orderService = new OrderService();

        while (true) {
            System.out.println("\n===== SELLER MENU =====");
            System.out.println("1. Add Product");
            System.out.println("2. View My Products");
            System.out.println("3. Update Stock");
            System.out.println("4. Delete Product");
            System.out.println("5. View Orders");
            System.out.println("6. Logout");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1 -> {
                    System.out.print("Product Name: ");
                    String name = sc.nextLine();
                    System.out.print("Description: ");
                    String desc = sc.nextLine();
                    System.out.print("Category: ");
                    String category = sc.nextLine();
                    System.out.print("Price: ");
                    double price = sc.nextDouble();
                    System.out.print("MRP: ");
                    double mrp = sc.nextDouble();
                    System.out.print("Discount Price: ");
                    double discount = sc.nextDouble();
                    System.out.print("Stock Quantity: ");
                    int stock = sc.nextInt();
                    sc.nextLine();

                    productService.addProduct(
                            new Product(0, sellerId, name, desc,
                                    category, price, mrp, discount, stock)
                    );
                }

                case 2 -> displayProducts(
                        productService.getProductsBySeller(sellerId)
                );

                case 3 -> {
                    System.out.print("Product ID: ");
                    int pid = sc.nextInt();
                    System.out.print("New Stock Quantity: ");
                    int qty = sc.nextInt();
                    sc.nextLine();
                    productService.updateStock(pid, qty);
                }

                case 4 -> {
                    System.out.print("Product ID: ");
                    int pid = sc.nextInt();
                    sc.nextLine();
                    productService.deleteProduct(pid);
                }

                case 5 -> orderService.viewOrdersBySeller(sellerId);

                case 6 -> {
                    System.out.println("👋 Seller logged out");
                    return;
                }
            }
        }
    }

    private static void displayProducts(List<Product> products) {

        System.out.println("\n--- PRODUCTS ---");

        if (products.isEmpty()) {
            System.out.println("📭 No products available");
            return;
        }

        for (Product p : products) {

            double rating =
                    reviewService.getAverageRating(p.getProductId());

            String ratingText =
                    rating == 0.0 ? "No ratings yet" : String.format("%.1f ⭐", rating);

            System.out.println(
                    p.getProductId() + " | " +
                            p.getName() + " | " +
                            p.getCategory() + " | ₹" +
                            p.getPrice() + " | Stock: " +
                            p.getStockQuantity() + " | Rating: " +
                            ratingText
            );
        }
    }
}

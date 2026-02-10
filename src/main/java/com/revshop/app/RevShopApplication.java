package com.revshop.app;

import com.revshop.model.Product;
import com.revshop.model.User;
import com.revshop.model.OrderStatus;
import com.revshop.notification.NotificationService;
import com.revshop.service.*;
import com.revshop.util.ConsoleColors;
import java.io.Console;
import java.util.List;
import java.util.Scanner;

public class RevShopApplication {

    private static final Scanner sc = new Scanner(System.in);
    private static final UserService userService = new UserService();
    private static final ReviewService reviewService = new ReviewService();
    private static String lastPaymentMethod = null;

    public static void main(String[] args) {

        while (true) {
            System.out.println(
                    ConsoleColors.CYAN + ConsoleColors.BOLD +
                            "\n════════════ REVSHOP ════════════" +
                            ConsoleColors.RESET
            );
            System.out.println("1. Register Buyer");
            System.out.println("2. Register Seller");
            System.out.println("3. Login");
            System.out.println("4. Forgot Password");
            System.out.println("5. Exit");
            System.out.print(ConsoleColors.YELLOW + "Choose: " + ConsoleColors.RESET);

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> registerUser("BUYER");
                case 2 -> registerUser("SELLER");
                case 3 -> login();
                case 4 -> forgotPassword();
                case 5 -> {
                    System.out.println(ConsoleColors.GREEN + "👋 Exiting RevShop" + ConsoleColors.RESET);
                    System.exit(0);
                }
                default ->
                        System.out.println(ConsoleColors.RED + "❌ Invalid option" + ConsoleColors.RESET);
            }
        }
    }

    // ================= LOGIN =================
    private static void login() {

        System.out.print("Email: ");
        String email = sc.nextLine();
        Console console = System.console();
        String pass;

        if (console != null) {
            char[] passwordChars = console.readPassword("Password: ");
            pass = new String(passwordChars);
        }  else {
            System.out.print("Password: ");
            pass = readPasswordFallback();
        }


        User user = userService.loginUser(email, pass);

        if (user == null) {
            System.out.println(ConsoleColors.RED + "❌ Invalid credentials" + ConsoleColors.RESET);
            return;
        }

        System.out.println(ConsoleColors.GREEN + "✅ Login successful" + ConsoleColors.RESET);

        if (user.getRole().equalsIgnoreCase("SELLER")) {
            sellerMenu(sc, user.getUserId());
        } else {
            buyerMenu(sc, user.getEmail());
        }
    }

    // ================= REGISTER =================
    private static void registerUser(String role) {

        System.out.print(role.equals("SELLER") ? "Business Name: " : "Name: ");
        String name = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        Console console = System.console();
        String pass;

        if (console != null) {
            char[] passwordChars = console.readPassword("Password: ");
            pass = new String(passwordChars);
        }  else {
            System.out.print("Password: ");
            pass = readPasswordFallback(); // ⭐ masked
        }


        System.out.println(ConsoleColors.CYAN + "\nChoose Security Question:" + ConsoleColors.RESET);
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

        userService.registerUser(name, email, pass, role, question, answer);
    }

    // ================= FORGOT PASSWORD =================
    private static void forgotPassword() {

        System.out.print("Email: ");
        String email = sc.nextLine();

        User user = userService.getUserByEmail(email);
        if (user == null) {
            System.out.println(ConsoleColors.RED + "❌ User not found" + ConsoleColors.RESET);
            return;
        }

        System.out.println(ConsoleColors.YELLOW + "🔐 Security Question:" + ConsoleColors.RESET);
        System.out.println(user.getSecurityQuestion());

        System.out.print("Answer: ");
        String answer = sc.nextLine();

        System.out.print("New Password: ");
        String newPass = sc.nextLine();

        userService.forgotPassword(email, answer, newPass);
    }

    // ================= BUYER MENU =================
    private static void buyerMenu(Scanner sc, String email) {

        CartService cartService = new CartService();
        OrderService orderService = new OrderService();
        PaymentService paymentService = new PaymentService();
        NotificationService notificationService = new NotificationService();
        ProductService productService = new ProductService();
        FavoriteService favoriteService = new FavoriteService();

        int buyerId = userService.getUserIdByEmail(email);

        while (true) {
            System.out.println(
                    ConsoleColors.BLUE + ConsoleColors.BOLD +
                            "\n──────── BUYER MENU ────────" +
                            ConsoleColors.RESET
            );
            System.out.println("1. Browse All Products");
            System.out.println("2. Browse Products by Category");
            System.out.println("3. Add Product to Cart");
            System.out.println("4. View Cart & Checkout");
            System.out.println("5. Add Review & Rating");
            System.out.println("6. View Product Reviews");
            System.out.println("7. Add to Favorites");
            System.out.println("8. View Favorites");
            System.out.println("9. View My Orders");
            System.out.println("10. View Notifications");
            System.out.println("11. Logout");
            System.out.print(ConsoleColors.YELLOW + "Choose: " + ConsoleColors.RESET);

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> displayProducts(productService.browseAllProducts());
                case 2 -> {
                    System.out.print("Enter category: ");
                    displayProducts(productService.browseByCategory(sc.nextLine()));
                }
                case 3 -> {
                    System.out.print("Product ID: ");
                    int pid = sc.nextInt();
                    System.out.print("Quantity: ");
                    int qty = sc.nextInt();
                    sc.nextLine();
                    cartService.addToCart(pid, qty);
                    System.out.println(ConsoleColors.GREEN + "✅ Added to cart" + ConsoleColors.RESET);
                }
                case 4 -> {
                    try {
                        double total = cartService.getTotal();
                        System.out.println(
                                ConsoleColors.CYAN + "🛒 Total = ₹" + total + ConsoleColors.RESET
                        );

                        System.out.print("Payment (UPI / CARD / COD): ");
                        String method = sc.nextLine().trim().toUpperCase();

                        // ---------- PAYMENT DETAILS ----------
                        switch (method) {

                            case "UPI" -> {
                                System.out.print("Enter UPI ID: ");
                                String upiId = sc.nextLine();

                                if (!upiId.matches("^[a-zA-Z0-9._-]{2,}@[a-zA-Z]{2,}$")) {
                                    throw new RuntimeException("Invalid UPI ID format");
                                }

                                System.out.println("✔ UPI verified");
                            }

                            case "CARD" -> {
                                System.out.print("Card Holder Name: ");
                                String holderName = sc.nextLine();

                                System.out.print("Card Number (16 digits): ");
                                String cardNumber = sc.nextLine();

                                String cvv = readMaskedInput("CVV: ");

                                if (!cardNumber.matches("\\d{16}")) {
                                    throw new RuntimeException("Invalid card number");
                                }

                                if (!cvv.matches("\\d{3}")) {
                                    throw new RuntimeException("Invalid CVV");
                                }

                                System.out.println("✔ Card verified for " + holderName);
                            }

                            case "COD" -> {
                                System.out.println("✔ Cash on Delivery selected");
                            }

                            default -> throw new RuntimeException(
                                    "Unsupported payment method. Allowed: UPI / CARD / COD"
                            );
                        }
                        // -------------------------------------

                        paymentService.makePayment(total, method);

                        orderService.placeOrder(
                                buyerId,
                                email,
                                cartService.getItems(),
                                total
                        );

                        notificationService.notifyOrderPlaced(buyerId, email);
                        cartService.clearCart();

                        System.out.println(
                                ConsoleColors.GREEN + "✅ Order completed" + ConsoleColors.RESET
                        );

                    } catch (Exception e) {
                        System.out.println(
                                ConsoleColors.RED + "❌ " + e.getMessage() + ConsoleColors.RESET
                        );
                    }
                }

                case 5 -> {
                    System.out.print("Product ID: ");
                    int pid = sc.nextInt();
                    System.out.print("Rating (1–5): ");
                    int rating = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Comment: ");
                    reviewService.addReview(pid, buyerId, rating, sc.nextLine());
                }
                case 6 -> {
                    System.out.print("Product ID: ");
                    reviewService.viewReviews(sc.nextInt());
                    sc.nextLine();
                }
                case 7 -> {
                    System.out.print("Product ID: ");
                    favoriteService.addToFavorites(buyerId, sc.nextInt());
                    sc.nextLine();
                }
                case 8 -> displayProducts(favoriteService.viewFavorites(buyerId));
                case 9 -> orderService.viewOrdersByBuyer(buyerId);
                case 10 -> notificationService.viewBuyerNotifications(buyerId);
                case 11 -> {
                    System.out.println(ConsoleColors.GREEN + "👋 Buyer logged out" + ConsoleColors.RESET);
                    return;
                }
            }
        }
    }

    // ================= SELLER MENU =================
    private static void sellerMenu(Scanner sc, int sellerId) {

        ProductService productService = new ProductService();
        OrderService orderService = new OrderService();
        NotificationService notificationService = new NotificationService();

        while (true) {
            System.out.println(
                    ConsoleColors.PURPLE + ConsoleColors.BOLD +
                            "\n════════ SELLER MENU ════════" +
                            ConsoleColors.RESET
            );
            System.out.println("1. Add Product");
            System.out.println("2. View My Products");
            System.out.println("3. Update Stock");
            System.out.println("4. Delete Product");
            System.out.println("5. View Orders");
            System.out.println("6. Update Order Status");
            System.out.println("7. View Notifications");
            System.out.println("8. Logout");
            System.out.print(ConsoleColors.YELLOW + "Choose: " + ConsoleColors.RESET);

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addProduct(sc, productService, sellerId);
                case 2 -> displayProducts(productService.getProductsBySeller(sellerId));
                case 3 -> {
                    System.out.print("Product ID: ");
                    int pid = sc.nextInt();
                    System.out.print("New Stock Quantity: ");
                    productService.updateStock(pid, sc.nextInt());
                    sc.nextLine();
                }
                case 4 -> {
                    System.out.print("Product ID: ");
                    productService.deleteProduct(sc.nextInt());
                    sc.nextLine();
                }
                case 5 -> orderService.viewOrdersBySeller(sellerId);
                case 6 -> {
                    System.out.print("Order ID: ");
                    int orderId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Status (SHIPPED / DELIVERED): ");
                    orderService.updateOrderStatus(
                            orderId,
                            OrderStatus.valueOf(sc.nextLine().toUpperCase())
                    );
                }
                case 7 -> notificationService.viewSellerNotifications(sellerId);
                case 8 -> {
                    System.out.println(ConsoleColors.GREEN + "👋 Seller logged out" + ConsoleColors.RESET);
                    return;
                }
            }
        }
    }

    // ================= HELPERS =================
    private static void addProduct(Scanner sc, ProductService ps, int sellerId) {

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

        ps.addProduct(new Product(0, sellerId, name, desc, category, price, mrp, discount, stock));
    }

    private static String readPasswordFallback() {
        StringBuilder password = new StringBuilder();

        try {
            while (true) {
                int ch = System.in.read();

                if (ch == '\n' || ch == '\r') {
                    System.out.println();
                    break;
                }

                if (ch == 127 || ch == 8) { // backspace
                    if (password.length() > 0) {
                        password.deleteCharAt(password.length() - 1);
                        System.out.print("\b \b");
                    }
                } else {
                    password.append((char) ch);
                    System.out.print("*");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return password.toString();
    }

    // ================= PAYMENT HELPERS =================

    private static String readMaskedInput(String label) {
        System.out.print(label);
        StringBuilder input = new StringBuilder();

        try {
            while (true) {
                int ch = System.in.read();
                if (ch == '\n' || ch == '\r') {
                    System.out.println();
                    break;
                }
                if (ch == 127 || ch == 8) { // backspace
                    if (input.length() > 0) {
                        input.deleteCharAt(input.length() - 1);
                        System.out.print("\b \b");
                    }
                } else {
                    input.append((char) ch);
                    System.out.print("*");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return input.toString();
    }

    private static boolean isValidUpi(String upi) {
        return upi.matches("^[a-zA-Z0-9._-]{2,}@[a-zA-Z]{2,}$");
    }

    private static boolean isValidCard(String card) {
        return card.matches("\\d{16}");
    }

    private static boolean isValidCvv(String cvv) {
        return cvv.matches("\\d{3}");
    }

    private static void displayProducts(List<Product> products) {

        System.out.println(ConsoleColors.CYAN + "\n--- PRODUCTS ---" + ConsoleColors.RESET);

        if (products.isEmpty()) {
            System.out.println(ConsoleColors.YELLOW + "📭 No products available" + ConsoleColors.RESET);
            return;
        }

        for (Product p : products) {
            double rating = reviewService.getAverageRating(p.getProductId());
            String ratingText = rating == 0 ? "No ratings yet" : String.format("%.1f ⭐", rating);

            System.out.println(
                    ConsoleColors.WHITE +
                            p.getProductId() + " | " +
                            p.getName() + " | " +
                            p.getCategory() + " | ₹" +
                            p.getPrice() + " | Stock: " +
                            p.getStockQuantity() + " | Rating: " +
                            ratingText +
                            ConsoleColors.RESET
            );
        }
    }
}

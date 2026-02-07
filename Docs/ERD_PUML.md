# 📦 3. MODULAR DESIGN EXPLANATION
#### RevShop is organized into well-defined functional modules, each responsible for a single concern.
#### This modular approach improves maintainability, testability, and scalability.
___
## 🧩 Module 1: User & Authentication Module
```text
User/
├── UserService.java        (Register / Login / Forgot Password)
├── UserDao.java            (User persistence contract)
├── UserDaoImpl.java        (JDBC implementation)
├── User.java               (User entity: Buyer & Seller)
└── PasswordUtil.java       (Password hashing & verification)
```
### Responsibilities 

 Buyer & Seller registration

 Secure login using hashed passwords

 Forgot password using security question

 Role identification (BUYER / SELLER)
___
## 🧩 Module 2: Product Management Module
```text
Product/
├── ProductService.java     (Business logic for products)
├── ProductDao.java         (Product DAO interface)
├── ProductDaoImpl.java     (JDBC implementation)
└── Product.java            (Product entity)
```
### Responsibilities

Add / update / delete products (Seller)

Browse products (Buyer)

Category-based filtering

Stock & pricing management

___
## 🧩 Module 3: Shopping Cart Module (In-Memory)
```text
Cart/
├── CartService.java        (Add / remove / calculate cart)
└── CartItem.java           (Cart item model)
```
___
## 🧩 Module 4: Order Processing Module
```text
Order/
├── OrderService.java       (Order placement & retrieval)
├── OrderDao.java           (Order persistence)
├── OrderDaoImpl.java       (JDBC implementation)
├── OrderItemDao.java       (Order-item persistence)
├── OrderItemDaoImpl.java
├── Order.java              (Order entity)
└── OrderItem.java          (Order item entity)
```
### Responsibilities

Checkout flow

Order creation

Order-item mapping

Seller-wise order views

Transaction consistency
___
## 🧩 Module 5: Review & Rating Module
```text
Review/
├── ReviewService.java      (Review business logic)
├── ReviewDao.java          (Review DAO interface)
├── ReviewDaoImpl.java      (JDBC implementation)
└── Review.java             (Review entity)
```
### Responsibilities

Product reviews & ratings

Buyer-specific reviews

Product feedback visibility
___
## 🧩 Module 6: Favorites (Wishlist) Module
```text
Favorites/
├── FavoriteService.java    (Wishlist operations)
├── FavoriteDao.java        (Favorites DAO)
├── FavoriteDaoImpl.java    (JDBC implementation)
└── Favorite.java           (Favorite entity)
```
### Responsibilities

Add/remove favorite products

View favorites

Add favorite items directly to cart
___
## 🧩 Module 7: Payment & Notification Module
```text
Payment & Notification/
├── PaymentService.java     (UPI / CARD / COD simulation)
└── NotificationService.java (Order notifications)
```
### Responsibilities

Payment simulation

Order confirmation messages

Buyer notifications
___
# 📐 4. CLASS DIAGRAM (SIMPLIFIED)
```text
┌──────────────────────┐
│ RevShopApplication   │
├──────────────────────┤
│ +main()              │
│ +buyerMenu()         │
│ +sellerMenu()        │
└───────────┬──────────┘
            │
┌───────────▼──────────┐
│     UserService      │
├──────────────────────┤
│ -userDao             │
│ +registerUser()      │
│ +loginUser()         │
│ +forgotPassword()    │
└───────────┬──────────┘
            │
┌───────────▼──────────┐
│   ProductService     │
├──────────────────────┤
│ -productDao          │
│ +addProduct()        │
│ +browseProducts()    │
└───────────┬──────────┘
            │
┌───────────▼──────────┐
│     OrderService     │
├──────────────────────┤
│ -orderDao            │
│ -orderItemDao        │
│ +placeOrder()        │
│ +viewOrders()        │
└───────────┬──────────┘
            │
┌───────────▼──────────┐
│       DAO Layer      │
├──────────────────────┤
│ UserDao / ProductDao │
│ OrderDao / ReviewDao │
└───────────┬──────────┘
            │
┌───────────▼──────────┐
│ DBConnectionUtil     │
└───────────┬──────────┘
            │
┌───────────▼──────────┐
│ MySQL Database       │
└──────────────────────┘
```
___
# 🧩 5. COMPONENT DIAGRAM
```text
┌──────────────────────────────────────────┐
│          RevShop Console App              │
├──────────────────────────────────────────┤
│  Input Handler → Menu → Services          │
│                                          │
│  ┌──────────────┐  ┌──────────────┐      │
│  │ User Module  │  │ Product Mod  │      │
│  └──────────────┘  └──────────────┘      │
│  ┌──────────────┐  ┌──────────────┐      │
│  │ Order Module │  │ Review Mod   │      │
│  └──────────────┘  └──────────────┘      │
│                                          │
│  ┌──────────────┐                        │
│  │ Favorites    │                        │
│  └──────────────┘                        │
└───────────────┬──────────────────────────┘
                │
┌───────────────▼──────────────────────────┐
│             JDBC DAO Layer                │
└───────────────┬──────────────────────────┘
                │
┌───────────────▼──────────────────────────┐
│              MySQL Database               │
└──────────────────────────────────────────┘
```
___
# ⚡ 6. SEQUENCE DIAGRAM – LOGIN FLOW
```text
User → Menu → UserService → UserDao → MySQL
User ← Menu ← UserService ← UserDao ← MySQL
```
___
# ⚡ 7. SEQUENCE DIAGRAM – PLACE ORDER FLOW
```text
User → CartService → OrderService → OrderDao → MySQL
User ← NotificationService ← OrderService
```
___
# 🚀 8. DEPLOYMENT ARCHITECTURE
```text
Developer Machine
│
├── IntelliJ IDEA
├── Maven Build
├── JVM (Local)
│   └── RevShop Console App
│
└── MySQL (Local Database)
```
___
# 🔐 9. SECURITY ARCHITECTURE
```text
Layer 1: Input Validation
Layer 2: Password Hashing (BCrypt)
Layer 3: Role-Based Access (Buyer/Seller)
Layer 4: Secure JDBC (PreparedStatements)
Layer 5: Logging & Auditing (Log4j2)
```



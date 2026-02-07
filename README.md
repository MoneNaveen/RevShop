# 🛒 RevShop – Console-Based E-Commerce Application (Java + JDBC)

RevShop is a **console-based e-commerce application** developed using **Core Java, JDBC, MySQL, and Maven**, following a clean **layered architecture (Model–DAO–Service–App)**.  
The application simulates a real-world online shopping platform supporting **buyers and sellers**, and is designed to be **easily extendable to web or microservices architecture** in future phases.

---

## 🚀 Features

### 👤 Buyer Features
- Register & login with **secure password hashing**
- Browse all products
- Browse products by **category**
- Add products to cart
- View cart & checkout
- Simulated payment (**UPI / CARD / COD**)
- Place orders
- View order history
- Add **reviews & ratings**
- View product reviews
- Add products to **favorites**
- View favorites & add them to cart
- Receive in-app notifications
- **Forgot password** using security question & answer

---

### 🧑‍💼 Seller Features
- Register & login as seller
- Add products with:
    - Price
    - MRP
    - Discount price
    - Stock quantity
    - Category & description
- View own products
- Update product stock
- Delete products
- View orders placed for their products
- Receive order notifications
- Low-stock alert logic (ready for extension)

---

## 🧱 Project Architecture

```text
Revshop
|── src/
   ├── main/
   │   ├── java/
   │   │   └── com/
   │   │       └── revshop/
   │   │           ├── app/
   │   │           │   └── RevShopApplication.java
   │   │           │
   │   │           ├── config/
   │   │           │   └── DBConfig.java
   │   │           │
   │   │           ├── dao/
   │   │           │   ├── FavoriteDao.java
   │   │           │   ├── FavoriteDaoImpl.java
   │   │           │   ├── OrderDao.java
   │   │           │   ├── OrderDaoImpl.java
   │   │           │   ├── OrderItemDao.java
   │   │           │   ├── OrderItemDaoImpl.java
   │   │           │   ├── ProductDao.java
   │   │           │   ├── ProductDaoImpl.java
   │   │           │   ├── ReviewDao.java
   │   │           │   ├── ReviewDaoImpl.java
   │   │           │   ├── UserDao.java
   │   │           │   └── UserDaoImpl.java
   │   │           │
   │   │           ├── exception/
   │   │           │   ├── CartEmptyException.java
   │   │           │   ├── InsufficientStockException.java
   │   │           │   ├── PaymentFailedException.java
   │   │           │   └── ProductNotFoundException.java
   │   │           │
   │   │           ├── model/
   │   │           │   ├── CartItem.java
   │   │           │   ├── Favorite.java
   │   │           │   ├── Order.java
   │   │           │   ├── OrderItem.java
   │   │           │   ├── Product.java
   │   │           │   ├── Review.java
   │   │           │   └── User.java
   │   │           │
   │   │           ├── notification/
   │   │           │   └── NotificationService.java
   │   │           │
   │   │           ├── service/
   │   │           │   ├── CartService.java
   │   │           │   ├── FavoriteService.java
   │   │           │   ├── OrderService.java
   │   │           │   ├── PaymentService.java
   │   │           │   ├── ProductService.java
   │   │           │   ├── ReviewService.java
   │   │           │   └── UserService.java
   │   │           │
   │   │           └── util/
   │   │               ├── DBConnectionUtil.java
   │   │               └── PasswordUtil.java
   │   │
   │   └── resources/
   │       └── log4j2.xml
   │
   └── test/
       └── java/
           └── com/
               └── revshop/
                   └── service/
                       ├── CartServiceTest.java
                       └── UserServiceTest.java

```

✔ Clean separation of concerns  
✔ Industry-standard layered design  
✔ Easy migration to Spring Boot / REST APIs

---

## 🛠️ Tech Stack

| Technology | Usage |
|----------|------|
| Java (JDK 21) | Core application |
| JDBC | Database access |
| MySQL | Persistent storage |
| Maven | Build & dependency management |
| Log4J2 | Logging |
| JUnit 5 | Unit testing |
| Mockito | Mocking |
| Git & GitHub | Version control |

---

## 🔐 Security
- Password hashing using **BCrypt**
- Secure login validation
- Forgot password flow using **security questions**
- No plain-text password storage

---

## 🧪 Testing
- `UserServiceTest` – registration, login, password validation
- `CartServiceTest` – cart total, empty cart, invalid quantity
- Mockito used to mock DAO layer

#### Run tests:
```bash
mvn test
```
___
### 🗄️ Database

#### Uses MySQL with the following core tables:

```users

products

orders

order_items

reviews

favorites
```
___
#### Database configuration:
```
src/main/java/com/revshop/config/DBConfig.java
```
___
### ▶️ How to Run

#### 1️⃣ Clone Repository
```
git clone https://github.com/MoneNaveen/RevShop.git
```
___
#### 2️⃣ Configure Database

Create MySQL database

Create required tables

Update credentials in DBConfig.java
___

#### 3️⃣ Run Application
```
mvn clean compile
```
Then run:
```
RevShopApplication.java
```
___
## 📈 Future Enhancements

Convert to Spring Boot REST APIs

JWT-based authentication

Web UI (React / Angular)

Admin dashboard

Pagination & sorting

Real payment gateway integration

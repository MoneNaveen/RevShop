package com.revshop.service;

import com.revshop.dao.UserDao;
import com.revshop.dao.UserDaoImpl;
import com.revshop.model.User;
import com.revshop.util.PasswordUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserService {

    private static final Logger logger =
            LogManager.getLogger(UserService.class);

    private final UserDao userDao;

    // Production
    public UserService() {
        this.userDao = new UserDaoImpl();
    }

    // Testing
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    // ================= REGISTER (GENERIC) =================
    public void registerUser(String name, String email, String password,
                             String role, String question, String answer) {

        if (userDao.findByEmail(email) != null) {
            System.out.println("⚠️ User already exists");
            return;
        }

        String hashed = PasswordUtil.hashPassword(password);

        userDao.save(new User(
                name,
                email,
                hashed,
                role,
                question,
                answer
        ));

        System.out.println("✅ " + role + " registered successfully");
    }

    // ================= BACKWARD COMPAT (UI SAFE) =================
    public void registerBuyer(String name, String email, String password) {
        registerUser(
                name,
                email,
                password,
                "BUYER",
                "What is your pet name?",
                "default"
        );
    }

    public void registerSeller(String name, String email, String password) {
        registerUser(
                name,
                email,
                password,
                "SELLER",
                "What is your favorite color?",
                "default"
        );
    }

    // ================= LOGIN =================
    public User loginUser(String email, String password) {

        logger.info("Login attempt for {}", email);

        User user = userDao.findByEmail(email);
        if (user == null) return null;

        if (PasswordUtil.checkPassword(password, user.getPassword())) {
            logger.info("Login successful for {}", email);
            return user;
        }

        logger.warn("Login failed for {}", email);
        return null;
    }

    // ================= FORGOT PASSWORD =================
    public void forgotPassword(String email, String answer, String newPassword) {

        User user = userDao.findByEmail(email);

        if (user == null) {
            System.out.println("❌ User not found");
            return;
        }

        System.out.println("🔐 Security Question:");
        System.out.println(user.getSecurityQuestion());

        if (!user.getSecurityAnswer().equalsIgnoreCase(answer)) {
            System.out.println("❌ Incorrect answer");
            return;
        }

        String hashed = PasswordUtil.hashPassword(newPassword);
        userDao.updatePassword(email, hashed);

        System.out.println("✅ Password reset successful");
    }

    // ================= UTIL =================
    public User getUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    public int getUserIdByEmail(String email) {
        User u = userDao.findByEmail(email);
        return u != null ? u.getUserId() : -1;
    }
}

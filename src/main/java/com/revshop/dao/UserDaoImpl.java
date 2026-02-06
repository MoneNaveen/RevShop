package com.revshop.dao;

import com.revshop.model.User;
import com.revshop.util.DBConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDaoImpl implements UserDao {

    private static final String INSERT_USER =
            "INSERT INTO users (name, email, password, role, security_question, security_answer) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String FIND_BY_EMAIL =
            "SELECT * FROM users WHERE email = ?";

    private static final String UPDATE_PASSWORD =
            "UPDATE users SET password = ? WHERE email = ?";

    @Override
    public boolean save(User user) {
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_USER)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole());
            ps.setString(5, user.getSecurityQuestion());
            ps.setString(6, user.getSecurityAnswer());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User findByEmail(String email) {
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(FIND_BY_EMAIL)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setSecurityQuestion(rs.getString("security_question"));
                user.setSecurityAnswer(rs.getString("security_answer"));
                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updatePassword(String email, String newPassword) {
        try (Connection con = DBConnectionUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_PASSWORD)) {

            ps.setString(1, newPassword);
            ps.setString(2, email);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

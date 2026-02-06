package com.revshop.dao;

import com.revshop.model.User;

public interface UserDao {

    boolean save(User user);

    User findByEmail(String email);

    void updatePassword(String email, String newPassword);
}

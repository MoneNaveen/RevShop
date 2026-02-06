package com.revshop.util;

import com.revshop.config.DBConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionUtil {

    private DBConnectionUtil() {}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DBConfig.URL,
                DBConfig.USERNAME,
                DBConfig.PASSWORD
        );
    }
}

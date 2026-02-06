package com.revshop.config;

public class DBConfig {

    public static final String URL =
            "jdbc:mysql://localhost:3306/revshop"
                    + "?useSSL=false"
                    + "&allowPublicKeyRetrieval=true"
                    + "&serverTimezone=UTC";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "root";
}

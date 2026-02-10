package com.revshop.util;

public class PasswordValidator {

    private static final String PASSWORD_REGEX =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%!^&*()_+]).{8,}$";

    public static boolean isStrong(String password) {
        if (password == null || password.contains(" ")) {
            return false;
        }
        return password.matches(PASSWORD_REGEX);
    }
}

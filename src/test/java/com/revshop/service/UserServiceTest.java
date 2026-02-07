package com.revshop.service;

import com.revshop.dao.UserDao;
import com.revshop.model.User;
import com.revshop.util.PasswordUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserService userService;

    // ================= REGISTER BUYER =================

    @Test
    void testRegisterBuyer_success() {

        when(userDao.findByEmail("buyer@mail.com")).thenReturn(null);

        try (MockedStatic<PasswordUtil> mocked =
                     mockStatic(PasswordUtil.class)) {

            mocked.when(() -> PasswordUtil.hashPassword("1234"))
                    .thenReturn("hashed1234");

            userService.registerBuyer("buyer", "buyer@mail.com", "1234");

            verify(userDao, times(1)).save(any(User.class));
        }
    }

    // ================= LOGIN SUCCESS =================

    @Test
    void testLoginUser_success() {

        User mockUser =
                new User("buyer", "buyer@mail.com", "hashed", "BUYER");

        when(userDao.findByEmail("buyer@mail.com"))
                .thenReturn(mockUser);

        try (MockedStatic<PasswordUtil> mocked =
                     mockStatic(PasswordUtil.class)) {

            mocked.when(() ->
                            PasswordUtil.checkPassword("1234", "hashed"))
                    .thenReturn(true);

            User result =
                    userService.loginUser("buyer@mail.com", "1234");

            assertNotNull(result);
            assertEquals("BUYER", result.getRole());
        }
    }

    // ================= LOGIN FAILURE (WRONG PASSWORD) =================

    @Test
    void testLoginUser_wrongPassword() {

        User mockUser =
                new User("buyer", "buyer@mail.com", "hashed", "BUYER");

        when(userDao.findByEmail("buyer@mail.com"))
                .thenReturn(mockUser);

        try (MockedStatic<PasswordUtil> mocked =
                     mockStatic(PasswordUtil.class)) {

            mocked.when(() ->
                            PasswordUtil.checkPassword("wrong", "hashed"))
                    .thenReturn(false);

            User result =
                    userService.loginUser("buyer@mail.com", "wrong");

            assertNull(result);
        }
    }

    // ================= LOGIN FAILURE (USER NOT FOUND) =================

    @Test
    void testLoginUser_userNotFound() {

        when(userDao.findByEmail("x@mail.com")).thenReturn(null);

        User result =
                userService.loginUser("x@mail.com", "1234");

        assertNull(result);
    }
}

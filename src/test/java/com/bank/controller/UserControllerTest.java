
package com.bank.controller;

import com.bank.model.User;
import com.bank.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void registerShouldReturnUser() {
        User user = mock(User.class);
        LocalDate birthDate = LocalDate.of(2004, 5, 2);

        when(userService.register(
                "123456789012",
                "Ivan",
                "Ivanov",
                birthDate,
                "ivan@example.com",
                "password123"
        )).thenReturn(user);

        User result = userController.register(
                "123456789012",
                "Ivan",
                "Ivanov",
                "2004-05-02",
                "ivan@example.com",
                "password123"
        );

        assertSame(user, result);
    }

    @Test
    void registerShouldFailWhenUserAlreadyExists() {
        when(userService.register(
                "123456789012",
                "Ivan",
                "Ivanov",
                LocalDate.of(2004, 5, 2),
                "ivan@example.com",
                "password123"
        )).thenReturn(null);

        assertThrows(RuntimeException.class,
                () -> userController.register(
                        "123456789012",
                        "Ivan",
                        "Ivanov",
                        "2004-05-02",
                        "ivan@example.com",
                        "password123"
                ));
    }

    @Test
    void loginShouldReturnUser() {
        User user = mock(User.class);

        when(userService.login(
                "ivan@example.com", "password123"
        )).thenReturn(user);

        User result = userController.login(
                "ivan@example.com", "password123"
        );

        assertSame(user, result);
    }

    @Test
    void loginShouldFailWithInvalidCredentials() {
        when(userService.login(
                "ivan@example.com", "wrongpassword"
        )).thenReturn(null);

        assertThrows(RuntimeException.class,
                () -> userController.login(
                        "ivan@example.com", "wrongpassword"
                ));
    }
}

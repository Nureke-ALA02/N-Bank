
package com.bank.service;

import com.bank.model.User;
import com.bank.storage.UserStorage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserStorage userStorage;

    @InjectMocks
    private UserService userService;

    @Test
    void registerShouldReturnUser() {
        when(userStorage.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.register(
                "123456789012",
                "Ivan",
                "Ivanov",
                LocalDate.of(2004, 5, 2),
                "ivan@example.com",
                "password123"
        );

        assertNotNull(result);
        verify(userStorage).save(any(User.class));
    }

    @Test
    void loginShouldReturnUserForValidCredentials() {
    }

    @Test
    void loginShouldReturnNullForInvalidCredentials() {
    }
}

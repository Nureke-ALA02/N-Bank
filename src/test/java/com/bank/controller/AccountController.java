
package com.bank.controller;

import com.bank.model.Account;
import com.bank.model.User;
import com.bank.service.AccountService;
import com.bank.storage.UserStorage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @Mock
    private UserStorage userStorage;

    @InjectMocks
    private AccountController accountController;

    @Test
    void createAccountReturnsCreatedAccount() {
        User user = mock(User.class);
        Account account = mock(Account.class);

        when(userStorage.findById(1L)).thenReturn(user);
        when(accountService.createAccount(user)).thenReturn(account);

        assertSame(account, accountController.createAccount(1L));
        verify(accountService).createAccount(user);
    }

    @Test
    void createAccountFailsWhenUserDoesNotExist() {
        when(userStorage.findById(1L)).thenReturn(null);

        assertThrows(RuntimeException.class,
                () -> accountController.createAccount(1L));

        verify(accountService, never()).createAccount(any());
    }

    @Test
    void getBalanceReturnsBalance() {
        Account account = mock(Account.class);
        BigDecimal balance = new BigDecimal("1500.00");

        when(accountService.getAccount(1L)).thenReturn(account);
        when(account.getBalance()).thenReturn(balance);

        assertEquals(balance, accountController.getBalance(1L));
    }

    @Test
    void depositPassesAmountToService() {
        Account account = mock(Account.class);
        when(accountService.getAccount(1L)).thenReturn(account);

        String result = accountController.deposit(1L, 500.0);

        assertEquals("Счёт пополнен на 500.0 ₸", result);
        verify(accountService).deposit(account, BigDecimal.valueOf(500.0));
    }

    @Test
    void withdrawPassesAmountToService() {
        Account account = mock(Account.class);
        when(accountService.getAccount(1L)).thenReturn(account);

        String result = accountController.withdraw(1L, 200.0);

        assertEquals("Снято 200.0 ₸", result);
        verify(accountService).withdraw(account, BigDecimal.valueOf(200.0));
    }
}

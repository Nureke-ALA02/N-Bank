
package com.bank.service;

import com.bank.model.Account;
import com.bank.model.User;
import com.bank.storage.AccountStorage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountStorage accountStorage;

    @InjectMocks
    private AccountService accountService;

    @Test
    void createAccountShouldReturnAccount() {
        User user = mock(User.class);
        Account account = mock(Account.class);

        when(accountStorage.save(any(Account.class))).thenReturn(account);

        Account result = accountService.createAccount(user);

        assertNotNull(result);
    }

    @Test
    void getAccountShouldReturnAccount() {
        Account account = mock(Account.class);

        when(accountStorage.findById(1L)).thenReturn(account);

        Account result = accountService.getAccount(1L);

        assertSame(account, result);
    }

    @Test
    void depositShouldIncreaseBalance() {
        Account account = mock(Account.class);

        when(account.getBalance()).thenReturn(new BigDecimal("100.00"));

        accountService.deposit(account, new BigDecimal("50.00"));

        verify(account).setBalance(new BigDecimal("150.00"));
    }

    @Test
    void withdrawShouldDecreaseBalance() {
        Account account = mock(Account.class);

        when(account.getBalance()).thenReturn(new BigDecimal("100.00"));

        accountService.withdraw(account, new BigDecimal("30.00"));

        verify(account).setBalance(new BigDecimal("70.00"));
    }
}

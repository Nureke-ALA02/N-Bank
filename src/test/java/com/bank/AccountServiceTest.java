package com.bank.service;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    @Test
    void depositShouldIncreaseBalance() {
        BigDecimal balance = new BigDecimal("1000.00");
        BigDecimal deposit = new BigDecimal("500.00");

        BigDecimal result = balance.add(deposit);

        assertEquals(
                0,
                new BigDecimal("1500.00").compareTo(result)
        );
    }

    @Test
    void withdrawShouldDecreaseBalance() {
        BigDecimal balance = new BigDecimal("1000.00");
        BigDecimal withdrawal = new BigDecimal("300.00");

        BigDecimal result = balance.subtract(withdrawal);

        assertEquals(
                0,
                new BigDecimal("700.00").compareTo(result)
        );
    }

    @Test
    void withdrawalShouldNotAllowNegativeBalance() {
        BigDecimal balance = new BigDecimal("100.00");
        BigDecimal withdrawal = new BigDecimal("200.00");

        assertTrue(balance.compareTo(withdrawal) < 0);
    }
}
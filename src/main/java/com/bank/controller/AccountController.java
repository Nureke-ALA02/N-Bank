
package com.bank.controller;

import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.model.User;
import com.bank.service.AccountService;
import com.bank.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    private final UserService userService;

    public AccountController(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }
    @PostMapping("/create/{userId}")
    public Account createAccount(@PathVariable Long userId) {
        User user = userService.getUserById(userId);

        if (user == null) {throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Пользователь не найден"
            );
        }
        return accountService.createAccount(user);
    }
    @GetMapping("/{id}/balance")
    public BigDecimal getBalance(@PathVariable Long id) {
        Account account = accountService.getAccount(id);

        if (account == null) {throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Счет не найден");
        }
        return account.getBalance();
    }
    @PostMapping("/{id}/deposit")
    public String deposit(@PathVariable Long id, @RequestParam BigDecimal amount) {

        Account account = accountService.getAccount(id);

        if (account == null) {throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Счет не найден");
        }

        boolean success = accountService.deposit(account, amount);

        if (!success) {throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Сумма должна быть больше нуля"
            );
        }

        return "Счет пополнен на " + amount + " ₸";
    }
    @PostMapping("/{id}/withdraw")
    public String withdraw(@PathVariable Long id, @RequestParam BigDecimal amount) {

        Account account = accountService.getAccount(id);

        if (account == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Счет не найден"
            );
        }
        boolean success = accountService.withdraw(account, amount);

        if (!success) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Недостаточно средств или сумма некорректна");
        }
        return "Снято " + amount + " ₸";
    }
    @GetMapping("/{id}/transactions")
    public List<Transaction> getTransactions(@PathVariable Long id) {
        Account account = accountService.getAccount(id);

        if (account == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Счет не найден"
            );
        }
        return accountService.getTransactions(id);
    }
}

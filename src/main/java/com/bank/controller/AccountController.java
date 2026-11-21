
package com.bank.controller;

import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.model.User;
import com.bank.service.AccountService;
import com.bank.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    private final UserService userService;

    public AccountController(
            AccountService accountService,
            UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    public record AccountResponse(Long id, BigDecimal balance) {
    }

    public record TransactionResponse(
            Long id,
            String type,
            BigDecimal amount,
            LocalDateTime createdAt) {
    }

    @PostMapping("/create/{userId}")
    public AccountResponse createAccount(
            @PathVariable Long userId,
            Authentication authentication) {

        User user = userService.getUserById(userId);

        if (user == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Пользователь не найден"
            );
        }

        if (!user.getEmail().equalsIgnoreCase(
                authentication.getName())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Нельзя создавать счёт для другого пользователя"
            );
        }

        return toAccountResponse(accountService.createAccount(user));
    }

    @GetMapping("/{id}/balance")
    public BigDecimal getBalance(
            @PathVariable Long id,
            Authentication authentication) {

        Account account = getOwnedAccount(id, authentication);
        return account.getBalance();
    }

    @PostMapping("/{id}/deposit")
    public String deposit(
            @PathVariable Long id,
            @RequestParam BigDecimal amount,
            Authentication authentication) {

        Account account = getOwnedAccount(id, authentication);

        if (!accountService.deposit(account, amount)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Сумма должна быть больше нуля"
            );
        }

        return "Счёт пополнен на " + amount + " ₸";
    }

    @PostMapping("/{id}/withdraw")
    public String withdraw(
            @PathVariable Long id,
            @RequestParam BigDecimal amount,
            Authentication authentication) {

        Account account = getOwnedAccount(id, authentication);

        if (!accountService.withdraw(account, amount)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Недостаточно средств или сумма некорректна"
            );
        }

        return "Снято " + amount + " ₸";
    }

    @GetMapping("/{id}/transactions")
    public List<TransactionResponse> getTransactions(
            @PathVariable Long id,
            Authentication authentication) {

        getOwnedAccount(id, authentication);

        return accountService.getTransactions(id)
                .stream()
                .map(this::toTransactionResponse)
                .toList();
    }

    private Account getOwnedAccount(
            Long id,
            Authentication authentication) {

        Account account = accountService.getAccount(id);

        if (account == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Счёт не найден"
            );
        }

        if (!account.getOwner().getEmail().equalsIgnoreCase(
                authentication.getName())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Нет доступа к этому счёту"
            );
        }

        return account;
    }

    private AccountResponse toAccountResponse(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getBalance()
        );
    }

    private TransactionResponse toTransactionResponse(
            Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getCreatedAt()
        );
    }
}

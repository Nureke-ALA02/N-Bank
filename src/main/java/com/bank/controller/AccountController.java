package com.bank.controller;
import com.bank.model.Account;
import com.bank.model.User;
import com.bank.service.AccountService;
import com.bank.storage.UserStorage;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")

public class AccountController {
private final AccountService accountService;
private final UserStorage userStorage;

public AccountController(AccountService accountService,UserStorage userStorage){
    this.accountService=accountService;
    this.userStorage=userStorage;
}
@PostMapping("/create/{userId}")
        public Account createAccount (@PathVariable Long userId){
        User user =userStorage.findById(userId);
        if (user=null){
            throw new RuntimeException("Пользователь не найден");
        }
        return accountService.createAccount(user);
    }
    @GetMapping("/{id}/balance")
    public double getBalance(@PathVariable Long id) {

        Account account = accountService.getAccount(id);

        return account.getBalance();
    }

    @PostMapping("/{id}/deposit")
    public String deposit(
            @PathVariable Long id,
            @RequestParam double amount
    ) {
        Account account = accountService.getAccount(id);

        accountService.deposit(account, amount);

        return "Счёт пополнен на " + amount + " ₸";
    }

    @PostMapping("/{id}/withdraw")
    public String withdraw(
            @PathVariable Long id,
            @RequestParam double amount
    ) {
        Account account = accountService.getAccount(id);

        accountService.withdraw(account, amount);

        return "Снято " + amount + " ₸";
    }
}

package com.bank.service;

import com.bank.model.Account;
import com.bank.model.User;
import com.bank.storage.AccountStorage;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountStorage accountStorage;

    private Long nextId = 1L;

    public AccountService(AccountStorage accountStorage) {
        this.accountStorage = accountStorage;
    }

    public Account createAccount(User user) {

        Account oldAccount = accountStorage.findByUserId(user.getId());
        if (oldAccount != null) {
            return oldAccount;
        }
        Account account = new Account(nextId, user, 0);
        nextId++;
        accountStorage.add(account);
        return account;
    }

    public Account getAccount(Long id) {

        return accountStorage.findById(id);
    }

    public boolean deposit(Account account, double amount
    ) {

        if (amount <= 0) {
            System.out.println("Сумма должна быть больше 0.");
            return false;
        }
        account.setBalance(account.getBalance() + amount);
        System.out.println("Счёт пополнен на " + amount + " T");
        return true;
    }
    public boolean withdraw(
            Account account,
            double amount
    ) {

        if (amount <= 0) {

            System.out.println("Сумма должна быть больше 0.");
            return false;
        }
        if (amount>account.getBalance()) {

            System.out.println("Недостаточно средств.");
            return false;
        }
        account.setBalance(account.getBalance() - amount);
        System.out.println("Снято " + amount + " ₸.");
        return true;
    }
}
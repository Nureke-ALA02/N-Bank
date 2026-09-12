package com.bank.service;

import com.bank.model.account;
import com.bank.model.User;
import com.bank.storage.AccountStorage;

public class AccountService {

    private AccountStorage accountStorage;

    private Long nextId = 1L;

    public AccountService(AccountStorage accountStorage) {
        this.accountStorage = accountStorage;
    }

    public account createAccount(User user) {

        account oldAccount = accountStorage.findByUserId(user.getId());
        if (oldAccount != null) {
            return oldAccount;
        }
        account account = new account(nextId, user, 0);
        nextId++;
        accountStorage.add(account);
        return account;
    }

    public boolean deposit(
            account account,
            double amount
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
            account account,
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
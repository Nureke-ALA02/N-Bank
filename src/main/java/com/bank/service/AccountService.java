package com.bank.service;

import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.model.User;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(
            AccountRepository accountRepository,
            TransactionRepository transactionRepository
    ) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public Account createAccount(User user) {
        Account existingAccount =
                accountRepository.findByOwner_Id(user.getId());

        if (existingAccount != null) {
            return existingAccount;
        }

        Account account = new Account(
                null,
                user,
                BigDecimal.ZERO
        );

        return accountRepository.save(account);
    }

    public Account getAccount(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Transactional
    public boolean deposit(Account account, BigDecimal amount) {
        if (amount == null ||
                amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        account.setBalance(account.getBalance());
        accountRepository.save(account);

        Transaction transaction = new Transaction(
                null,
                account,
                "DEPOSIT",
                amount
        );

        transactionRepository.save(transaction);

        return true;
    }

    @Transactional
    public boolean withdraw(Account account, BigDecimal amount) {
        if (amount == null ||
                amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }

        if (amount.compareTo(account.getBalance()) > 0) {
            return false;
        }

        account.setBalance(account.getBalance());
        accountRepository.save(account);

        Transaction transaction = new Transaction(
                null,
                account,
                "WITHDRAW",
                amount
        );

        transactionRepository.save(transaction);

        return true;
    }

    public List<Transaction> getTransactions(Long accountId) {
        return transactionRepository.findByAccount_Id(accountId);
    }
}
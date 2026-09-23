package com.bank.storage;
import com.bank.model.Account;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountStorage {
    private final List<Account> accounts=new ArrayList<>();
     public void add (Account account){
         accounts.add(account);
     }

     public Account findByUserId(Long userId){
         for  (Account account:accounts){
             if (account.getOwner().getId().equals(userId)){
                 return account;
             }
         }
         return null;
     }
    public Account findById(Long id) {
        for (Account account :accounts) {
            if (account.getId().equals(id)) {
                return account;
            }
        }
        return null;
    }
}

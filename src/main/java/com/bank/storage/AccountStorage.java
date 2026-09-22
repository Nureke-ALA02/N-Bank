package com.bank.storage;
import com.bank.model.Account;
import java.util.ArrayList;
import java.util.List;

public class AccountStorage {
    private List<Account> accounts=new ArrayList<>();
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
}

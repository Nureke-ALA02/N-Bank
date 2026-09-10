package com.bank.storage;
import com.bank.model.account;
import java.util.ArrayList;
import java.util.List;

public class accountstorage {
    private List<account> accounts=new ArrayList<>();
     public void add (account account){
         accounts.add(account);
     }

     public account findByUserId(Long userId){
         for  (account account:accounts){
             if (account.getOwner().getId().equals(userId)){
                 return account;
             }
         }
         return null;
     }
}

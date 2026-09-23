package com.bank.storage;
import com.bank.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserStorage {
    private final List<User> users= new ArrayList<>();
    public void add (User user){
        users.add(user);

    }
    public List<User> findAll(){
        return users;
    }

    public User findbyEmail(String email){
        for (User user:users){
            if (user.getEmail().equals(email)){
                return user;
            }
        }
        return null;
    }
    public User findByIin(String iin){
        for (User user : users){
            if(user.getIin().equals(iin)){
                return user;
            }
        }
        return null;
    }
}

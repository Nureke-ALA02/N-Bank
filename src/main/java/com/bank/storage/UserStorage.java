package com.bank.storage;
import com.bank.model.User;
import java.util.ArrayList;
import java.util.List;

public class UserStorage {
    private List<User> users= new ArrayList<>();
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
}

package com.bank.storage;
import com.bank.model.User;
import java.util.ArrayList;
import java.util.List;

public class userStorage {
    private List<User> users= new ArrayList<>();
    public void save (User user){
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

package com.bank.service;
import com.bank.model.User;
import com.bank.storage.UserStorage;

public class UserService {
    private UserStorage userStorage;
    private Long nextId=1L;

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User register(String name,String email,String password){
        User oldUser=userStorage.findbyEmail(email);
            if (oldUser !=null){
                System.out.println("Такой пользователь уже существует");
                return null;
        }
        User user=new User(nextId,name,email,password);
        nextId++;
        userStorage.add(user);
        return user;
    }

    public User login(String email,String password){
        User user=userStorage.findbyEmail(email);
        if(user !=null && user.getPassword().equals(password)){
            return user;
        }
        return null;
    }
}

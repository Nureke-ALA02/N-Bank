package com.bank.service;
import com.bank.model.User;
import com.bank.storage.UserStorage;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class UserService {
    private final UserStorage userStorage;
    private Long nextId=1L;

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User register( String iin, String firstName, String lastName, LocalDate birthDate, String email, String password){
        User oldUser=userStorage.findbyEmail(email);
            if (oldUser !=null){
                System.out.println("Такой пользователь уже существует");
                return null;
        }
            User oldUserByIin=userStorage.findByIin(iin);
            if (oldUser !=null){
                return null;
            }
        User user=new User(nextId, iin, firstName, lastName, birthDate, email, password);
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

package com.bank.controller;
import com.bank.model.User;
import com.bank.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService){
        this.userService=userService;
    }
    @PostMapping("/register")
    public User register( @RequestParam String iin, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String birthDate, @RequestParam String email, @RequestParam String password){
        User user=userService.register(iin,firstName,lastName,LocalDate.parse(birthDate),email,password);
        if(user==null){
            throw new RuntimeException("Email или IIN уже используется");
        }
        return user;
    }
    @PostMapping("/login")
    public User login(@RequestParam String email, @RequestParam String password){
        User user =userService.login(email,password);
        if(user==null){
            throw new RuntimeException("Неверный email или пароль");
        }
        return user;
    }
}


package com.bank.controller;

import com.bank.model.User;
import com.bank.service.UserService;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestParam String iin, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String birthDate, @RequestParam String email, @RequestParam String password) {
        LocalDate date;
        try {
            date = LocalDate.parse(birthDate);
        } catch (DateTimeParseException e) {
            throw new RuntimeException(
                    "Неверный формат даты. Используй YYYY-MM-DD"
            );
        }
        User user = userService.register(iin, firstName, lastName, date, email, password);
        if (user == null) {throw new RuntimeException("Email или ИИН уже используется");}
        return user;
    }
    @PostMapping("/login")
    public User login(@RequestParam String email, @RequestParam String password) {
        User user = userService.login(email, password);
        if (user == null) {throw new RuntimeException("Неверный email или пароль");}
        return user;
    }
}

package com.bank.service;

import com.bank.model.User;
import com.bank.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User register(
            String iin,
            String firstName,
            String lastName,
            LocalDate birthDate,
            String email,
            String password
    ) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException(
                    "Email должен содержать @"
            );
        }

        if (userRepository.findByEmail(email) != null) {
            throw new IllegalArgumentException(
                    "Пользователь с таким email уже существует"
            );
        }

        if (userRepository.findByIin(iin) != null) {
            throw new IllegalArgumentException(
                    "Пользователь с таким ИИН уже существует"
            );
        }

        User user = new User(
                null,
                iin,
                firstName,
                lastName,
                birthDate,
                email,
                password
        );

        return userRepository.save(user);
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            return user;
        }

        return null;
    }
}
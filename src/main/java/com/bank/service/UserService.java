
package com.bank.service;

import com.bank.model.User;
import com.bank.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User register(
            String iin,
            String firstName,
            String lastName,
            LocalDate birthDate,
            String email,
            String password) {

        if (iin == null || !iin.matches("\\d{12}")) {
            throw new IllegalArgumentException(
                    "ИИН должен содержать 12 цифр"
            );
        }

        if (firstName == null || firstName.isBlank()
                || lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException(
                    "Имя и фамилия обязательны"
            );
        }

        if (email == null
                || !email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            throw new IllegalArgumentException(
                    "Введите корректный email"
            );
        }

        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException(
                    "Пароль должен содержать минимум 8 символов"
            );
        }

        if (birthDate == null) {
            throw new IllegalArgumentException(
                    "Дата рождения обязательна"
            );
        }

        email = email.trim().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException(
                    "Пользователь с таким email уже существует"
            );
        }

        if (userRepository.existsByIin(iin)) {
            throw new IllegalArgumentException(
                    "Пользователь с таким ИИН уже существует"
            );
        }

        User user = new User(
                null,
                iin,
                firstName.trim(),
                lastName.trim(),
                birthDate,
                email,
                passwordEncoder.encode(password)
        );

        return userRepository.save(user);
    }

    public User login(String email, String password) {
        if (email == null || password == null) {
            return null;
        }

        User user = userRepository.findByEmail(
                email.trim().toLowerCase()
        ).orElse(null);

        if (user != null
                && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }

        return null;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}

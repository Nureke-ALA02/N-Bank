
package com.bank.controller;

import com.bank.model.User;
import com.bank.repository.UserRepository;
import com.bank.security.BankUserDetailsService;
import com.bank.security.JwtService;
import com.bank.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final BankUserDetailsService userDetailsService;
    private final JwtService jwtService;

    public AuthController(
            UserService userService,
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            BankUserDetailsService userDetailsService,
            JwtService jwtService
    ) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    public record RegisterRequest(
            String iin,
            String firstName,
            String lastName,
            String birthDate,
            String email,
            String password
    ) {
    }

    public record LoginRequest(
            String email,
            String password
    ) {
    }

    public record AuthResponse(
            String token,
            String email,
            String name
    ) {
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse register(@RequestBody RegisterRequest request) {

        if (request == null
                || isBlank(request.iin())
                || isBlank(request.firstName())
                || isBlank(request.lastName())
                || isBlank(request.birthDate())
                || isBlank(request.email())
                || isBlank(request.password())) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Заполните все обязательные поля"
            );
        }

        if (!request.email().contains("@")) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Введите корректный email"
            );
        }

        if (request.password().length() < 8) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Пароль должен содержать минимум 8 символов"
            );
        }

        LocalDate birthDate;

        try {
            birthDate = LocalDate.parse(request.birthDate());
        } catch (DateTimeParseException exception) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Дата рождения должна иметь формат YYYY-MM-DD"
            );
        }

        try {
            User user = userService.register(
                    request.iin(),
                    request.firstName(),
                    request.lastName(),
                    birthDate,
                    request.email(),
                    request.password()
            );

            return new AuthResponse(
                    null,
                    user.getEmail(),
                    user.getFirstname() + " " + user.getLastname()
            );

        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    exception.getMessage()
            );
        }
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {

        if (request == null
                || isBlank(request.email())
                || isBlank(request.password())) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Введите email и пароль"
            );
        }

        try {
            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    request.email(),
                                    request.password()
                            )
                    );

            UserDetails userDetails =
                    (UserDetails) authentication.getPrincipal();

            // JwtService.generateToken() принимает email типа String.
            String token = jwtService.generateToken(
                    userDetails.getUsername()
            );

            User user = userRepository.findByEmail(request.email())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.UNAUTHORIZED,
                            "Неверный email или пароль"
                    ));

            return new AuthResponse(
                    token,
                    user.getEmail(),
                    user.getFirstname() + " " + user.getLastname()
            );

        } catch (AuthenticationException exception) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Неверный email или пароль"
            );
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}

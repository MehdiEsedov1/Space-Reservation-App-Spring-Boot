package com.example.spacereservationappspringboot.controller;

import com.example.spacereservationappspringboot.dto.request.UserRequestDTO;
import com.example.spacereservationappspringboot.service.AuthService;
import com.example.spacereservationappspringboot.token.Token;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid UserRequestDTO userRequest) {
        return authService.signup(userRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody @Valid UserRequestDTO userRequest) {
        return authService.login(userRequest);
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> confirmUser(@RequestParam("token") String token) {
        authService.confirmUser(token);
        return ResponseEntity.ok("User confirmed successfully");
    }
}

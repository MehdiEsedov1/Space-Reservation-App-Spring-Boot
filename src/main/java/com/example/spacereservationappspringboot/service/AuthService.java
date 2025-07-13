package com.example.spacereservationappspringboot.service;

import com.example.spacereservationappspringboot.dto.request.UserRequestDTO;
import com.example.spacereservationappspringboot.entity.UserEntity;
import com.example.spacereservationappspringboot.mail.MailServiceImpl;
import com.example.spacereservationappspringboot.mapper.UserMapper;
import com.example.spacereservationappspringboot.repository.TokenRepository;
import com.example.spacereservationappspringboot.repository.UserRepository;
import com.example.spacereservationappspringboot.security.JWTGenerator;
import com.example.spacereservationappspringboot.token.ConfirmationToken;
import com.example.spacereservationappspringboot.token.Token;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepo;
    private final UserMapper userMapper;
    private final MailServiceImpl mailService;
    private final TokenRepository tokenRepo;
    private final PasswordEncoder passwordEncoder;
    private final JWTGenerator jwtGenerator;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> signup(@Valid UserRequestDTO userRequest) {
        if (userRepo.existsByUsername(userRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Username is taken");
        } else if (userRepo.existsByEmail(userRequest.getEmail())) {
            return ResponseEntity.badRequest().body("E-mail is taken");
        }

        String rawPassword = userRequest.getPassword();
        userRequest.setPassword(passwordEncoder.encode(rawPassword));
        addUser(userRequest);

        List<GrantedAuthority> authorities = new ArrayList<>();
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userRequest.getUsername(), rawPassword, authorities
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtGenerator.generateToken(authentication);

        Token token = new Token();
        token.setToken(jwt);
        return ResponseEntity.ok(token);
    }

    public ResponseEntity<Token> login(@Valid UserRequestDTO userRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userRequest.getUsername(),
                        userRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtGenerator.generateToken(authentication);

        Token token = new Token();
        token.setToken(jwt);
        return ResponseEntity.ok(token);
    }

    public void confirmUser(String token) {
        ConfirmationToken confirmationToken = tokenRepo.findConfirmationTokenByToken(token);
        if (confirmationToken != null) {
            UserEntity user = userRepo.findByEmail(confirmationToken.getEmail()).orElseThrow();
            user.setIsEnable(true);
            userRepo.save(user);
        }
    }

    private void addUser(UserRequestDTO userRequest) {
        String token = UUID.randomUUID().toString();
        String confirmationLink = "http://localhost:8090/api/auth/confirm?token=" + token;

        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setEmail(userRequest.getEmail());
        confirmationToken.setToken(token);

        tokenRepo.save(confirmationToken);

        mailService.sendSimpleEmail(userRequest.getEmail(), "Confirm your registration",
                "Please confirm your registration by clicking the following link: " + confirmationLink);

        userRepo.save(userMapper.mapper(userRequest));
    }
}

package com.example.spacereservationappspringboot.mapper;

import com.example.spacereservationappspringboot.dto.request.UserRequestDTO;
import com.example.spacereservationappspringboot.entity.Role;
import com.example.spacereservationappspringboot.entity.UserEntity;
import com.example.spacereservationappspringboot.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserMapper {
    public final RoleRepository roleRepo;

    public UserEntity mapper(UserRequestDTO userRequest) {
        Role role = roleRepo.findByName("USER").get();

        return UserEntity.builder()
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .roles(Collections.singletonList(role))
                .password(userRequest.getPassword())
                .isEnable(false)
                .build();
    }
}

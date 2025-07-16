package com.example.spacereservationappspringboot.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserRequestDTO {
    public String username;
    public String email;
    public String password;
}

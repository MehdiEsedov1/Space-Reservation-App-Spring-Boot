package com.example.spacereservationappspringboot.repository;

import com.example.spacereservationappspringboot.token.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<ConfirmationToken, Long> {
    public ConfirmationToken findConfirmationTokenByToken(String email);
}

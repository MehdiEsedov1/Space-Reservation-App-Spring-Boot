package com.example.spacereservationappspringboot.security;

import com.example.spacereservationappspringboot.entity.Role;
import com.example.spacereservationappspringboot.entity.UserEntity;
import com.example.spacereservationappspringboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!user.getIsEnable()) {
            throw new UsernameNotFoundException("User account is not activated");
        }

        return new User(user.getUsername(), user.getPassword(), mapRolesToAuth(user.getRoles()));
    }

    public Collection<GrantedAuthority> mapRolesToAuth(List<Role> roleList) {
        return roleList.stream().map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}

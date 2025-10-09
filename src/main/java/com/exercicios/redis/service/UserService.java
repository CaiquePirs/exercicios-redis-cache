package com.exercicios.redis.service;

import com.exercicios.redis.controller.dto.UserRequestDTO;
import com.exercicios.redis.controller.dto.UserResponseDTO;
import com.exercicios.redis.model.User;
import com.exercicios.redis.model.UserRoles;
import com.exercicios.redis.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User create(UserRequestDTO dto){
        userRepository.findByEmail(dto.email()).ifPresent(u -> {
            throw new RuntimeException(
                    String.format("User email: %s already exist", dto.email()));
        });

        User user = User.builder()
                .username(dto.username())
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .roles(List.of(UserRoles.ADMIN))
                .build();

        return userRepository.save(user);
    }

    public User findUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Cacheable(value = "users", key = "#id")
    public UserResponseDTO findUserById(UUID id) {
        return userRepository.findById(id)
                .map(u -> UserResponseDTO.builder()
                        .id(u.getId())
                        .username(u.getUsername())
                        .email(u.getEmail())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User ID not found"));
    }

}

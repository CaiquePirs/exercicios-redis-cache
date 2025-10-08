package com.exercicios.redis.controller.dto;

import com.exercicios.redis.model.UserRoles;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record UserResponseDTO(
        UUID id,
        String username,
        String email,
        List<UserRoles> roles) {
}

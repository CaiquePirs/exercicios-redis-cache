package com.exercicios.redis.controller.dto;

public record UserRequestDTO(
        String username,
        String email,
        String password) {
}

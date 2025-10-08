package com.exercicios.redis.controller.advice.dto;

import java.time.LocalDateTime;

public record ErrorResponseDTO(
        Integer status,
        String error,
        LocalDateTime timeStamp) {
}

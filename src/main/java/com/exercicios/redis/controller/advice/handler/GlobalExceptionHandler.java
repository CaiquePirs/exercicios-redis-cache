package com.exercicios.redis.controller.advice.handler;

import com.exercicios.redis.controller.advice.dto.ErrorResponseDTO;
import com.exercicios.redis.controller.advice.exceptions.RateLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RateLimitExceededException.class)
    public ResponseEntity<ErrorResponseDTO> handlerRateLimit(RateLimitExceededException e){
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .body(new ErrorResponseDTO(
                        HttpStatus.TOO_MANY_REQUESTS.value(),
                        e.getMessage() + "ID: ",
                        LocalDateTime.now()
                ));
    }
}

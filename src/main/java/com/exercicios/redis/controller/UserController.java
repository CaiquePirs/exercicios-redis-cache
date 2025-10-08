package com.exercicios.redis.controller;

import com.exercicios.redis.controller.dto.UserLoginDTO;
import com.exercicios.redis.controller.dto.UserRequestDTO;
import com.exercicios.redis.controller.dto.UserResponseDTO;
import com.exercicios.redis.event.pubsub.RedisPublisher;
import com.exercicios.redis.model.User;
import com.exercicios.redis.service.UserAuthService;
import com.exercicios.redis.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final HttpServletRequest request;
    private final UserAuthService userAuthService;
    private final RedisPublisher redisPublisher;

    @PostMapping("/auth/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRequestDTO userRequestDTO){
        User userEntity = userService.create(userRequestDTO);

        UserResponseDTO userResponse = UserResponseDTO.builder()
                .id(userEntity.getId())
                .username(userEntity.getName())
                .email(userEntity.getEmail())
                .roles(userEntity.getRoles())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDTO loginDTO){
        return ResponseEntity.ok(userAuthService.login(
                loginDTO.email(),
                loginDTO.password())
        );
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> getMyProfile(){
        String currentUserLoggedId = request.getAttribute("id").toString();
        UUID userId = UUID.fromString(currentUserLoggedId);

        return ResponseEntity.ok(userService.findUserById(userId));
    }

    @PostMapping("/sendMessage")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> publisherMessage(@RequestBody String message){
        redisPublisher.publish(message);
        return ResponseEntity.noContent().build();
    }
}

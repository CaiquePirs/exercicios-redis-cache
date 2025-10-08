package com.exercicios.redis.service;

import com.exercicios.redis.model.User;
import com.exercicios.redis.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class UserAuthService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public String login(String email, String password) {
        String accessKey = String.format("user:%s", email);

        String cachedAccessToken = (String) redisTemplate.opsForValue().get(accessKey);

        if (cachedAccessToken != null) {
            jwtService.validateToken(cachedAccessToken);
            return cachedAccessToken;
        }

        User user = userService.findUserByEmail(email);
        isValidPassword(password, user.getPassword());

        String token = jwtService.createToken(user);

        redisTemplate.opsForValue().set(accessKey, token, Duration.ofMinutes(10));
        return token;
    }

    private void isValidPassword(String loginPassword, String userPassword) {
        boolean passwordMatches = passwordEncoder.matches(loginPassword, userPassword);
        if (!passwordMatches) {
            throw new RuntimeException("Invalid password. Please try again.");
        }
    }


}

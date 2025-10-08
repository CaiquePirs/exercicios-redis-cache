package com.exercicios.redis.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.exercicios.redis.model.User;
import com.exercicios.redis.model.UserRoles;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class JwtService {

    private final String SECRET = "SECRET_KEY_EXAMPLE";
    private final Algorithm algorithm = Algorithm.HMAC256(SECRET);

    public String createToken(User user){
       return JWT.create()
                .withSubject(user.getId().toString())
                .withClaim("roles", user.getRoles().stream().map(UserRoles::toString).toList())
                .withExpiresAt(Instant.now().plus(Duration.ofHours(10)))
                .sign(algorithm);
    }

    public DecodedJWT validateToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }

        try {
            return JWT.require(algorithm)
                    .build()
                    .verify(token);
        } catch (JWTVerificationException e) {
            return null;
        }
    }
}

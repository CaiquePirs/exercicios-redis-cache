package com.exercicios.redis.security.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.exercicios.redis.controller.advice.exceptions.RateLimitExceededException;
import com.exercicios.redis.service.RedisService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final RedisService redisService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null) {
            var token = jwtService.validateToken(header);
            if (token == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            request.setAttribute("id", token.getSubject());
            var auth = getAuthenticationToken(token);

            try {
                redisService.checkRateLimit(request.getAttribute("id").toString());

            } catch (RateLimitExceededException e) {
                handlerExceptionResolver.resolveException(request, response, null, e);
                return;
            }
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(DecodedJWT token) {
        return new UsernamePasswordAuthenticationToken(token.getSubject(), null,
                token.getClaim("roles").asList(Object.class)
                        .stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase()))
                        .toList()
        );
    }
}

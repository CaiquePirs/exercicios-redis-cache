package com.exercicios.redis.service;

import com.exercicios.redis.controller.advice.exceptions.RateLimitExceededException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisService {

    private final static int LIMIT = 100;
    private final static int WINDOW_SECONDS = 60;
    private final RedisTemplate<String, String> redisTemplate;

    public void checkRateLimit(String userId) {
        String accessKey = "user:" + userId + ":requests";

        Long requests = redisTemplate.opsForValue().increment(accessKey);

        if (requests != null) {
            if (requests == 1) {
                redisTemplate.expire(accessKey, WINDOW_SECONDS, TimeUnit.SECONDS);
            }

            boolean beAllowedToMakeRequests = requests <= LIMIT;

            if (!beAllowedToMakeRequests) {
                throw new RateLimitExceededException("Rate limit exceeded. Try again later. ");
            }

        }
    }
}

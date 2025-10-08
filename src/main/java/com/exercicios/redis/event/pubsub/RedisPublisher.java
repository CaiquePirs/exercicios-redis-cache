package com.exercicios.redis.event.pubsub;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisPublisher {

    private final StringRedisTemplate redisTemplate;

    public void publish(String message) {
        redisTemplate.convertAndSend("chat", message);
        System.out.println("Publicando messagem: " + message);
    }
}
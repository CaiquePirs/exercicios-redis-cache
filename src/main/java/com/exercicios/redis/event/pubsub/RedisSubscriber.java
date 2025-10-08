package com.exercicios.redis.event.pubsub;

import org.springframework.stereotype.Component;

@Component
public class RedisSubscriber {

    public void onMessage(String message) {
        System.out.println("Message received: " + message);
    }
}
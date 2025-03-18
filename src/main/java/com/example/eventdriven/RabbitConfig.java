package com.example.eventdriven;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String USER_EVENT_QUEUE = "user-profile";

    @Bean
    public Queue userEventQueue() {
        return new Queue(USER_EVENT_QUEUE, true, false, false);
    }
}

package com.example.eventdriven;

import com.example.eventdriven.model.UserRabbit;
import com.example.eventdriven.model.UserRest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserEventListener {

    private final UserService userService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitConfig.USER_EVENT_QUEUE)
    public void handleUserEvent(String event) {
        try {
            UserRabbit eventUserRest = objectMapper.readValue(event, UserRabbit.class);
            System.out.println("📩 Receive event to rabbit: " + eventUserRest);

            userService.updateOrCreateUser(eventUserRest);
        } catch (Exception e) {
            System.err.println("❌ Error while processing message: " + e.getMessage());
        }
    }
}

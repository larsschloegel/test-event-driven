package com.example.eventdriven;

import com.example.eventdriven.model.UserDTO;
import com.example.eventdriven.model.UserType;
import com.example.eventdriven.repo.entities.UserRabbit;
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
            UserDTO eventUserRest = objectMapper.readValue(event, UserDTO.class);
            System.out.println("📩 Receive event to rabbit: " + eventUserRest);

            userService.updateOrCreateUser(eventUserRest, UserType.RABBIT);
        } catch (Exception e) {
            System.err.println("❌ Error while processing message: " + e.getMessage());
        }
    }
}

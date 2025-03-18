package com.example.eventdriven;

import com.example.eventdriven.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class UserEventListener {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitConfig.USER_EVENT_QUEUE)
    public void handleUserEvent(String event) {
        try {
            User eventUser = objectMapper.readValue(event, User.class);
            System.out.println("📩 Receive event: " + eventUser);

            Optional<User> existingUser = userRepository.findByEmail(eventUser.getEmail());
            if (existingUser.isPresent()) {
                User user = existingUser.get();
                user.setFirstName(eventUser.getFirstName());
                user.setLastName(eventUser.getLastName());
                user.setPayload(eventUser.getPayload());
                userRepository.save(user);
                System.out.println("Update User: " + user.getId());
            } else {
                User newUser = new User();
                newUser.setFirstName(eventUser.getFirstName());
                newUser.setLastName(eventUser.getLastName());
                newUser.setEmail(eventUser.getEmail());
                newUser.setPayload(eventUser.getPayload());
                userRepository.save(newUser);
                System.out.println("Save new User: " + newUser.getId());
            }
        } catch (Exception e) {
            System.err.println("❌ Error while processing message: " + e.getMessage());
        }
    }
}

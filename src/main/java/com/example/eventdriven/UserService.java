package com.example.eventdriven;

import com.example.eventdriven.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void updateOrCreateUser(User eventUser) {
        Optional<User> existingUser = userRepository.findByEmail(eventUser.getEmail());
        if (existingUser.isPresent()) {
            // Update des bestehenden Users
            User user = existingUser.get();
            user.setFirstName(eventUser.getFirstName());
            user.setLastName(eventUser.getLastName());
            user.setPayload(eventUser.getPayload());
            userRepository.save(user);
            System.out.println("Update User: " + user.getId());
        } else {
            // Neuen User anlegen
            User newUser = new User();
            newUser.setFirstName(eventUser.getFirstName());
            newUser.setLastName(eventUser.getLastName());
            newUser.setEmail(eventUser.getEmail());
            newUser.setPayload(eventUser.getPayload());
            userRepository.save(newUser);
            System.out.println("Save new User: " + newUser.getId());
        }
    }
}

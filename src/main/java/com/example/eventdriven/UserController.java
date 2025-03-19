package com.example.eventdriven;

import com.example.eventdriven.model.UserDTO;
import com.example.eventdriven.model.UserType;
import com.example.eventdriven.repo.entities.UserRest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final ObjectMapper objectMapper;

    @PostMapping("/user-event")
    public ResponseEntity<String> handleUserEvent(@RequestBody String event) {
        try {
            UserDTO eventUserRest = objectMapper.readValue(event, UserDTO.class);
            System.out.println("📩 Receive event to controller: " + eventUserRest);

            userService.updateOrCreateUser(eventUserRest, UserType.CONTROLLER);
            return ResponseEntity.ok("User processed successfully");
        } catch (Exception e) {
            System.err.println("❌ Error while processing message: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }
}

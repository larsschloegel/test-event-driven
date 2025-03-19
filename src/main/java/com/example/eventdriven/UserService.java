package com.example.eventdriven;

import com.example.eventdriven.model.UserDTO;
import com.example.eventdriven.model.UserType;
import com.example.eventdriven.repo.entities.UserRabbit;
import com.example.eventdriven.repo.entities.UserRest;
import com.example.eventdriven.repo.UserRabbitRepository;
import com.example.eventdriven.repo.UserRestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRestRepository userRestRepository;
    private final UserRabbitRepository userRabbitRepository;
    private final UserMapper userMapper;

    @Transactional
    public void updateOrCreateUser(UserDTO eventUser, UserType userType) {
        if (userType.equals(UserType.CONTROLLER)) {
            UserRest restUser = userMapper.mapToUserRest(eventUser);
            handleUserRest(restUser);
        } else if (userType.equals(UserType.RABBIT)) {
            UserRabbit rabbit = userMapper.mapToUserRabbit(eventUser);
            handleUserRabbit(rabbit);
        } else {
            throw new IllegalArgumentException("Unsupported user type: " + eventUser.getClass().getSimpleName());
        }
    }


    void handleUserRest(UserRest eventUserRest) {
        System.out.println("Rest method");
        Optional<UserRest> existingUser = userRestRepository.findByEmail(eventUserRest.getEmail());
        if (existingUser.isPresent()) {
            UserRest userRest = existingUser.get();
            userRest.setFirstName(eventUserRest.getFirstName());
            userRest.setLastName(eventUserRest.getLastName());
            userRest.setPayload(eventUserRest.getPayload());
            userRestRepository.save(userRest);
            System.out.println("Updated User (REST): " + userRest.getId());
        } else {
            userRestRepository.save(eventUserRest);
            System.out.println("Saved new User (REST): " + eventUserRest.getId());
        }
    }

    void handleUserRabbit(UserRabbit eventUserRabbit) {
        System.out.println("Rabbit method");
        Optional<UserRabbit> existingUser = userRabbitRepository.findByEmail(eventUserRabbit.getEmail());
        if (existingUser.isPresent()) {
            UserRabbit userRabbit = existingUser.get();
            userRabbit.setFirstName(eventUserRabbit.getFirstName());
            userRabbit.setLastName(eventUserRabbit.getLastName());
            userRabbit.setPayload(eventUserRabbit.getPayload());
            userRabbitRepository.save(userRabbit);
            System.out.println("Updated User (RabbitMQ): " + userRabbit.getId());
        } else {
            userRabbitRepository.save(eventUserRabbit);
            System.out.println("Saved new User (RabbitMQ): " + eventUserRabbit.getId());
        }
    }
}

package com.example.eventdriven;

import com.example.eventdriven.model.UserDTO;
import com.example.eventdriven.repo.entities.UserRabbit;
import com.example.eventdriven.repo.entities.UserRest;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserRest mapToUserRest(UserDTO dto) {
        UserRest entity = new UserRest();
        entity.setEmail(dto.getEmail());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPayload(dto.getPayload());
        return entity;
    }


    public UserRabbit mapToUserRabbit(UserDTO dto) {
        UserRabbit entity = new UserRabbit();
        entity.setEmail(dto.getEmail());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPayload(dto.getPayload());
        return entity;
    }
}

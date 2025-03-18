package com.example.eventdriven;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class UserEvent implements Serializable {
    private String firstName;
    private String lastName;
    private String email;
    private String payload;

    public UserEvent(String firstName, String lastName, String email, String payload) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.payload = payload;
    }

}

package com.example.clmp.dto;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.*;

@Setter
@Getter
public class ContactDTO {
    private Long id;

    @NotBlank(message =  "First name is a required field")
    private String firstName;

    @NotBlank(message = "Last name is a required field")
    private String lastName;

    @NotBlank(message = "Phone number is a required field")
    private String phoneNumber;

    @Email(message = "Invalid email address")
    @NotBlank(message = "Last name is a required field")
    private String email;

    private String address;

    private Date dateCreated;

    @Override
    public String toString() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            // Handle the exception, e.g., log it or return a default string
            return "Error converting object to JSON string";
        }
    }

}

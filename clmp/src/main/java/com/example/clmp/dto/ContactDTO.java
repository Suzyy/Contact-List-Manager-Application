package com.example.clmp.dto;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class ContactDTO {
    private Long id;

    @NotBlank(message =  "First name is a required field")
    private String firstName;

    @NotBlank(message = "Last name is a required field")
    private String lastName;

    @Email(message = "Invalid email address")
    private String email;

    private String address;

    private Date dateCreated;
}
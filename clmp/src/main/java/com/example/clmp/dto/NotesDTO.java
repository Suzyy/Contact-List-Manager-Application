package com.example.clmp.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.clmp.model.Contact;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.*;

@Setter
@Getter
public class NotesDTO {
    private Long id;

    @NotNull(message = "Contact id is required")
    private Long contactId;

    @NotBlank(message = "Note text is required")
    @Size(max = 255, message = "Note text cannot exceed 255 characters")
    private String noteText;

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

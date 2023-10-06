package com.example.clmp.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.clmp.model.Contact;

import lombok.*;

@Setter
@Getter
public class NotesDTO {
    private Long id;

    @NotNull(message = "Contact is required")
    private Contact contact;

    @NotBlank(message = "Note text is required")
    @Size(max = 255, message = "Note text cannot exceed 255 characters")
    private String noteText;

    private Date dateCreated;
}

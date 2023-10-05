package com.example.clmp.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.*;


@Entity
@Table(name="Contact")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class Contact {

    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String address;

    private LocalDateTime dateCreated;
    
}

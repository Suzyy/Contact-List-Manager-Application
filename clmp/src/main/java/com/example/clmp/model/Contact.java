package com.example.clmp.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String address;

    @Column(name="date_created")
    private LocalDateTime dateCreated;
    
}

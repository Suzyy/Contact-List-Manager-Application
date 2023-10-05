package com.example.clmp.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.*;


@Entity
@Table(name="Contact")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="contact_id")
    private Contact contact;

    private String noteText;

    @Column(name="date_created")
    private LocalDateTime dateCreated;
    
}

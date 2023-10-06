package com.example.clmp.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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

    @OneToOne
    @JoinColumn(name="contact_id")
    private Contact contact;

    private String noteText;

    @Column(name="date_created")
    private LocalDateTime dateCreated;
    
}

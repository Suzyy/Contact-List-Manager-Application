package com.example.clmp.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.*;


@Entity
@Table(name="Contact")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EntityListeners(AuditingEntityListener.class)

public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String address;

    @CreatedDate
    @Column(name="date_created", nullable = false, updatable = false)
    private Date dateCreated;
}

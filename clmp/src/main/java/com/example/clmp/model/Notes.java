package com.example.clmp.model;

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
@Table(name="Notes")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EntityListeners(AuditingEntityListener.class)

public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long contactId;

    private String noteText;

    @CreatedDate
    @Column(name="date_created", nullable = false, updatable = false)
    private Date dateCreated;
    
}

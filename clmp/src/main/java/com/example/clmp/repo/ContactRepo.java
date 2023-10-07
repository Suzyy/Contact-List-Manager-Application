package com.example.clmp.repo;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.clmp.entity.Contact;

@Repository
public interface ContactRepo extends JpaRepository<Contact, Long>{
    //Custom query methods - Accepts pageable parameter that allows pagination and sorting
    //ex) Pageable pageable = PageRequest.of(0, 5, Sort.by("lastName"));
    //    List<Contact> contacts = contactRepo.findByFirstName("Suzy", pageable);
    
    List<Contact> findByFirstName(String firstName, Pageable pageable);
    List<Contact> findByLastName(String lastName, Pageable pageable);
}

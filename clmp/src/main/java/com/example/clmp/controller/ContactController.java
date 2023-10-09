package com.example.clmp.controller;

import com.example.clmp.service.ContactService;
import com.example.clmp.util.JwtUtil;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;

import com.example.clmp.dto.ContactDTO;
import com.example.clmp.entity.AuthRequest;
import com.example.clmp.exception.ContactNotFoundException;
import com.example.clmp.exception.ContactNotValidException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/contacts")  //Version Controlling: V1 - Can add different versions when needed
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    //Rate limiting. API should allow only 50 request in a minute
    //This can be used in pricing plan for each API client
    private final Bucket bucket;

    public ContactController() {
        Bandwidth limit = Bandwidth.classic(50, Refill.greedy(50, Duration.ofMinutes(1)));
        this.bucket = Bucket4j.builder().addLimit(limit).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllContacts")
    public ResponseEntity<List<ContactDTO>> getAllContacts() {
        try {
            List<ContactDTO> contactDTOList = contactService.getAllContacts();

            if (contactDTOList.isEmpty()) {
                return new ResponseEntity<>(contactDTOList, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(contactDTOList, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/getContactById/{id}")
    public ResponseEntity<ContactDTO> getContactById(@PathVariable Long id) {
        try {
            Optional<ContactDTO> contactDTOData = contactService.getContactById(id);
            return new ResponseEntity<>(contactDTOData.get(), HttpStatus.OK);
        } catch (ContactNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping("/addContact")
    public ResponseEntity<ContactDTO> addContact(@RequestBody ContactDTO contactDTO) {
        System.out.println("Received request body: " + contactDTO.toString());
        try {
            ContactDTO contactObj = contactService.addContact(contactDTO);
            return new ResponseEntity<>(contactObj, HttpStatus.OK);
        } catch (ContactNotValidException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping("/updateContactById/{id}")
    public ResponseEntity<ContactDTO> updateContactById(@PathVariable Long id, @RequestBody ContactDTO newContactDTO) {
        try {
            Optional<ContactDTO> updatedContactDTOData = contactService.updateContactById(id, newContactDTO);
            return new ResponseEntity<>(updatedContactDTOData.get(), HttpStatus.OK);
        } catch (ContactNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @DeleteMapping("/deleteContactById/{id}")
    public ResponseEntity<HttpStatus> deleteContactById(@PathVariable Long id) {
        try {
            contactService.deleteContactById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ContactNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

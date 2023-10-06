package com.example.clmp.controller;

import com.example.clmp.service.ContactService;
import com.example.clmp.dto.ContactDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ContactController {

    @Autowired
    private ContactService contactService;

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

    @GetMapping("/getContactById/{id}")
    public ResponseEntity<ContactDTO> getContactById(@PathVariable Long id) {
        Optional<ContactDTO> contactDTOData = contactService.getContactById(id);

        if (contactDTOData.isPresent()) {
            return new ResponseEntity<>(contactDTOData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addContact")
    public ResponseEntity<ContactDTO> addContact(@RequestBody ContactDTO contactDTO) {
        try {
            ContactDTO contactObj = contactService.addContact(contactDTO);
            return new ResponseEntity<>(contactObj, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/updateContactById/{id}")
    public ResponseEntity<ContactDTO> updateContactById(@PathVariable Long id, @RequestBody ContactDTO newContactDTO) {
        Optional<ContactDTO> updatedContactDTOData = contactService.updateContactById(id, newContactDTO);

        if (updatedContactDTOData.isPresent()){
            return new ResponseEntity<>(updatedContactDTOData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<ContactDTO>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteContactById/{id}")
    public ResponseEntity<HttpStatus> deleteContactById(@PathVariable Long id) {
        contactService.deleteContactById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

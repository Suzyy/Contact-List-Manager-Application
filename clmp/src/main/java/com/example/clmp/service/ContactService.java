package com.example.clmp.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.example.clmp.repo.ContactRepo;
import com.example.clmp.dto.ContactDTO;
import com.example.clmp.model.Contact;


@Service
@Validated
public class ContactService {

    @Autowired
    private ContactRepo contactRepo;

    public List<ContactDTO> getAllContacts() {
        List<Contact> contactList = contactRepo.findAll();
        return contactList.stream().map(this::convertToDTO).collect(Collectors.toList());
    
    }

    public Optional<ContactDTO> getContactById(Long id) {
        Optional<Contact> contactData = contactRepo.findById(id);

        if (contactData.isPresent()) {
            return Optional.of(convertToDTO(contactData.get()));
        } else {
            return Optional.empty();
        }
     
    }

    public ContactDTO addContact(ContactDTO contactDTO) {
        Contact contact = convertToEntity(contactDTO);
        Contact savedContact = contactRepo.save(contact);

        return convertToDTO(savedContact);
    }

    public Optional<ContactDTO> updateContactById(Long id, ContactDTO newContactDTO) {
        Optional<Contact> oldContactData = contactRepo.findById(id);
        Contact newContactData = convertToEntity(newContactDTO);

        if (oldContactData.isPresent()) {
            Contact updatedContactData = oldContactData.get();
            updatedContactData.setFirstName(newContactData.getFirstName());
            updatedContactData.setLastName(newContactData.getLastName());
            updatedContactData.setEmail(newContactData.getEmail());
            updatedContactData.setAddress(newContactData.getAddress());
            updatedContactData.setDateCreated(newContactData.getDateCreated());

            Contact savedContact = contactRepo.save(updatedContactData);
            return Optional.of(convertToDTO(savedContact));
        } else {
            return Optional.empty();
        }
    }

    public void deleteContactById(Long id) {
        contactRepo.deleteById(id);
    }

    //Helper Function: Converting entity to DTO
    private ContactDTO convertToDTO(Contact contact) {
        ContactDTO contactDTO = new ContactDTO();

        contactDTO.setId(contact.getId());
        contactDTO.setFirstName(contact.getFirstName());
        contactDTO.setLastName(contact.getLastName());
        contactDTO.setEmail(contact.getEmail());
        contactDTO.setAddress(contact.getAddress());
        contactDTO.setDateCreated(contact.getDateCreated());
        
        return contactDTO;
    }
    //Helper Function: Converting DTO to entity
    private Contact convertToEntity(ContactDTO contactDTO) {
        Contact contact = new Contact();

        contact.setId(contactDTO.getId());
        contact.setFirstName(contactDTO.getFirstName());
        contact.setLastName(contactDTO.getLastName());
        contact.setEmail(contactDTO.getEmail());
        contact.setAddress(contactDTO.getAddress());
        contact.setDateCreated(contactDTO.getDateCreated());

        return contact;
    }
    
}

package com.example.clmp.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.example.clmp.repo.ContactRepo;
import com.example.clmp.dto.ContactDTO;
import com.example.clmp.entity.Contact;
import com.example.clmp.exception.ContactNotFoundException;
import com.example.clmp.exception.ContactNotValidException;


@Service
@Validated
public class ContactService {

    @Autowired
    private ContactRepo contactRepo;

    //DESCRIPTION: Getting all contacts and returning a list of contactDTOs to the controller
    public List<ContactDTO> getAllContacts() {
        List<Contact> contactList = contactRepo.findAll();
        return contactList.stream().map(this::convertToDTO).collect(Collectors.toList());
    
    }

    //DESCRIPTION: Getting contact by specific contact id
    public Optional<ContactDTO> getContactById(Long id) {
        Optional<Contact> contactData = contactRepo.findById(id);

        //If contact exists, return contactDtO to the controller
        if (contactData.isPresent()) {
            return Optional.of(convertToDTO(contactData.get()));
        } else { //If contact does not exist, throw contact not found exception
            throw new ContactNotFoundException("Contact not found with ID: " + id);
        }
    }

    //DESCRIPTION: Adding contact to the contact list database
    public ContactDTO addContact(ContactDTO contactDTO) {
        //Validation check: check if required fields are not null
        //If required fiedls are null, throw contact not valid exception
        if (contactDTO.getFirstName() == null || contactDTO.getLastName() == null || contactDTO.getPhoneNumber() == null || contactDTO.getEmail() == null) {
            throw new ContactNotValidException("Contact data is not valid. First name, last name, and email are required.");
        }
        Contact contact = convertToEntity(contactDTO);
        Contact savedContact = contactRepo.save(contact);

        return convertToDTO(savedContact);
    }

    //DESCRIPTION: Updating contact by specific contact id
    public Optional<ContactDTO> updateContactById(Long id, ContactDTO newContactDTO) {
        Optional<Contact> oldContactData = contactRepo.findById(id);
        Contact newContactData = convertToEntity(newContactDTO);

        //If contact with the specific id exists, update fields and return saved contactDTO to controller
        if (oldContactData.isPresent()) {
            Contact updatedContactData = oldContactData.get();
            updatedContactData.setFirstName(newContactData.getFirstName());
            updatedContactData.setLastName(newContactData.getLastName());
            updatedContactData.setPhoneNumber(newContactData.getPhoneNumber());
            updatedContactData.setEmail(newContactData.getEmail());
            updatedContactData.setAddress(newContactData.getAddress());
            updatedContactData.setDateCreated(newContactData.getDateCreated());

            Contact savedContact = contactRepo.save(updatedContactData);
            return Optional.of(convertToDTO(savedContact));
        } else {
            //exception where contact is not found with the id given: no contact to update
            throw new ContactNotFoundException("Contact not found with ID: " + id);
        }
    }

    //DESCRIPTION: Deleting contact by specific contact id
    public void deleteContactById(Long id) {
        Optional<Contact> contactData = contactRepo.findById(id);
        
        //If contact with the specific id exists, delete contact
        if (contactData.isPresent()) {
            contactRepo.deleteById(id);
        } else {
            //exception where contact is not found with the id given: no contact to delete
            throw new ContactNotFoundException("Contact not found with ID: " + id);
        }
    }

    //Helper Function: Converting entity to DTO
    private ContactDTO convertToDTO(Contact contact) {
        ContactDTO contactDTO = new ContactDTO();

        contactDTO.setId(contact.getId());
        contactDTO.setFirstName(contact.getFirstName());
        contactDTO.setLastName(contact.getLastName());
        contactDTO.setPhoneNumber(contact.getPhoneNumber());
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
        contact.setPhoneNumber(contactDTO.getPhoneNumber());
        contact.setEmail(contactDTO.getEmail());
        contact.setAddress(contactDTO.getAddress());
        contact.setDateCreated(contactDTO.getDateCreated());

        return contact;
    }
    
}

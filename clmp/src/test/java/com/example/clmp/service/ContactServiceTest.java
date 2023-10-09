package com.example.clmp.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.clmp.repo.ContactRepo;
import com.example.clmp.dto.ContactDTO;
import com.example.clmp.entity.Contact;
import com.example.clmp.exception.ContactNotFoundException;
import com.example.clmp.exception.ContactNotValidException;

@SpringBootTest
public class ContactServiceTest {
    
    @Mock
    private ContactRepo contactRepo;

    @InjectMocks
    private ContactService contactService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //getAllContact
    @Test
    public void testGetAllContact() {
        //Creating mock contacts
        List<Contact> mockContactList = new ArrayList<>();
        mockContactList.add(new Contact(null, "Suzy", "Lee", "000-000-0000","suzy@example.com", "123 Yonge St", null));
        mockContactList.add(new Contact(null, "Jay", "Lee", "111-111-1111","jay@example.com", "456 Yonge St", null));

        //Mocking findAll() method in repo
        when(contactRepo.findAll()).thenReturn(mockContactList);

        //Calling contact service
        List<ContactDTO> contactDTOs = contactService.getAllContacts();

        //Verifying contactDOTs contain expected contact info
        assertEquals(2, contactDTOs.size());
        assertEquals("Suzy", contactDTOs.get(0).getFirstName());
        assertEquals("Lee", contactDTOs.get(0).getLastName());
        assertEquals("000-000-0000", contactDTOs.get(0).getPhoneNumber());
        assertEquals("suzy@example.com", contactDTOs.get(0).getEmail());
        assertEquals("123 Yonge St", contactDTOs.get(0).getAddress());
        assertEquals("Jay", contactDTOs.get(1).getFirstName());
        assertEquals("Lee", contactDTOs.get(1).getLastName());
        assertEquals("111-111-1111", contactDTOs.get(1).getPhoneNumber());
        assertEquals("jay@example.com", contactDTOs.get(1).getEmail());
        assertEquals("456 Yonge St", contactDTOs.get(1).getAddress());
    }

    //getContactById
    @Test
    public void testGetContactById_ContactFound() {

        Contact mockContact = new Contact(1L, "Suzy", "Lee", "000-000-0000","suzy@example.com", "123 Yonge St", null);

        //Mocking findById() method in repo
        when(contactRepo.findById(1L)).thenReturn(Optional.of(mockContact));

        Optional<ContactDTO> contactDTO = contactService.getContactById(1L);

        //Verifying contactDTO contains expected contact info
        assertTrue(contactDTO.isPresent());
        assertEquals("Suzy", contactDTO.orElse(null).getFirstName());
        assertEquals("Lee", contactDTO.orElse(null).getLastName());
        assertEquals("suzy@example.com", contactDTO.orElse(null).getEmail());
        assertEquals("123 Yonge St", contactDTO.orElse(null).getAddress());
    }

    @Test
    public void testGetContactById_ContactNotFound() {

        when(contactRepo.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ContactNotFoundException.class, () -> contactService.getContactById(anyLong()));
    }

    //addContact
    @Test
    public void testAddContact_DataValid() {
        
        ContactDTO mockContactDTO = new ContactDTO();
        mockContactDTO.setFirstName("Suzy");
        mockContactDTO.setLastName("Lee");
        mockContactDTO.setPhoneNumber("000-000-0000");
        mockContactDTO.setEmail("suzy@example.com");

        //Mocking save() method in repo
        when(contactRepo.save(any(Contact.class))).thenAnswer(invocation -> {
            Contact savedContact = invocation.getArgument(0);
            //Simulating Id generation during save
            savedContact.setId(1L);
            return savedContact;
        });

        ContactDTO savedContactDTO = contactService.addContact(mockContactDTO);

        assertNotNull(savedContactDTO.getId());
        assertEquals("Suzy", savedContactDTO.getFirstName());
        assertEquals("Lee", savedContactDTO.getLastName());
        assertEquals("000-000-0000", savedContactDTO.getPhoneNumber());
        assertEquals("suzy@example.com", savedContactDTO.getEmail());
    }

    @Test
    public void testAddContact_DataInvalid() {

        //Only setting first name and other required fields to null
        ContactDTO mockContactDTO = new ContactDTO();
        mockContactDTO.setFirstName("Suzy");;

        assertThrows(ContactNotValidException.class, () -> contactService.addContact(mockContactDTO));
    }

    //updateContactById
    @Test
    public void testUpdateContactById_ContactFound() {

        Contact mockContact = new Contact(1L, "Suzy", "Lee", "000-000-0000","suzy@example.com", "123 Yonge St", null);

        when(contactRepo.findById(1L)).thenReturn(Optional.of(mockContact));
        when(contactRepo.save(any(Contact.class))).thenReturn(mockContact);

        ContactDTO newContactDTO = new ContactDTO();
        newContactDTO.setId(1L);
        newContactDTO.setFirstName("Jay");
        newContactDTO.setLastName("Lee");
        newContactDTO.setPhoneNumber("111-111-1111");
        newContactDTO.setEmail("jay@example.com");

        Optional<ContactDTO> updateContact = contactService.updateContactById(1L, newContactDTO);

        assertTrue(updateContact.isPresent());
        assertEquals("Jay", updateContact.get().getFirstName());
        assertEquals("Lee", updateContact.get().getLastName());
        assertEquals("111-111-1111", updateContact.get().getPhoneNumber());
        assertEquals("jay@example.com", updateContact.get().getEmail());
    }

    @Test
    public void testUpdateContactById_ContactNotFound() {

        when(contactRepo.findById(1L)).thenReturn(Optional.empty());

        ContactDTO newContactDTO = new ContactDTO();
        newContactDTO.setId(1L);
        newContactDTO.setFirstName("Jay");
        newContactDTO.setLastName("Lee");
        newContactDTO.setPhoneNumber("111-111-1111");
        newContactDTO.setEmail("jay@example.com");

        assertThrows(ContactNotFoundException.class, () -> contactService.updateContactById(1L, newContactDTO));
    }

    //deleteContactById
    @Test
    public void testDeleteContactById_ContactFound() {

        when(contactRepo.findById(1L)).thenReturn(Optional.of(new Contact()));

        contactService.deleteContactById(1L);

        //Verifying that delete method in the repo was invoced
        verify(contactRepo, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteContactById_ContactNotFound() {

        when(contactRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ContactNotFoundException.class, () -> contactService.deleteContactById(1L));
    }

}

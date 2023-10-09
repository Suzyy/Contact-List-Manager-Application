package com.example.clmp.service;

import static org.junit.Assert.assertEquals;
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

    @Test
    public void testGetAllContact() {
        //Creating mock contacts
        List<Contact> mockContactList = new ArrayList<>();
        mockContactList.add(new Contact(null, "Suzy", "Lee", "suzy@example.com", "123 Yonge St", null));
        mockContactList.add(new Contact(null, "Jay", "Lee", "jay@example.com", "456 Yonge St", null));

        //Mocking findAll() method in repo
        when(contactRepo.findAll()).thenReturn(mockContactList);

        //Calling contact service
        List<ContactDTO> contactDTOs = contactService.getAllContacts();

        //Verifying contactDOTs contain expected contact info
        assertEquals(2, contactDTOs.size());
        assertEquals("Suzy", contactDTOs.get(0).getFirstName());
        assertEquals("Lee", contactDTOs.get(0).getLastName());
        assertEquals("suzy@example.com", contactDTOs.get(0).getEmail());
        assertEquals("123 Yonge St", contactDTOs.get(0).getAddress());
        assertEquals("Jay", contactDTOs.get(1).getFirstName());
        assertEquals("Lee", contactDTOs.get(1).getLastName());
        assertEquals("jay@example.com", contactDTOs.get(1).getEmail());
        assertEquals("456 Yonge St", contactDTOs.get(1).getAddress());
    }

    @Test
    public void testGetContactById_ContactFound() {

        Contact mockContact = new Contact(1L, "Suzy", "Lee", "suzy@example.com", "123 Yonge St", null);

        //Mocking findById() method in repo
        when(contactRepo.findById(1L)).thenReturn(Optional.of(mockContact));

        Optional<ContactDTO> contactDTO = contactService.getContactById(1L);

        assertTrue(contactDTO.isPresent());
        assertEquals("Suzy", contactDTO.orElse(null).getFirstName());
        assertEquals("Lee", contactDTO.orElse(null).getLastName());
        assertEquals("suzy@example.com", contactDTO.orElse(null).getEmail());
        assertEquals("123 Yonge St", contactDTO.orElse(null).getAddress());
    }


}

package com.example.clmp.controller;

import java.util.List;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import com.example.clmp.dto.ContactDTO;
import com.example.clmp.entity.User;
import com.example.clmp.repo.ContactRepo;
import com.example.clmp.repo.UserRepo;
import com.example.clmp.service.ContactService;
import com.example.clmp.service.CustomUserDetailsService;
import com.example.clmp.util.JwtUtil;

@SpringBootTest
@AutoConfigureMockMvc
public class ContactControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ContactService contactService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepo userRepo;

    @BeforeEach
    public void setUp() {
        userRepo.save(new User(1, "admin", "password", "ROLE_ADMIN"));
        userRepo.save(new User(2, "user", "password", "ROLE_USER"));
    }

    @Test
    @WithUserDetails("admin")
    public void testGetAllContacts() throws Exception {
        //Generating jwt token
        UserDetails userDetails = userDetailsService.loadUserByUsername("admin");
        String token = jwtUtil.generateToken(userDetails);

        //Mocking getAllContact in contactService
        ContactDTO contactDTO_1 = new ContactDTO();
        ContactDTO contactDTO_2 = new ContactDTO();
        contactDTO_1.setId(1L);
        contactDTO_1.setFirstName("Suzy");
        contactDTO_1.setLastName("Lee");
        contactDTO_1.setPhoneNumber("000-000-0000");
        contactDTO_1.setEmail("suzy@example.com");
        contactDTO_1.setAddress("123 Yonge St");
        contactDTO_2.setId(1L);
        contactDTO_2.setFirstName("Jay");
        contactDTO_2.setLastName("Lee");
        contactDTO_2.setPhoneNumber("111-111-1111");
        contactDTO_2.setEmail("jay@example.com");
        contactDTO_2.setAddress("456 Yonge St");
        
        List<ContactDTO> contactList = List.of(
            contactDTO_1, contactDTO_2
        );
        when(contactService.getAllContacts()).thenReturn(contactList);

        //GET request to the endpoint w/ generated jwt token in the 'Authorization' header.
        
    }


}

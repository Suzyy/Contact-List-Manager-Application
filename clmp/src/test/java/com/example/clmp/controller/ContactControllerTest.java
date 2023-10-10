package com.example.clmp.controller;

import java.util.List;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.clmp.dto.ContactDTO;
import com.example.clmp.entity.User;
import com.example.clmp.filter.JwtFilter;
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

    @Mock
    private ContactRepo contactRepo;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired JwtFilter jwtFilter;

    @Autowired
    private UserRepo userRepo;

    @Mock
    private ContactService contactService;

    @InjectMocks
    private ContactController contactController;

    @BeforeEach
    public void setUp() {
        userRepo.save(new User(1, "admin", "password", "ROLE_ADMIN"));
        userRepo.save(new User(2, "user", "password", "ROLE_USER"));
    }

    
    @Test
    //@WithUserDetails("admin")
    @WithMockUser(username = "admin", password = "password", roles = {"ADMIN"})
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
        System.out.println("Contact List: " + contactList);
        //DEBUG: contact list is not empty but contactService.getAllContacts() is returning an empty list.
        when(contactService.getAllContacts()).thenReturn(contactList);

        //GET request to the endpoint w/ generated jwt token in the 'Authorization' header.
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/v1/contacts/getAllContacts")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer " + token))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(contactList.size()))
            .andExpect(MockMvcResultMatchers.jsonPath("[0].id").value(contactList.get(0).getId()))
            .andExpect(MockMvcResultMatchers.jsonPath("[0].firstName").value(contactList.get(0).getFirstName()))
            .andExpect(MockMvcResultMatchers.jsonPath("[0].lastName").value(contactList.get(0).getLastName()))
            .andExpect(MockMvcResultMatchers.jsonPath("[0].phoneNumber").value(contactList.get(0).getPhoneNumber()))
            .andExpect(MockMvcResultMatchers.jsonPath("[0].email").value(contactList.get(0).getEmail()))
            .andExpect(MockMvcResultMatchers.jsonPath("[0].address").value(contactList.get(0).getAddress()))
            .andExpect(MockMvcResultMatchers.jsonPath("[1].id").value(contactList.get(1).getId()))
            .andExpect(MockMvcResultMatchers.jsonPath("[1].firstName").value(contactList.get(1).getFirstName()))
            .andExpect(MockMvcResultMatchers.jsonPath("[1].lastName").value(contactList.get(1).getLastName()))
            .andExpect(MockMvcResultMatchers.jsonPath("[1].phoneNumber").value(contactList.get(1).getPhoneNumber()))
            .andExpect(MockMvcResultMatchers.jsonPath("[1].email").value(contactList.get(1).getEmail()))
            .andExpect(MockMvcResultMatchers.jsonPath("[1].address").value(contactList.get(1).getAddress())) 
            .andReturn();

        //DEBUG: Response is empty. Seems like contactService.getAllContacts is not invoked correctly.
        System.out.println("RESULT: "+ result.getResponse().getContentAsString());
        
        verify(contactService).getAllContacts();

    } 


}

package com.example.clmp.service;

import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.clmp.repo.ContactRepo;

@SpringBootTest
public class ContactServiceTest {
    
    @Mock
    private ContactRepo contactRepo;

    @InjectMocks
    private ContactService contactService;
}

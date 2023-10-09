package com.example.clmp.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.clmp.dto.NotesDTO;
import com.example.clmp.entity.Notes;
import com.example.clmp.exception.NotesNotFoundException;
import com.example.clmp.repo.NotesRepo;

@SpringBootTest
public class NotesServiceTest {

    @Mock
    private NotesRepo notesRepo;

    @InjectMocks
    private NotesService notesService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //getNotesById
    @Test
    public void testGetNotesById_NotesFound() {

        Long notesId = 1L;
        Long contactId = 2L;
        Notes mockNotes = new Notes();
        mockNotes.setId(notesId);
        mockNotes.setNoteText("This is a test note.");
        mockNotes.setContactId(contactId);

        when(notesRepo.findById(notesId)).thenReturn(Optional.of(mockNotes));

        Optional<NotesDTO> notesDTO = notesService.getNotesById(notesId);

        assertTrue(notesDTO.isPresent());
        assertEquals("This is a test note.", notesDTO.get().getNoteText());
    }

    @Test
    public void testGetNotesById_NotesNotFound() {
        
        when(notesRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotesNotFoundException.class, () -> notesService.getNotesById(1L));
    }



}

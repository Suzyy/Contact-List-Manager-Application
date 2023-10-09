package com.example.clmp.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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
import com.example.clmp.exception.NotesNotValidException;
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
        Notes mockNotes = new Notes(notesId, contactId, "This is a test note.", null);

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

    //addNotes
    @Test
    public void testAddNotes_DataValid() {

        Long contactId = 2L;
        NotesDTO mockNotesDTO = new NotesDTO();
        mockNotesDTO.setNoteText("This is a test note.");
        mockNotesDTO.setContactId(contactId);

        when(notesRepo.save(any(Notes.class))).thenAnswer(invocation -> {
            Notes savedNotes = invocation.getArgument(0);
            //Simulating Id generation during save
            savedNotes.setId(1L);
            return savedNotes;
        });

        NotesDTO savedNotesDTO = notesService.addNotes(mockNotesDTO);

        //Verifying notesDTO has an id and contain expected notes info
        assertNotNull(savedNotesDTO.getId());
        assertEquals("This is a test note.", savedNotesDTO.getNoteText());
        assertEquals(contactId, savedNotesDTO.getContactId());
    }

    @Test
    public void testAddNotes_DataInvalid() {

        //Required fields are set to null
        NotesDTO notesDTO = new NotesDTO();

        assertThrows(NotesNotValidException.class, () -> notesService.addNotes(notesDTO));
    }

    //updateNotesById
    @Test
    public void testUpdateNotesById_NotesFound() {

        Long notesId = 1L;
        Long contactId = 2L;
        Notes mockNotes = new Notes(notesId, contactId, "This is a test note.", null);

        when(notesRepo.findById(notesId)).thenReturn(Optional.of(mockNotes));
        when(notesRepo.save(any(Notes.class))).thenAnswer(invocation -> invocation.getArgument(0));

        //Only updating the notetext.
        NotesDTO updatedNotesDTO = new NotesDTO();
        updatedNotesDTO.setId(notesId);
        updatedNotesDTO.setContactId(contactId);
        updatedNotesDTO.setNoteText("This is an updated test note.");

        Optional<NotesDTO> updatedNotes = notesService.updateNotesById(notesId, updatedNotesDTO);

        assertTrue(updatedNotes.isPresent());
        assertEquals("This is an updated test note.", updatedNotes.get().getNoteText());
        //Checking if these fields are not updated as intended.
        assertEquals(notesId, updatedNotes.get().getId());
        assertEquals(contactId, updatedNotes.get().getContactId());
    }

    @Test
    public void testUpdateNotesById_NotesNotFound() {

        Long notesId = 1L;
        Long contactId = 2L;

        when(notesRepo.findById(notesId)).thenReturn(Optional.empty());

        NotesDTO updatedNotesDTO = new NotesDTO();
        updatedNotesDTO.setId(notesId);
        updatedNotesDTO.setContactId(contactId);
        updatedNotesDTO.setNoteText("This is an updated test note.");

        assertThrows(NotesNotFoundException.class, () -> notesService.updateNotesById(notesId, updatedNotesDTO));
    }

    //deleteNotesById
    @Test
    public void testDeleteNotesById_NotesFound() {

        Long notesId = 1L;

        when(notesRepo.findById(notesId)).thenReturn(Optional.of(new Notes()));

        notesService.deleteNotesById(notesId);

        //Verifying that delete method in the repo was invoked
        verify(notesRepo, times(1)).deleteById(notesId);
    }

    @Test
    public void testDeleteNotesById_NotesNotFound() {

        Long notesId = 1L;

        when(notesRepo.findById(notesId)).thenReturn(Optional.empty());

        assertThrows(NotesNotFoundException.class, () -> notesService.deleteNotesById(notesId));
    }


}

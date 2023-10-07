package com.example.clmp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.example.clmp.repo.NotesRepo;
import com.example.clmp.dto.NotesDTO;
import com.example.clmp.model.Notes;
import com.example.clmp.exception.NotesNotFoundException;
import com.example.clmp.exception.NotesNotValidException;



@Service
@Validated
public class NotesService {

    @Autowired
    private NotesRepo notesRepo;

    public Optional<NotesDTO> getNotesById(Long id) {
        Optional<Notes> notesData = notesRepo.findById(id);

        if (notesData.isPresent()) {
            return Optional.of(convertToDTO(notesData.get()));
        } else {
            throw new NotesNotFoundException("Notes not found with ID: " + id);
        }
    }

    public NotesDTO addNotes(NotesDTO notesDTO) {
        //Validation check: check if required fields are not null
        if (notesDTO.getNoteText() == null || notesDTO.getContactId() == null) {
            throw new NotesNotValidException("Note data is not valid. NoteText and ContactID are required.");
        }
        Notes notes = convertToEntity(notesDTO);
        Notes savedNotes = notesRepo.save(notes);

        return convertToDTO(savedNotes);
    }

    public Optional<NotesDTO> updateNotesById(Long id, NotesDTO newNotesDTO) {
        Optional<Notes> oldNotesData = notesRepo.findById(id);
        Notes newNotesData = convertToEntity(newNotesDTO);

        if (oldNotesData.isPresent()) {
            Notes updatedNotesData = oldNotesData.get();
            updatedNotesData.setContactId(newNotesData.getContactId());
            updatedNotesData.setNoteText(newNotesData.getNoteText());
            updatedNotesData.setDateCreated(newNotesData.getDateCreated());

            Notes savedNotes = notesRepo.save(updatedNotesData);
            return Optional.of(convertToDTO(savedNotes));
        } else {
            //exception where note is not found with the id given: no note to update
            throw new NotesNotFoundException("Notes not found with ID: " + id);
        }
    }

    public void deleteNotesById(Long id) {
        Optional<Notes> notesData = notesRepo.findById(id);
        
        if (notesData.isPresent()) {
            notesRepo.deleteById(id);
        } else {
            //exception where note is not found with the id given: no note to delete
            throw new NotesNotFoundException("Notes not found with ID: " + id);
        }
    }

    //Helper Function: Converting entity to DTO
    private NotesDTO convertToDTO(Notes notes) {
        NotesDTO notesDTO = new NotesDTO();

        notesDTO.setId(notes.getId());
        notesDTO.setContactId(notes.getContactId());
        notesDTO.setNoteText(notes.getNoteText());
        notesDTO.setDateCreated(notes.getDateCreated());
        
        return notesDTO;
    }
    //Helper Function: Converting DTO to entity
    private Notes convertToEntity(NotesDTO notesDTO) {
        Notes notes = new Notes();

        notes.setId(notesDTO.getId());
        notes.setContactId(notesDTO.getContactId());
        notes.setNoteText(notesDTO.getNoteText());
        notes.setDateCreated(notesDTO.getDateCreated());

        return notes;
    }
    
}

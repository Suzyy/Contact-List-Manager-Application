package com.example.clmp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.example.clmp.repo.NotesRepo;
import com.example.clmp.dto.NotesDTO;
import com.example.clmp.entity.Notes;
import com.example.clmp.exception.NotesNotFoundException;
import com.example.clmp.exception.NotesNotValidException;



@Service
@Validated
public class NotesService {

    @Autowired
    private NotesRepo notesRepo;

    //Getting notes by specific note id
    public Optional<NotesDTO> getNotesById(Long id) {
        Optional<Notes> notesData = notesRepo.findById(id);

        //If note with the specific id exists, return note data to controller
        if (notesData.isPresent()) {
            return Optional.of(convertToDTO(notesData.get()));
        } else { //if note does not exists, throw notest not found exception
            throw new NotesNotFoundException("Notes not found with ID: " + id);
        }
    }

    //Adding notes
    public NotesDTO addNotes(NotesDTO notesDTO) {
        //Validation check: check if required fields are not null
        //If required fields are null, throw note not valid exception to controller
        if (notesDTO.getNoteText() == null || notesDTO.getContactId() == null) {
            throw new NotesNotValidException("Note data is not valid. NoteText and ContactID are required.");
        }
        //Note is valid, add notes to the database
        Notes notes = convertToEntity(notesDTO);
        Notes savedNotes = notesRepo.save(notes);

        return convertToDTO(savedNotes);
    }

    //Updating notes by specific note id
    public Optional<NotesDTO> updateNotesById(Long id, NotesDTO newNotesDTO) {
        Optional<Notes> oldNotesData = notesRepo.findById(id);
        Notes newNotesData = convertToEntity(newNotesDTO);

        //If note with note id exists, update fields and save
        if (oldNotesData.isPresent()) {
            Notes updatedNotesData = oldNotesData.get();
            updatedNotesData.setContactId(newNotesData.getContactId());
            updatedNotesData.setNoteText(newNotesData.getNoteText());
            updatedNotesData.setDateCreated(newNotesData.getDateCreated());

            Notes savedNotes = notesRepo.save(updatedNotesData);
            return Optional.of(convertToDTO(savedNotes));
        } else { //If note with not id does not exist, throw notes not found exception
            //exception where note is not found with the id given: no note to update
            throw new NotesNotFoundException("Notes not found with ID: " + id);
        }
    }

    //Deleting note by specific note id
    public void deleteNotesById(Long id) {
        Optional<Notes> notesData = notesRepo.findById(id);
        
        //If note with note id exists, delete
        if (notesData.isPresent()) {
            notesRepo.deleteById(id);
        } else { //If note with note id does not exists, throw notes not found exception
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

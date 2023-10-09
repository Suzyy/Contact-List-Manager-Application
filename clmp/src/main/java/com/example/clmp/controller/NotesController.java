package com.example.clmp.controller;

import com.example.clmp.service.NotesService;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;

import com.example.clmp.dto.NotesDTO;
import com.example.clmp.exception.NotesNotFoundException;
import com.example.clmp.exception.NotesNotValidException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Optional;

@RestController
@RequestMapping("/v1/notes")  //Version Controlling: V1 - Can add different versions when needed
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class NotesController {

    @Autowired
    private NotesService notesService;

    //Rate limiting. API should allow only 50 request in a minute
    //This can be used in pricing plan for each API client
    private final Bucket bucket;

    public NotesController() {
        Bandwidth limit = Bandwidth.classic(50, Refill.greedy(50, Duration.ofMinutes(1)));
        this.bucket = Bucket4j.builder().addLimit(limit).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/getNotesById/{id}")
    public ResponseEntity<NotesDTO> getNotesById(@PathVariable Long id) {
        try {
            Optional<NotesDTO> notesDTOData = notesService.getNotesById(id);
            return new ResponseEntity<>(notesDTOData.get(), HttpStatus.OK);
        } catch (NotesNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping("/addNotes")
    public ResponseEntity<NotesDTO> addNotes(@RequestBody NotesDTO notesDTO) {
        System.out.println("Received request body: " + notesDTO.toString());
        try {
            NotesDTO notesObj = notesService.addNotes(notesDTO);
            return new ResponseEntity<>(notesObj, HttpStatus.OK);
        } catch (NotesNotValidException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping("/updateNotesById/{id}")
    public ResponseEntity<NotesDTO> updateNotesById(@PathVariable Long id, @RequestBody NotesDTO newNotesDTO) {
        try {
            Optional<NotesDTO> updatedNotesDTOData = notesService.updateNotesById(id, newNotesDTO);
            return new ResponseEntity<>(updatedNotesDTOData.get(), HttpStatus.OK);
        } catch (NotesNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @DeleteMapping("/deleteNotesById/{id}")
    public ResponseEntity<HttpStatus> deleteNotesById(@PathVariable Long id) {
        try {
            notesService.deleteNotesById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotesNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

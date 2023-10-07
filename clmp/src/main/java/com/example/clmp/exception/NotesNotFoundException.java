package com.example.clmp.exception;

public class NotesNotFoundException extends RuntimeException {
    public NotesNotFoundException(String message) {
        super(message);
    }
}

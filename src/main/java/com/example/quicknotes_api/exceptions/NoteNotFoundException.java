package com.example.quicknotes_api.exceptions;

public class NoteNotFoundException extends RuntimeException{
    public NoteNotFoundException(Long id){
        super("Note not found with id " + id);
    }
}

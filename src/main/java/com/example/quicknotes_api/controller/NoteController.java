package com.example.quicknotes_api.controller;

import com.example.quicknotes_api.model.Note;
import com.example.quicknotes_api.service.NoteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService){
        this.noteService = noteService;
    }
    @GetMapping("/notes")
    public List<Note> getNotes(){
        return noteService.getNotes();
    }
}

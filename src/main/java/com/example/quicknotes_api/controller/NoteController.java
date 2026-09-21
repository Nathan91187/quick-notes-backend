package com.example.quicknotes_api.controller;

import com.example.quicknotes_api.model.Note;
import com.example.quicknotes_api.service.NoteService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService){
        this.noteService = noteService;
    }
    @PutMapping("/notes/{id}")
    public void putNoteById(@RequestBody Note note, @PathVariable Long id){
        noteService.putNoteById(id,note);
    }
    @DeleteMapping("/notes/{id}")
    public void deleteNoteById(@PathVariable Long id){
        noteService.deleteNoteById(id);
    }
    @GetMapping("/notes")
    public List<Note> getNotes(){
        return noteService.getNotes();
    }
    @GetMapping("/notes/{id}")
    public Note getNodeById(@PathVariable Long id){
        return noteService.getNoteById(id);
    }
    @PostMapping("/notes")
    public Note saveNote(@Valid @RequestBody Note note){
        return noteService.createNote(note);
    }
}

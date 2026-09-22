package com.example.quicknotes_api.controller;

import com.example.quicknotes_api.dto.CreateNoteRequest;
import com.example.quicknotes_api.dto.NoteResponse;
import com.example.quicknotes_api.dto.UpdateNoteRequest;
import com.example.quicknotes_api.service.NoteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService){
        this.noteService = noteService;
    }
    @PutMapping("/notes/{id}")
    public NoteResponse putNoteById(@Valid @RequestBody UpdateNoteRequest noteRequest, @PathVariable Long id){
        return noteService.putNoteById(id,noteRequest);
    }
    @DeleteMapping("/notes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNoteById(@PathVariable Long id){
        noteService.deleteNoteById(id);
    }
    @GetMapping("/notes")
    public List<NoteResponse> getNotes(){
        return noteService.getNotes();
    }
    @GetMapping("/notes/{id}")
    public NoteResponse getNodeById(@PathVariable Long id){
        return noteService.getNoteById(id);
    }
    @PostMapping("/notes")
    @ResponseStatus(HttpStatus.CREATED)
    public NoteResponse saveNote(@Valid @RequestBody CreateNoteRequest noteRequest){
        return noteService.createNote(noteRequest);
    }
}

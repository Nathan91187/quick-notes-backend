package com.example.quicknotes_api.service;

import com.example.quicknotes_api.exceptions.NoteNotFoundException;
import com.example.quicknotes_api.model.Note;
import com.example.quicknotes_api.repository.NoteRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class NoteService {
    private final NoteRepository noteRepository;
    public  NoteService(NoteRepository noteRepository){
        this.noteRepository = noteRepository;
    }
    public List<Note> getNotes(){
       return noteRepository.findAll();
    }
    public Note createNote(Note note){
        return noteRepository.save(note);
    }
    public Note getNoteById(Long id){
        return noteRepository.findById(id).orElseThrow(()-> new NoteNotFoundException(id));
    }
    public void deleteNoteById(Long id){
         noteRepository.deleteById(id);
    }
    public Note putNoteById(Long id, Note note){
        Note existingNote = noteRepository.findById(id).orElseThrow(()->new NoteNotFoundException(id));
        existingNote.setTitle(note.getTitle());
        existingNote.setContent(note.getContent());
        return noteRepository.save(existingNote);
    }
}

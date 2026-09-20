package com.example.quicknotes_api.service;

import com.example.quicknotes_api.model.Note;
import com.example.quicknotes_api.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NoteService {
    private final NoteRepository noteRepository;
    public  NoteService(NoteRepository noteRepository){
        this.noteRepository = noteRepository;
    }
    public List<Note> getNotes(){
       return noteRepository.findAll();
    }
}

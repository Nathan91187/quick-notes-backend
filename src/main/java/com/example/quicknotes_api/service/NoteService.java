package com.example.quicknotes_api.service;

import com.example.quicknotes_api.dto.CreateNoteRequest;
import com.example.quicknotes_api.dto.NoteResponse;
import com.example.quicknotes_api.dto.UpdateNoteRequest;
import com.example.quicknotes_api.exceptions.NoteNotFoundException;
import com.example.quicknotes_api.mapper.NoteMapper;
import com.example.quicknotes_api.model.Note;
import com.example.quicknotes_api.repository.NoteRepository;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {
    private final NoteMapper noteMapper;
    private final NoteRepository noteRepository;
    public  NoteService(NoteRepository noteRepository,NoteMapper noteMapper){
        this.noteMapper = noteMapper;
        this.noteRepository = noteRepository;
    }
    public List<NoteResponse> getNotes(){
       List<Note> notes = noteRepository.findAll();
        List<NoteResponse> noteResponses = new ArrayList<>();
        for(Note note : notes){
            NoteResponse noteResponse = noteMapper.toResponse(note);
            noteResponses.add(noteResponse);
        }
        return noteResponses;
    }
    public NoteResponse createNote(CreateNoteRequest noteRequest){
        Note note = noteMapper.toEntity(noteRequest);
        note = noteRepository.save(note);
        return noteMapper.toResponse(note);
    }
    public NoteResponse getNoteById(Long id){
        Note note = noteRepository.findById(id).orElseThrow(()-> new NoteNotFoundException(id));
        return noteMapper.toResponse(note);
    }
    public void deleteNoteById(Long id){
        noteRepository.delete(noteRepository.findById(id).orElseThrow(()-> new NoteNotFoundException(id)));
    }
    public NoteResponse putNoteById(Long id, UpdateNoteRequest noteRequest){
        Note existingNote = noteRepository.findById(id).orElseThrow(()->new NoteNotFoundException(id));
        noteMapper.updateEntity(noteRequest,existingNote);
        return noteMapper.toResponse(noteRepository.save(existingNote));
    }
}

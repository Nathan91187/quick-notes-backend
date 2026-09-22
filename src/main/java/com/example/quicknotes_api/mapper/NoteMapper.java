package com.example.quicknotes_api.mapper;

import com.example.quicknotes_api.dto.CreateNoteRequest;
import com.example.quicknotes_api.dto.NoteResponse;
import com.example.quicknotes_api.dto.UpdateNoteRequest;
import com.example.quicknotes_api.model.Note;
import org.springframework.stereotype.Component;

@Component
public class NoteMapper {
    public NoteResponse toResponse(Note note){
        NoteResponse noteResponse = new NoteResponse();
        noteResponse.setContent(note.getContent());
        noteResponse.setTitle(note.getTitle());
        noteResponse.setId(note.getId());
        return noteResponse;
    }
    public Note toEntity(CreateNoteRequest noteRequest){
        Note note = new Note();
        note.setTitle(noteRequest.getTitle());
        note.setContent(noteRequest.getContent());
        return note;
    }
    public void updateEntity(UpdateNoteRequest noteRequest,Note existingNote){
        existingNote.setTitle(noteRequest.getTitle());
        existingNote.setContent(noteRequest.getContent());
    }
}

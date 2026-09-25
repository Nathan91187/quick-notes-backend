package com.example.quicknotes_api.unit;


import com.example.quicknotes_api.dto.CreateNoteRequest;
import com.example.quicknotes_api.dto.NoteResponse;
import com.example.quicknotes_api.dto.UpdateNoteRequest;
import com.example.quicknotes_api.mapper.NoteMapper;
import com.example.quicknotes_api.model.Note;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NoteMapperTest {
    private NoteMapper noteMapper;
    @BeforeEach
    void setUp(){
        noteMapper = new NoteMapper();
    }
    @Test
    void toResponseMapsNoteToResponse(){
        Note note = new Note();
        note.setId(1L);
        note.setContent("Test content");
        note.setTitle("Test title");
        NoteResponse actualResponse = noteMapper.toResponse(note);
        Assertions.assertEquals(1L,actualResponse.getId());
        Assertions.assertEquals("Test content",actualResponse.getContent());
        Assertions.assertEquals("Test title",actualResponse.getTitle());
    }
    @Test
    void toEntityMapsCreateNoteRequestToNote(){
        CreateNoteRequest noteRequest = new CreateNoteRequest();
        noteRequest.setContent("Test content");
        noteRequest.setTitle("Test title");
        Note note = noteMapper.toEntity(noteRequest);
        Assertions.assertEquals("Test content",note.getContent());
        Assertions.assertEquals("Test title",note.getTitle());
    }
    @Test
    void updateEntityModifiesExistingNote(){
        UpdateNoteRequest noteRequest = new UpdateNoteRequest();
        noteRequest.setTitle("Test title");
        noteRequest.setContent("Test content");
        Note existingNote = new Note();
        existingNote.setTitle("Old title");
        existingNote.setContent("Old content");
        noteMapper.updateEntity(noteRequest,existingNote);
        Assertions.assertEquals("Test content",existingNote.getContent());
        Assertions.assertEquals("Test title",existingNote.getTitle());
    }


}

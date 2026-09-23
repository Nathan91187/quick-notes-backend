package com.example.quicknotes_api.controller;

import com.example.quicknotes_api.dto.CreateNoteRequest;
import com.example.quicknotes_api.dto.NoteResponse;
import com.example.quicknotes_api.dto.UpdateNoteRequest;
import com.example.quicknotes_api.model.Note;
import com.example.quicknotes_api.service.NoteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NoteControllerTest {
    private NoteController noteController;
    @Mock
    private NoteService noteService;
    @BeforeEach
    void setUp(){
        noteController = new NoteController(noteService);
    }
    @Test
    void getNotesDelegatesToService(){
        NoteResponse noteResponse = new NoteResponse();
        noteResponse.setId(0L);
        NoteResponse noteResponse1 = new NoteResponse();
        noteResponse1.setId(1L);
        when(noteService.getNotes()).thenReturn(List.of(noteResponse,noteResponse1));
        List<NoteResponse> actualResponses = noteController.getNotes();
        Assertions.assertEquals(2, actualResponses.size());
        Assertions.assertEquals(0L, actualResponses.get(0).getId());
        Assertions.assertEquals(1L, actualResponses.get(1).getId());
        verify(noteService).getNotes();
    }
    @Test
    void getNoteByIdDelegatesToService(){
        NoteResponse expectedResponse = new NoteResponse();
        expectedResponse.setId(1L);
        when(noteService.getNoteById(1L)).thenReturn(expectedResponse);
        NoteResponse actualResponse = noteController.getNoteById(1L);
        Assertions.assertEquals(1L, actualResponse.getId());
        verify(noteService).getNoteById(1L);
    }
    @Test
    void saveNoteDelegatesToService(){
        CreateNoteRequest noteRequest = new CreateNoteRequest();
        NoteResponse expectedResponse = new NoteResponse();
        expectedResponse.setId(1L);
        when(noteService.createNote(noteRequest)).thenReturn(expectedResponse);
        NoteResponse actualResponse = noteController.saveNote(noteRequest);
        Assertions.assertEquals(1L, actualResponse.getId());
        verify(noteService).createNote(noteRequest);
    }
    @Test
    void deleteNoteByIdDelegatesToService(){
        noteController.deleteNoteById(1L);
        verify(noteService).deleteNoteById(1L);
    }
    @Test
    void putNoteByIdDelegatesToService(){
        UpdateNoteRequest noteRequest = new UpdateNoteRequest();
        NoteResponse expectedResponse = new NoteResponse();
        expectedResponse.setId(1L);
        when(noteService.putNoteById(1L, noteRequest)).thenReturn(expectedResponse);
        NoteResponse actualResponse = noteController.putNoteById(noteRequest,1L);
        Assertions.assertEquals(1L, actualResponse.getId());
        verify(noteService).putNoteById(1L,noteRequest);
    }
}

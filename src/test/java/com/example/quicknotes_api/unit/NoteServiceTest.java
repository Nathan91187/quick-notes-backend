package com.example.quicknotes_api.unit;

import com.example.quicknotes_api.dto.CreateNoteRequest;
import com.example.quicknotes_api.dto.NoteResponse;
import com.example.quicknotes_api.dto.UpdateNoteRequest;
import com.example.quicknotes_api.exceptions.NoteNotFoundException;
import com.example.quicknotes_api.mapper.NoteMapper;
import com.example.quicknotes_api.model.Note;
import com.example.quicknotes_api.repository.NoteRepository;
import com.example.quicknotes_api.service.NoteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {

    private NoteService noteService;
    @Mock
    private NoteRepository noteRepository;
    @Mock
    private NoteMapper noteMapper;
    @BeforeEach
    void setUp(){
        noteService = new NoteService(noteRepository,noteMapper);
    }
    @Test
    void getNotesReturnsResponses(){
        NoteResponse noteResponse = new NoteResponse();
        noteResponse.setId(0L);
        NoteResponse noteResponse1 = new NoteResponse();
        noteResponse1.setId(1L);
        Note note = new Note();
        note.setId(0L);
        Note note1 = new Note();
        note1.setId(1L);
        when(noteRepository.findAll()).thenReturn(List.of(note,note1));
        when(noteMapper.toResponse(note)).thenReturn(noteResponse);
        when(noteMapper.toResponse(note1)).thenReturn(noteResponse1);
        List<NoteResponse> actualResponses = noteService.getNotes();
        Assertions.assertEquals(2, actualResponses.size());
        Assertions.assertEquals(0L, actualResponses.get(0).getId());
        Assertions.assertEquals(1L, actualResponses.get(1).getId());
        verify(noteMapper).toResponse(note);
        verify(noteMapper).toResponse(note1);
        verify(noteRepository).findAll();
    }
    @Test
    void getNoteByIdReturnsResponse(){
        Note note = new Note();
        note.setId(1L);
        NoteResponse expectedResponse = new NoteResponse();
        expectedResponse.setId(1L);
        when(noteRepository.findById(1L)).thenReturn(Optional.of(note));
        when(noteMapper.toResponse(note)).thenReturn(expectedResponse);
        NoteResponse actualResponse = noteService.getNoteById(1L);
        Assertions.assertEquals(1L, actualResponse.getId());
        verify(noteRepository).findById(1L);
        verify(noteMapper).toResponse(note);
    }
    @Test
    void getNoteByIdThrowsException(){
       when(noteRepository.findById(1L)).thenReturn(Optional.empty());
       Assertions.assertThrows(NoteNotFoundException.class , ()->noteService.getNoteById(1L));
        verify(noteRepository).findById(1L);
    }
    @Test
    void createNoteReturnsResponse(){
        CreateNoteRequest noteRequest = new CreateNoteRequest();
        Note note = new Note();
        note.setId(1L);
        NoteResponse expectedResponse = new NoteResponse();
        expectedResponse.setId(1L);
        when(noteMapper.toEntity(noteRequest)).thenReturn(note);
        when(noteRepository.save(note)).thenReturn(note);
        when(noteMapper.toResponse(note)).thenReturn(expectedResponse);
        NoteResponse actualResponse = noteService.createNote(noteRequest);
        Assertions.assertEquals(1L, actualResponse.getId());
        verify(noteMapper).toEntity(noteRequest);
        verify(noteRepository).save(note);
        verify(noteMapper).toResponse(note);
    }
    @Test
    void deleteNoteByIdDeletesNote(){
        Note note = new Note();
        note.setId(1L);
        when(noteRepository.findById(1L)).thenReturn(Optional.of(note));
        noteService.deleteNoteById(1L);
        verify(noteRepository).findById(1L);
        verify(noteRepository).delete(note);
    }
    @Test
    void deleteNoteByIdThrowsException(){
        when(noteRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(
                NoteNotFoundException.class,
                ()-> noteService.deleteNoteById(1L)
        );
        verify(noteRepository,never()).delete(any(Note.class)); // Verify that noteRepository.delete() was never called with any Note object.
    }
    @Test
    void putNoteByIdReturnsResponse(){
        UpdateNoteRequest noteRequest = new UpdateNoteRequest();
        Note existingnote = new Note();
        existingnote.setId(1L);
        NoteResponse noteResponse = new NoteResponse();
        noteResponse.setId(1L);
        when(noteRepository.findById(1L)).thenReturn(Optional.of(existingnote));
        when(noteRepository.save(existingnote)).thenReturn(existingnote);
        when(noteMapper.toResponse(existingnote)).thenReturn(noteResponse);
        NoteResponse updatedResponse = noteService.putNoteById(1L,noteRequest);
        Assertions.assertEquals(1L, updatedResponse.getId());
        verify(noteRepository).findById(1L);
        verify(noteRepository).save(existingnote);
        verify(noteMapper).toResponse(existingnote);
        verify(noteMapper).updateEntity(noteRequest,existingnote);
    }
    @Test
    void putNoteByIdThrowsException(){
        when(noteRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(
                NoteNotFoundException.class,
                ()-> noteService.putNoteById(1L, new UpdateNoteRequest())
        );
        verify(noteRepository).findById(1L);
        verify(noteRepository,never()).save(any(Note.class));
        verify(noteMapper, never())
                .updateEntity(any(UpdateNoteRequest.class), any(Note.class));
        verify(noteMapper, never()).toResponse(any(Note.class));
    }
}

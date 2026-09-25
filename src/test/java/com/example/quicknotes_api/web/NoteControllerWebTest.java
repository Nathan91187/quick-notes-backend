package com.example.quicknotes_api.web;
import com.example.quicknotes_api.controller.NoteController;
import com.example.quicknotes_api.dto.CreateNoteRequest;
import com.example.quicknotes_api.dto.NoteResponse;
import com.example.quicknotes_api.dto.UpdateNoteRequest;
import com.example.quicknotes_api.exceptions.NoteNotFoundException;
import com.example.quicknotes_api.service.NoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NoteController.class)
public class NoteControllerWebTest {
@Autowired
    private MockMvc mockMvc;
@MockitoBean
    private NoteService noteService;
    @Test
    void getNoteByIdReturnsNote() throws Exception {
        NoteResponse expectedResponse = new NoteResponse();
        expectedResponse.setId(1L);
        when(noteService.getNoteById(1L)).thenReturn(expectedResponse);
        mockMvc.perform(get("/notes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }
    @Test
    void getNoteByIdReturnsNotFound() throws Exception{
        when(noteService.getNoteById(1L)).thenThrow( new NoteNotFoundException(1L));
        mockMvc.perform(get("/notes/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Note not found with id 1"));
    }
    @Test
    void getNotesReturnsNotes() throws Exception {
        NoteResponse expectedResponse = new NoteResponse();
        expectedResponse.setId(1L);
        NoteResponse expectedResponse1 = new NoteResponse();
        expectedResponse1.setId(2L);
        when(noteService.getNotes()).thenReturn(List.of(expectedResponse,expectedResponse1));
        mockMvc.perform(get("/notes")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }
    @Test
    void createNotesReturnsCreated()throws Exception{
        String requestBody = """
        {
            "title": "Test title",
            "content": "Test content"
        }
        """;
        NoteResponse expectedResponse = new NoteResponse();
        expectedResponse.setId(1L);
        when(noteService.createNote(any(CreateNoteRequest.class))).thenReturn(expectedResponse);
        mockMvc.perform(post("/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }
    @Test
    void createNoteReturnsBadRequestWhenTitleIsMissing() throws Exception {
        String requestBody = """
                {
                    "content": "Test content"
                }
                """;
        mockMvc.perform(post(("/notes"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors.title").exists());
    }
    @Test
    void createNoteReturnsBadRequestWhenContentIsMissing() throws Exception {
        String requestBody = """
                {
                    "title": "Test title"
                }
                """;
        mockMvc.perform(post(("/notes"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors.content").exists()); // content exists as an error in our error response,
        // check error response data type if unclear.
    }
    @Test
    void createNoteReturnsBadRequestWhenJsonIsMalformed() throws Exception {
        String requestBody = """
                {
                    "title": "Test title",
                    "content": "Test content"
                """;
        mockMvc.perform(post("/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Malformed JSON request"));
    }
    @Test
    void putNoteByIdReturnsUpdatedNote() throws Exception {
        String requestBody = """
        {
            "title": "Test title",
            "content": "Test content"
        }
        """;
        NoteResponse expectedResponse = new NoteResponse();
        expectedResponse.setId(1L);
        when(noteService.putNoteById(eq(1L), any(UpdateNoteRequest.class)))
                .thenReturn(expectedResponse);
        mockMvc.perform(
                put("/notes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }
    @Test
    void putNoteByIdReturnsNotFound() throws Exception {
        String requestBody = """
        {
            "title": "Test title",
            "content": "Test content"
        }
        """;
        when(noteService.putNoteById(eq(1L),any(UpdateNoteRequest.class)))
                .thenThrow(new NoteNotFoundException(1L));
        mockMvc.perform(put("/notes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Note not found with id 1"));
    }
    @Test
    void putNoteByIdReturnsBadRequestWhenJsonIsMalformed() throws Exception {
        String requestBody = """
                {
                    "title": "Test title",
                    "content": "Test content"
                """;
        mockMvc.perform(put("/notes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Malformed JSON request"));
    }
    @Test
    void putNoteByIdReturnsBadRequestWhenContentIsMissing() throws Exception {
        String requestBody = """
                {
                    "title": "Test title"
                }
                """;
        mockMvc.perform(put(("/notes/1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors.content").exists());
    }
    @Test
    void putNoteByIdReturnsBadRequestWhenTitleIsMissing() throws Exception {
        String requestBody = """
                {
                    "content": "Test content"
                }
                """;
        mockMvc.perform(put(("/notes/1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors.title").exists());
    }
    @Test
    void deleteNoteByIdReturnsNoContent() throws Exception {
        mockMvc.perform(delete ("/notes/1"))
                .andExpect(status().isNoContent());
        verify(noteService).deleteNoteById(1L);
    }
    @Test
    void deleteNoteByIdReturnsNotFound() throws Exception {
        doThrow(new NoteNotFoundException(1L)).when(noteService).deleteNoteById(1L);
        mockMvc.perform(delete("/notes/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Note not found with id 1"));
    }
    @Test
    void unsupportedHttpMethodReturnsMethodNotAllowed() throws Exception {
        mockMvc.perform(patch("/notes/1"))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath("$.message").value("HTTP method not supported"));
    }
}

package com.example.quicknotes_api.integration;
import com.example.quicknotes_api.repository.NoteRepository;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mysql.MySQLContainer;

import static org.hamcrest.Matchers.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class QuickNotesIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private NoteRepository noteRepository;
    @Container
    @ServiceConnection
    static MySQLContainer mysqlContainer = new MySQLContainer("mysql:8.4");
    @BeforeEach
    void clearDatabase(){
        noteRepository.deleteAll();
    }
    @Test
    void createNotePersistsNote() throws Exception {
        String requestBody = """
        {
            "title": "Test title",
            "content": "Test content"
        }
        """;
        mockMvc.perform(post("/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test title"))
                .andExpect(jsonPath("$.content").value("Test content"));

    }
    @Test
    void getNoteByIdReturnsNote() throws Exception {
        String requestBody = """
        {
            "title": "Test title",
            "content": "Test content"
        }
        """;
        MvcResult result =  mockMvc.perform(post("/notes")
                        .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        String content = result.getResponse().getContentAsString();
        int id = JsonPath.read(content,"$.id");
        mockMvc.perform(get("/notes/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test title"))
                .andExpect(jsonPath("$.content").value("Test content"))
                .andExpect(jsonPath("$.id").value(id));
    }
    @Test
    void getNotesReturnsAllNotes() throws Exception {
        String requestBody = """
        {
            "title": "Test title",
            "content": "Test content"
        }
        """;
        String requestBody1= """
        {
            "title": "Test title 1",
            "content": "Test content 1"
        }
        """;
        MvcResult result = mockMvc.perform(post("/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated()).andReturn();
        String content = result.getResponse().getContentAsString();
        int id = JsonPath.read(content ,"$.id");
        MvcResult result1 = mockMvc.perform(post("/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody1))
                .andExpect(status().isCreated()).andReturn();
        String content1 = result1.getResponse().getContentAsString();
        int id1 = JsonPath.read(content1 ,"$.id");
        mockMvc.perform(get("/notes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id", hasItems(id,id1)));
    }
    @Test
    void putNotesByIdPersistsNote() throws Exception {
        String requestBody = """
        {
            "title": "Test title",
            "content": "Test content"
        }
        """;
        String updatedRequestBody= """
        {
            "title": "Updated title",
            "content": "Updated content"
        }
        """;
        MvcResult result = mockMvc.perform(post("/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated()).andReturn();
        String content = result.getResponse().getContentAsString();
        int id = JsonPath.read(content ,"$.id");

        mockMvc.perform(put("/notes/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedRequestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value("Updated title"))
                .andExpect(jsonPath("$.content").value("Updated content"));
        mockMvc.perform(get("/notes/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value("Updated title"))
                .andExpect(jsonPath("$.content").value("Updated content"));
    }
    @Test
    void deleteNoteByIdDeletesNote() throws Exception {
        String requestBody = """
        {
            "title": "Test title",
            "content": "Test content"
        }
        """;
        MvcResult result = mockMvc.perform(post("/notes").
                        contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        int id = JsonPath.read(content, "$.id");
        mockMvc.perform(delete("/notes/" + id)).andExpect(status().isNoContent());
        mockMvc.perform(get("/notes/" + id)).andExpect(status().isNotFound());
    }
    @Test
    void  getNoteByIdReturnsNotFoundWhenNoteDoesNotExist() throws Exception {
        mockMvc.perform(get("/notes/99999"))
                .andExpect(status().isNotFound()).andReturn();

    }
    @Test
    void  putNoteByIdReturnsNotFoundWhenNoteDoesNotExist() throws Exception {
        String updateRequestBody = """
        {
            "title": "Test title",
            "content": "Test content"
        }
        """;
        mockMvc.perform(put("/notes/999999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateRequestBody)).andExpect(status().isNotFound());
    }
    @Test
    void  deleteNoteByIdReturnsNotFoundWhenNoteDoesNotExist() throws Exception {
        mockMvc.perform(delete("/notes/99999"))
                .andExpect(status().isNotFound());
    }
}

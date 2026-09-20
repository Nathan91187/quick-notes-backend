package com.example.quicknotes_api.repository;

import com.example.quicknotes_api.model.Note;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class InMemoryNoteRepository implements NoteRepository {


    @Override
    public List<Note> findAll() {
        return List.of(
                new Note(1L,"first","Yo, 148-3-to-the-3-to-the-6-to-the-9. " ),
                new Note(2L, "second" , "Representin' the ABQ. What up, biatch? "),
                new Note(3L, "third" ,  "Leave it at the tone!")
        );
    }
}

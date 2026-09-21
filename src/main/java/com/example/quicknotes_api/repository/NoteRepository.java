package com.example.quicknotes_api.repository;

import com.example.quicknotes_api.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note,Long> {

}

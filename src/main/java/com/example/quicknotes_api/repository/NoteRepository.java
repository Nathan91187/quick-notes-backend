package com.example.quicknotes_api.repository;

import com.example.quicknotes_api.model.Note;

import java.util.List;

public interface NoteRepository {
List<Note> findAll();
}

package com.example.quicknotes_api.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler{
    @ExceptionHandler(NoteNotFoundException.class)
    public ResponseEntity<String> handleNoteNotFound(NoteNotFoundException ex){
        return ResponseEntity.status(404).body(ex.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ResponseEntity<String>> handleValidationException(MethodArgumentNotValidException ex){
        List<FieldError> errors = ex.getBindingResult()
                .getFieldErrors();
        return errors.stream().map((error)-> ResponseEntity.status(400).body(error.getDefaultMessage())).toList();


    }
}

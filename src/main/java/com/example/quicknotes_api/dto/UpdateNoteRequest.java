package com.example.quicknotes_api.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdateNoteRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    public String getTitle(){
        return title;
    }
    public String getContent(){
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}

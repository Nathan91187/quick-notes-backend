package com.example.quicknotes_api.dto;

public class NoteResponse {
    private Long id;
    private String title;
    private String content;
    public Long getId(){
        return id;
    }
    public String getTitle(){
        return title;
    }
    public String getContent(){
        return content;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}

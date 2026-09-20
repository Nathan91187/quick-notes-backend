package com.example.quicknotes_api.model;

public class Note {
    private Long id;
    private String title;
    private String content;
    public Note(Long id, String title, String content){
        this.content = content;
        this.id = id;
        this.title = title;
    }
    public Long getId(){
        return id;
    }
    public String getTitle(){
        return title;
    }
    public String getContent(){
        return content;
    }
}

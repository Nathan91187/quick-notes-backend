package com.example.quicknotes_api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

public class ErrorResponse {
    private int status;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String,String> errors;
    public ErrorResponse(int status, String message){
        this.status = status;
        this.message = message;
    }
    public ErrorResponse(int status, String message, Map<String,String> errors){
        this.status = status;
        this.message = message;
        this.errors = errors;
    }
    public void setErrors(Map<String,String> errors){
       this.errors = errors;
    }
    public Map<String, String> getErrors(){
        return errors;
    }
    public void setStatus(int status){
        this.status = status;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public int getStatus(){
        return status;
    }
    public String getMessage(){
        return message;
    }

}

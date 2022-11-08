package com.webjournal.exception;

public class DatabaseFetchException extends RuntimeException {
    public DatabaseFetchException(String message){
        super(message);
    }
    public DatabaseFetchException(Integer integer, String message){
        super(message);
    }
}

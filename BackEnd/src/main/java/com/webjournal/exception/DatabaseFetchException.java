package com.webjournal.exception;

public class DatabaseFetchException extends RuntimeException {
    public DatabaseFetchException(String message){
        super(message);
    }
}

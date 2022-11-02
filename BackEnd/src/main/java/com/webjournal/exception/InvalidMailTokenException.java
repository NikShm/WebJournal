package com.webjournal.exception;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class InvalidMailTokenException
 * @since 11/1/2022 - 23.04
 **/
public class InvalidMailTokenException extends RuntimeException {
    public InvalidMailTokenException(String message) {
        super(message);
    }
}

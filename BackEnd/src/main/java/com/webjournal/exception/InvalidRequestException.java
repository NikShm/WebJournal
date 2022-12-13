package com.webjournal.exception;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class InvalidRequestException
 * @since 12/7/2022 - 23.34
 **/
public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String message) {
        super(message);
    }
}

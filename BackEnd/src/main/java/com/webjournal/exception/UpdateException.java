package com.webjournal.exception;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class UpdateException
 * @since 12/7/2022 - 23.34
 **/
public class UpdateException extends RuntimeException {
    public UpdateException(String message) {
        super(message);
    }
}

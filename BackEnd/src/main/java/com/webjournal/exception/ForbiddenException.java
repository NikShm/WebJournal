package com.webjournal.exception;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class ForbiddenException
 * @since 12/5/2022 - 00.14
 **/
public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}

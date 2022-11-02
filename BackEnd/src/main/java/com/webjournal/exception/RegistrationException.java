package com.webjournal.exception;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class RegistrationException
 * @since 11/2/2022 - 16.40
 **/
public class RegistrationException extends RuntimeException {
    public RegistrationException(String message) {
        super(message);
    }
}

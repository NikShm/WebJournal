package com.webjournal.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(DatabaseFetchException.class)
    public ResponseEntity<ApiErrorMessage> handleDatabaseFetchException(DatabaseFetchException e, WebRequest request){
        LOGGER.error("Could not fetch data from database", e);
        return getResponse(HttpStatus.NOT_FOUND, request, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, WebRequest request) {
        LOGGER.error("Validation error", e);
        List<String> errorMessages = e.getBindingResult().getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage()).toList();
        return getResponse(HttpStatus.BAD_REQUEST, request, errorMessages.toString());
    }

    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<ApiErrorMessage> handleRegistrationException(RegistrationException e, WebRequest request) {
        LOGGER.error("Could not register user", e);
        return getResponse(HttpStatus.BAD_REQUEST, request, e.getMessage());
    }

    @ExceptionHandler(TokenRefreshException.class)
    public ResponseEntity<ApiErrorMessage> handleTokenRefreshException(TokenRefreshException e, WebRequest request) {
        LOGGER.error("Refresh token was expired", e);
        return getResponse(HttpStatus.FORBIDDEN, request, e.getMessage());
    }

    private ResponseEntity<ApiErrorMessage> getResponse(HttpStatus status, WebRequest request, String message) {
        String path = ((ServletWebRequest)request).getRequest().getRequestURI();
        ApiErrorMessage errorMessage = new ApiErrorMessage(status.value(), status.getReasonPhrase(), LocalDateTime.now(), path, message);
        return new ResponseEntity<>(errorMessage, status);
    }
}

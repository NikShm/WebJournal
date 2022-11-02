package com.webjournal.exception;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project FreshBeauty
 * @class ProductNotFoundException
 * @since 8/13/2022 - 14.32
 **/
public class DatabaseFetchException extends RuntimeException {
    public DatabaseFetchException(String message) {
        super(message);
    }
}

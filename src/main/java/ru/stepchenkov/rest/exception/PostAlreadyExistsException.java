package ru.stepchenkov.rest.exception;

public class PostAlreadyExistsException extends Exception {
    public PostAlreadyExistsException(String message) {
        super(message);
    }
}

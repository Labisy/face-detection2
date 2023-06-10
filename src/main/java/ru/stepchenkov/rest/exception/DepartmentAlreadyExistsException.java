package ru.stepchenkov.rest.exception;

public class DepartmentAlreadyExistsException extends Exception {
    public DepartmentAlreadyExistsException(String message) {
        super(message);
    }
}

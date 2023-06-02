package ru.stepchenkov.rest.exception;

public class DepartmentAlreadyException extends Exception {
    public DepartmentAlreadyException(String message) {
        super(message);
    }
}

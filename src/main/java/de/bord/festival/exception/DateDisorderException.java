package de.bord.festival.exception;

/**
 * It is an Exception child class
 * Should be thrown, if the start event day is bigger then end event day
 */
public class DateDisorderException extends Exception {
    private String message;

    public DateDisorderException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}


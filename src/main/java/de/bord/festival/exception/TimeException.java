package de.bord.festival.exception;


/**
 * It is an Exception child class
 * Should be thrown, if a band gets the same timeSlot on more than one stage
 */
public class TimeException extends Exception {
    private String message;

    public TimeException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}


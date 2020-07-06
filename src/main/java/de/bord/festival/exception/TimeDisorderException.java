package de.bord.festival.exception;

/**
 * Should be thrown, if the start event time is bigger than end time, or if end time is over 23:59
 */
public class TimeDisorderException extends Exception {
    private String message;

    public TimeDisorderException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

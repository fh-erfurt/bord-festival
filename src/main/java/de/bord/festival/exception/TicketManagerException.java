package de.bord.festival.exception;

/**
 * It is an Exception child class
 * Should be thrown, if the updated price level not exists
 */
public class TicketManagerException extends Exception {
    private String message;

    public TicketManagerException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
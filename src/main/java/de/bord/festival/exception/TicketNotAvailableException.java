package de.bord.festival.exception;

/**
 * should be thrown when no tickets of the desired category are available
 */

public class TicketNotAvailableException extends Exception{
    private String message;

    public TicketNotAvailableException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

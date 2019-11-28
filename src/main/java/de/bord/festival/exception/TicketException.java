package de.bord.festival.exception;

/**
 * should be thrown when no tickets of the desired category are available
 */
public class TicketException extends Exception
{
    private String message;

    public TicketException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

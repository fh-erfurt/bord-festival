package de.bord.festival.exception;

/**
 * Should be thrown when a wrong email adress is entered
 * */
public class MailException extends Exception
{
    private String message;

    public MailException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

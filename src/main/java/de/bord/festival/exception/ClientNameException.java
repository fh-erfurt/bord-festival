package de.bord.festival.exception;

/**
 *  Exception child class
 *  Should be thrown if entered name is either:
 *   empty
 *   too long
 *   doesn't consist of the correct characters (a-z, A-Z, 0-9, .),
 *   is the name already used for the account or
 */
public class ClientNameException extends Exception
{
    private String message;

    public ClientNameException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

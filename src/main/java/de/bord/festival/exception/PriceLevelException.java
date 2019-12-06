package de.bord.festival.exception;

/**
 * It is an Exception child class
 * Should be thrown, if the percentage of the price level is not between [0,100]
 */
public class PriceLevelException extends Exception {
    private String message;

    public PriceLevelException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
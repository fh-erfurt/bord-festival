package de.bord.festival.exception;


/**
 * It is an Exception child class
 * Should be thrown, if an index of "ArrayList<PriceLevel> priceLevels" in
 * TicketManager is not available
 */
public class PriceLevelNotAvailableException extends Exception{
    private String message;

    public PriceLevelNotAvailableException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

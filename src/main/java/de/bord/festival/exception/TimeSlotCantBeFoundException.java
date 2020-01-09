package de.bord.festival.exception;


/**
 * It is an Exception child class
 * Should be thrown, if a band gets the same time slot on more than one stage
 *                  ,if there is not found any time slot for new band
 */
public class TimeSlotCantBeFoundException extends Exception {
    private String message;

    public TimeSlotCantBeFoundException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}


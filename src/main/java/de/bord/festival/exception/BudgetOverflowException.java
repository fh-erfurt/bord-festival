package de.bord.festival.exception;

/**
 * It is an exception child class
 * Should be thrown if the budget limits are exceeded
 */
public class BudgetOverflowException extends Exception {
    private String message;

    public BudgetOverflowException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}


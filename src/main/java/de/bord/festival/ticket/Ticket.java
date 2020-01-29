package de.bord.festival.ticket;

import de.bord.festival.eventManagement.Event;

/**
 * gives information about ticket price and description of the current price level
 * for the selected ticket type (DAY, CAMPING, VIP)
 */

public abstract class Ticket {

    public enum TicketType {DAY, CAMPING, VIP}


    protected String description;
    protected double stdPrice;

    public Ticket(String description, double standardPrice) {

        this.description = description;
        this.stdPrice = standardPrice;
    }

    abstract public TicketType getTicketType();

    public void setDescription(String description){
        this.description = description;
    }
    public void setStdPrice(double stdPrice){
        this.stdPrice = stdPrice;
    }

    public double getStdPrice() { return stdPrice; }
    public String getDescription(){return description;}
}
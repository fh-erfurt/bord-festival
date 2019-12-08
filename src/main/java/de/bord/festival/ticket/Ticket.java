package de.bord.festival.ticket;

import de.bord.festival.eventManagement.Event;

public abstract class Ticket {

    enum TicketType {DAY, CAMPING, VIP}


    protected int id;
    protected String description;
    protected boolean available;
    protected Event event;
    protected double stdPrice;


    public boolean isAvailable() {return available;}

    public Ticket(int id, String description, boolean available, double standardPrice, Event event) {
        this.available = available;
        this.description = description;
        this.id = id;
        this.stdPrice = standardPrice;
        this.event = event;
    }

    abstract public TicketType getTicketType();
}

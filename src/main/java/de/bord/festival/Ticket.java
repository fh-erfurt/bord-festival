package de.bord.festival;

public abstract class Ticket {
    enum TicketType{DAY, CAMPING, VIP};
    protected int ID;
    protected String description;
    protected boolean available;
    protected Event event;
    protected double stdPrice;
    public Ticket(TicketType ticketType, int ID, String description, boolean available, double standardPrice, Event event){
        this.available=available;
        this.description=description;
        this.ID=ID;
        this.stdPrice=standardPrice;
        this.event=event;
    }
}

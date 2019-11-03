package de.bord.festival;

public class VIPTicket extends Ticket {
    public VIPTicket(TicketType ticketType, int ID, String description, boolean available, double standardPrice, Event event){
        super(ticketType, ID, description, available, standardPrice, event);
    }
}

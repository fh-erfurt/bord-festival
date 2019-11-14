package de.bord.festival;

public class VIPTicket extends Ticket {

    public VIPTicket(TicketType ticketType, int id, String description,
                     boolean available, double standardPrice, Event event){

        super(ticketType, id, description, available, standardPrice, event);
    }
}

package de.bord.festival.ticket;

import de.bord.festival.eventManagement.Event;

public class VIPTicket extends Ticket {



    public VIPTicket(TicketType ticketType, int id, String description,
                     boolean available, double standardPrice, Event event){

        super(ticketType, id, description, available, standardPrice, event);
    }

    @Override
    public TicketType getTicketType() {
        return TicketType.VIP;
    }

}

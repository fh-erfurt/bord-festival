package de.bord.festival.ticket;

import de.bord.festival.eventManagement.Event;

/**
 * gives information about ticket price and description of the current price level
 * ticketType = TicketType.VIP
 */

public class VIPTicket extends Ticket {

    private TicketType ticketType;

    public VIPTicket(TicketType ticketType, int id, String description,
                     boolean available, double standardPrice/*, Event event*/){

        super(id, description, available, standardPrice/*, event*/);
        this.ticketType = TicketType.VIP;
    }

    @Override
    public TicketType getTicketType() {
        return TicketType.VIP;
    }

}

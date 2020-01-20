package de.bord.festival.ticket;

import de.bord.festival.eventManagement.Event;

/**
 * gives information about ticket price and description of the current price level
 * ticketType = TicketType.VIP
 */

public class VIPTicket extends Ticket {

    private TicketType ticketType;

    public VIPTicket(TicketType ticketType, String description,
                      double standardPrice){

        super(description, standardPrice);
        this.ticketType = TicketType.VIP;
    }

    @Override
    public TicketType getTicketType() {
        return TicketType.VIP;
    }

}

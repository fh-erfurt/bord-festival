package de.bord.festival.ticket;

import de.bord.festival.eventManagement.Event;
import de.bord.festival.ticket.Ticket.TicketType;

/**
 * gives information about ticket price and description of the current price level
 * ticketType = TicketType.CAMPING
 */

public class CampingTicket extends Ticket {

    private TicketType ticketType;

    public CampingTicket(TicketType ticketType, String description, double standardPrice){

        super(description, standardPrice);
        this.ticketType = TicketType.CAMPING;
    }

    @Override
    public TicketType getTicketType() {
        return TicketType.CAMPING;
    }
}

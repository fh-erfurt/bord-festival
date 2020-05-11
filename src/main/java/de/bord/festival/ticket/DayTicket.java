package de.bord.festival.ticket;

import de.bord.festival.eventManagement.Event;

/**
 * gives information about ticket price and current price level
 * ticketType = TicketType.DAY
 */

public class DayTicket extends Ticket {


    private TicketType ticketType;

    public DayTicket(TicketType ticketType, String description,
                     double standardPrice ){

        super(description, standardPrice);

        this.ticketType = TicketType.DAY;
    }

    @Override
    public TicketType getTicketType() {
        return TicketType.DAY;
    }
}

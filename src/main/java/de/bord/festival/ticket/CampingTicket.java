package de.bord.festival.ticket;

import de.bord.festival.eventManagement.Event;
import de.bord.festival.ticket.Ticket.TicketType;

/**
 * gives information about ticket price and description of the current price level
 * ticketType = TicketType.CAMPING
 */

public class CampingTicket extends Ticket {

    //private String campingPlaceName;
    private TicketType ticketType;

    public CampingTicket(TicketType ticketType, int id, String description,
                         boolean available, double standardPrice/*, String campingPlaceName , Event event*/){

        super(id, description, available, standardPrice/*, event*/);
        //this.campingPlaceName=campingPlaceName;
        this.ticketType = TicketType.CAMPING;
    }

    @Override
    public TicketType getTicketType() {
        return TicketType.CAMPING;
    }
}

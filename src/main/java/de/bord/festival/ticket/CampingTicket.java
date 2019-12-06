package de.bord.festival.ticket;

import de.bord.festival.eventManagement.Event;
import de.bord.festival.ticket.Ticket.TicketType;

public class CampingTicket extends Ticket {

    private String campingPlaceName;
    public CampingTicket(TicketType ticketType, int id, String description,
                         boolean available, double standardPrice, String campingPlaceName, Event event){

        super(ticketType, id, description, available, standardPrice, event);
        this.campingPlaceName=campingPlaceName;
    }

    @Override
    public TicketType getTicketType() {
        return TicketType.CAMPING;
    }
}

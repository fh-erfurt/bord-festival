package de.bord.festival.ticket;

import de.bord.festival.models.Ticket;

import javax.persistence.Entity;

/**
 * gives information about ticket price and current price level
 * ticketType = TicketType.CAMPING
 */
@Entity
public class CampingTicket extends Ticket {

    private TicketType ticketType;

    public CampingTicket( String description, double standardPrice){

        super(description, standardPrice);
        this.ticketType = TicketType.CAMPING;
    }
    public CampingTicket(){}

    @Override
    public TicketType getTicketType() {
        return TicketType.CAMPING;
    }
}

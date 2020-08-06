package de.bord.festival.ticket;

import de.bord.festival.models.Ticket;

import javax.persistence.Entity;
import javax.persistence.Enumerated;

/**
 * gives information about ticket price and current price level
 * ticketType = TicketType.CAMPING
 */
@Entity
public class CampingTicket extends Ticket {
    @Enumerated
    private Type ticketType;

    public CampingTicket( String description, double standardPrice){

        super(description, standardPrice);
        this.ticketType = Type.CAMPING;
    }
    public CampingTicket(){}

    @Override
    public Type getTicketType() {
        return Type.CAMPING;
    }
}

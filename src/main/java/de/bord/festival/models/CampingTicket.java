package de.bord.festival.models;

import de.bord.festival.models.Ticket;
import de.bord.festival.ticket.Type;

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

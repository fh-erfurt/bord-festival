package de.bord.festival.models;

import de.bord.festival.models.Ticket;
import de.bord.festival.ticket.Type;

import javax.persistence.Entity;
import javax.persistence.Enumerated;

/**
 * gives information about ticket price and current price level
 * ticketType = TicketType.VIP
 */
@Entity
public class VIPTicket extends Ticket {
    @Enumerated
    private Type ticketType;

    public VIPTicket(String description,
                      double standardPrice){

        super(description, standardPrice);
        this.ticketType = Type.VIP;
    }
    public VIPTicket(){}

    @Override
    public Type getTicketType() {
        return Type.VIP;
    }

}

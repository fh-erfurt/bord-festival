package de.bord.festival.ticket;

import de.bord.festival.models.Ticket;

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

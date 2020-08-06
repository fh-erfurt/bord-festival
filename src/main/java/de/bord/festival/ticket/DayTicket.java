package de.bord.festival.ticket;

import de.bord.festival.models.Ticket;

import javax.persistence.Entity;
import javax.persistence.Enumerated;

/**
 * gives information about ticket price and current price level
 * ticketType = TicketType.DAY
 */
@Entity
public class DayTicket extends Ticket {

    @Enumerated
    private Type ticketType;

    public DayTicket(String description,
                     double standardPrice ){

        super(description, standardPrice);

        this.ticketType = Type.DAY;
    }

    public DayTicket(){}

    @Override
    public Type getTicketType() {
        return Type.DAY;
    }
}

package de.bord.festival.models;

import de.bord.festival.models.Ticket;
import de.bord.festival.ticket.Type;

import javax.persistence.Entity;

/**
 * gives information about ticket price and current price level
 * ticketType = TicketType.DAY
 */
@Entity
public class DayTicket extends Ticket {


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

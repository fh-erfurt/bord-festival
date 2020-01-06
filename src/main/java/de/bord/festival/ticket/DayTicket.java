package de.bord.festival.ticket;

import de.bord.festival.eventManagement.Event;

public class DayTicket extends Ticket {

    private String date;
    private TicketType ticketType;

    public DayTicket(TicketType ticketType, int id, String description, boolean available,
                     double standardPrice /*, Event event*/, String date){

        super(id, description, available, standardPrice/*, event*/);
        this.date=date;
        this.ticketType = TicketType.DAY;
    }

    @Override
    public TicketType getTicketType() {
        return TicketType.DAY;
    }
}

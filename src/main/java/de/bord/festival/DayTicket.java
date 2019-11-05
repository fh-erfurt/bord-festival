package de.bord.festival;

public class DayTicket extends Ticket{
    private String date;
    public DayTicket(TicketType ticketType, int id, String description, boolean available,
                     double standardPrice, Event event, String date){

        super(ticketType, id, description, available, standardPrice, event);
        this.date=date;
    }
}

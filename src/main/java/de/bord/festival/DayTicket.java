package de.bord.festival;

public class DayTicket extends Ticket{
    private String date;
    public DayTicket(TicketType ticketType, int ID, String description, boolean available,
                     double standardPrice, Event event, String date){

        super(ticketType, ID, description, available, standardPrice, event);
        this.date=date;
    }
}

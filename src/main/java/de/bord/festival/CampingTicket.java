package de.bord.festival;

public class CampingTicket extends Ticket {
    private String campingPlaceName;
    public CampingTicket(TicketType ticketType, int ID, String description,
                         boolean available, double standardPrice, String campingPlaceName, Event event){

        super(ticketType, ID, description, available, standardPrice, event);
        this.campingPlaceName=campingPlaceName;

    }
}

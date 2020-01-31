package de.bord.festival.ticket;

import de.bord.festival.client.Client;

public interface ITicketManager {

    public Ticket getTicket(Ticket.TicketType type);

    public boolean isAvailable(Ticket.TicketType type);

    public void setTicketDescription(String description, Ticket.TicketType type);

    public void setTicketStdPrice(double stdPrice, Ticket.TicketType type);

    public void setAutomaticPriceLevelChange(boolean isPriceLevelChangeAutomatic);

    public boolean setPriceLevel(int index);

    public PriceLevel getPriceLevel(int index);

    public boolean sellTickets(Client client);
}

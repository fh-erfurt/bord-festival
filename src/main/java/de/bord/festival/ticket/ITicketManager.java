package de.bord.festival.ticket;

import de.bord.festival.client.Client;
import de.bord.festival.exception.TicketManagerException;

public interface ITicketManager {

    public Ticket getTicket(Ticket.TicketType type);

    public boolean isAvailable(Ticket.TicketType type, int numberOfCartTickets);

    public void setTicketDescription(String description, Ticket.TicketType type);

    public void setTicketStdPrice(double stdPrice, Ticket.TicketType type);

    public void setAutomaticPriceLevelChange(boolean isPriceLevelChangeAutomatic) throws TicketManagerException;

    public boolean setPriceLevel(int index);

    public PriceLevel getPriceLevel(int index);

    public boolean sellTickets(Client client) throws TicketManagerException;
}

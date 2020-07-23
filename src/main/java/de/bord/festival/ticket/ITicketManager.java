package de.bord.festival.ticket;

import de.bord.festival.models.Client;
import de.bord.festival.exception.PriceLevelNotAvailableException;
import de.bord.festival.exception.TicketNotAvailableException;
import de.bord.festival.models.Ticket;

public interface ITicketManager {
    Ticket getNewTicket(Type type) throws TicketNotAvailableException;

    boolean isAvailable(Type type, int numberOfCartTickets);

    void setTicketDescription(String description, Ticket.TicketType type);

    void setTicketStdPrice(double stdPrice, Ticket.TicketType type);

    void setAutomaticPriceLevelChange(boolean isPriceLevelChangeAutomatic);

    boolean setPriceLevel(int index) throws PriceLevelNotAvailableException;

    void sellTickets(Client client) throws TicketNotAvailableException;
}

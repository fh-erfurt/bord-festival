package de.bord.festival.client;

import de.bord.festival.exception.ClientNameException;
import de.bord.festival.exception.PriceLevelException;
import de.bord.festival.exception.TicketNotAvailableException;
import de.bord.festival.models.Ticket;
import de.bord.festival.models.TicketManager;
import de.bord.festival.ticket.Type;

public interface IClient {

    public void changeName(String newFirstName, String newLastName) throws ClientNameException;

    public void addTicket(Type type, TicketManager ticketmanager) throws TicketNotAvailableException, PriceLevelException;

    public void addCartToInventory();

    public Ticket getCartItem(int index);

    public void clearCart();
}

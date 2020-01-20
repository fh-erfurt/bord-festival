package de.bord.festival.client;

import de.bord.festival.exception.ClientNameException;
import de.bord.festival.exception.MailException;
import de.bord.festival.exception.TicketException;
import de.bord.festival.ticket.Ticket;
import de.bord.festival.ticket.TicketManager;

public interface IClient {

    public void changeName(String newFirstName, String newLastName) throws ClientNameException;

    public void addTicket(Ticket.TicketType type, TicketManager ticketmanager) throws TicketException;

    public void addCartToTickets();

    public Ticket getCartItem(int index);

    public void clearCart();
}

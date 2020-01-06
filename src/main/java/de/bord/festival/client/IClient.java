package de.bord.festival.client;

import de.bord.festival.exception.ClientNameException;
import de.bord.festival.exception.MailException;
import de.bord.festival.exception.TicketException;
import de.bord.festival.ticket.Ticket;
import de.bord.festival.ticket.TicketManager;

public interface IClient {

    public void nameCheck(String name) throws ClientNameException;

    public void mailCheck(String mail) throws MailException;

    public void changeName(String newFirstName, String newLastName) throws ClientNameException;

    public void addTicket(Ticket ticket, TicketManager ticketmanager) throws TicketException;

}

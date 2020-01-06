package de.bord.festival.client;

import de.bord.festival.address.Address;
import de.bord.festival.exception.ClientNameException;
import de.bord.festival.exception.MailException;
import de.bord.festival.exception.TicketException;
import de.bord.festival.ticket.Ticket;
import de.bord.festival.ticket.TicketManager;

import java.util.LinkedList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Client implements IClient {

    private int _id;
    private String _firstname;
    private String _lastname;
    private Address _address;
    private String _mail;
    private LinkedList<Ticket> _tickets;
    private LinkedList<Ticket> _cart;

    //benjamin
    private double expenditure = 0.0;

    public Client(String firstname, String lastname, String mail, int id, Address address)
            throws ClientNameException, MailException {
        nameCheck(firstname);
        nameCheck(lastname);
        mailCheck(mail);

        _tickets = new LinkedList<Ticket>();
        _cart = new LinkedList<Ticket>();
        this._id = id;
        this._firstname = firstname;
        this._lastname = lastname;
        this._address = address;
        this._mail = mail;
    }

    /**
     * Checks if an entered name is within given restictions
     * @param name
     * @throws ClientNameException
     */
    public void nameCheck(String name) throws ClientNameException {
        Pattern p = Pattern.compile("^[a-zA-ZäÄöÖüÜß]*$");
        Matcher m = p.matcher(name);

        if(!m.find()) {
            throw new ClientNameException("Name can only consist of letters");
        }
        else if(name.trim().isEmpty()) {
            throw new ClientNameException("Name can't be empty");
        }
        else if(name.length() > 50) {
            throw new ClientNameException("Name can't be longer than 50 characters");
        }
    }

    /**
     * Checks if a given e-mail-address is within given restrictions
     * @param mail
     * @throws MailException
     */
    public void mailCheck(String mail) throws MailException {
        Pattern p = Pattern.compile("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
        Matcher m = p.matcher(mail);

        if(!m.find()) {
            throw new MailException("Falsche E-Mail-Adresse");
        }
    }

    /**
     * Overrides old firstname and lastname with new ones
     * @param newFirstName
     * @param newLastName
     * @throws ClientNameException
     */
    public void changeName(String newFirstName, String newLastName) throws ClientNameException {
        nameCheck(newFirstName);
        nameCheck(newLastName);

        this._firstname = newFirstName;
        this._lastname = newLastName;
    }

    /**
     * Called when a Client buys a ticket
     * Adds ticket to ticket-array of Client,
     * @param ticket
     * @throws TicketException
     */
    public void addTicket(Ticket ticket, TicketManager ticketmanager) throws TicketException {
        if(!ticket.isAvailable()) {
            throw new TicketException("No more tickets available");
        }
        this._tickets.add(ticket);
    }


    //benjamin////////
    public void addTicket(Ticket.TicketType type, TicketManager ticketmanager) throws TicketException {
        if(!ticketmanager.getTicket(type).isAvailable()) {
            throw new TicketException("No more tickets available");
        }
            if(ticketmanager.getTicket(type)!= null) {
                this._cart.add(ticketmanager.getTicket(type));
            }
    }


    public int get_id(){
        return _id;
    }

    public Ticket get_ticket(int index) {
        return _tickets.get(index);
    }

    public void add_cartToTickets(){
        _tickets.addAll(_cart);
    }


    public Ticket get_cartItem(int index) {
        return _cart.get(index);
    }

    public int get_cartSize(){
        return _cart.size();
    }

    public int get_ticketsSize(){
        return _tickets.size();
    }

    public void clear_cart(){

        this._cart.clear();

    }

    public void setExpenditure(double expenditure) {
        this.expenditure += expenditure;
    }
    public double getExpenditure(){return expenditure;}
}

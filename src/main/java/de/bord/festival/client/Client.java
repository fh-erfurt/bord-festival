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
    private String firstname;
    private String lastname;
    private Address address;
    private String mail;
    private LinkedList<Ticket> inventory;
    private LinkedList<Ticket> cart;


    //benjamin
    private double expenditure = 0.0;


    private Client(String firstname, String lastname, String mail, int id, Address address)
    {
        inventory = new LinkedList<Ticket>();
        cart = new LinkedList<Ticket>();
        this._id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.mail = mail;
    }

    public static Client getNewClient(String firstname, String lastname, String mail, int id, Address address)
            throws ClientNameException, MailException {
        nameCheck(firstname);
        nameCheck(lastname);
        mailCheck(mail);

        return new Client(firstname, lastname, mail, id, address);
    }

    /**
     * Checks if an entered name is within given restrictions
     * @param name
     * @throws ClientNameException
     */
    public static void nameCheck(String name) throws ClientNameException {
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
    public static void mailCheck(String mail) throws MailException {
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

        this.firstname = newFirstName;
        this.lastname = newLastName;
    }

    /**
     * Called when a Client buys a ticket
     * Adds ticket to ticket-array of Client,
     * @param type type
     * @param ticketmanager ticketmanager
     * @throws TicketException
     */
    //benjamin////////
    public void addTicket(Ticket.TicketType type, TicketManager ticketmanager) throws TicketException {
        if(!ticketmanager.isAvailable(type)) {
            throw new TicketException("No more tickets available");
        }
            if(ticketmanager.getTicket(type)!= null) {
                this.cart.add(ticketmanager.getTicket(type));
            }
    }


    public void addCartToInventory(){
        inventory.addAll(cart);
    }


    public Ticket getCartItem(int index) {
        return cart.get(index);
    }

    public int getCartSize(){
        return cart.size();
    }

    public int getInventorySize(){
        return inventory.size();
    }

    public void clearCart(){

        this.cart.clear();

    }

    public void setExpenditure(double expenditure) {
        this.expenditure += expenditure;
    }
    public double getExpenditure(){return expenditure;}

}

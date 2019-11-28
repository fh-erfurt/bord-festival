package de.bord.festival.client;

import de.bord.festival.address.Address;
import de.bord.festival.exception.ClientNameException;
import de.bord.festival.ticket.Ticket;

import java.util.LinkedList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Client {
    private int _id;
    private String _firstname;
    private String _lastname;
    private Address _address;
    private String _mail;
    private LinkedList<Ticket> _tickets;

    public Client(String firstname, String lastname, String phoneNumber, int id, Address address) throws ClientNameException
    {
        nameCheck(firstname);
        nameCheck(lastname);

        _tickets = new LinkedList<Ticket>();
        this._id = id;
        this._firstname = firstname;
        this._lastname = lastname;
        this._address = address;
        this._mail = phoneNumber;
    }

    /**
     * Checks if an entered name is within given restictions
     * @param name
     * @throws ClientNameException
     */
    public void nameCheck(String name) throws ClientNameException
    {
        Pattern p = Pattern.compile("[^äÄöÖüÜßa-zA-Z]*$");
        Matcher m = p.matcher(name);

        if(m.find()) {
            throw new ClientNameException("Name can only consist of letters");
        } else if(name.trim().isEmpty()) {
            throw new ClientNameException("Name can't be empty");
        } else if(name.length() > 50) {
            throw new ClientNameException("Name can't be longer than 50 characters");
        }
    }

    /**
     * Overrides old firstname and lastname with new ones
     * @param newFirstName
     * @param newLastName
     * @throws ClientNameException
     */
    public void changeName(String newFirstName, String newLastName) throws ClientNameException
    {
        nameCheck(newFirstName);
        nameCheck(newLastName);

        this._firstname = newFirstName;
        this._lastname = newLastName;
    }
}

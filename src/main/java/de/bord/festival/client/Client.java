package de.bord.festival;

import de.bord.festival.ticket.Ticket;

import java.util.LinkedList;

public class Client {
    private int id;
    private String name;
    private Address address;
    private String phoneNumber;
    private LinkedList<Ticket> tickets;
    public Client(String name, String phoneNumber, int id, Address address){
        tickets=new LinkedList<Ticket>();
        this.id=id;
        this.name=name;
        this.address=address;
        this.phoneNumber=phoneNumber;
    }
}

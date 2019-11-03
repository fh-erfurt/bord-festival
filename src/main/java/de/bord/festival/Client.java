package de.bord.festival;

import java.util.LinkedList;

public class Client {
    private int id;
    private String name;
    private Address address;
    private String phoneNumber;
    private LinkedList<Ticket> tickets;
    public Client(String name, String phoneNumber, int id, Address address){
        this.id=id;
        this.name=name;
        this.address=address;
        this.phoneNumber=phoneNumber;
    }
}

package de.bord.festival.eventManagement;

import de.bord.festival.ticket.*;

import java.util.ArrayList;
import java.util.LinkedList;

public class Event {

    private TicketManager ticketManager;////////////

    private int id;
    private String date;
    private String name;
    private double budget;
    private LineUp lineUp;
    private LinkedList<Ticket> tickets; //verkaufte tickets
    private int maxCapacity; ///////////////// im konstruktor berechnen

    public Event(int id, String date, String name, double budget, int maxCapacity){
        lineUp=new LineUp();
        tickets=new LinkedList<Ticket>();
        this.maxCapacity=maxCapacity;
        this.budget=budget;
        this.date=date;
        this.id=id;
        this.name=name;
    }

}

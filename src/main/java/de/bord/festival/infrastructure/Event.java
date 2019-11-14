package de.bord.festival;

import de.bord.festival.ticket.Ticket;

import java.util.LinkedList;

public class Event {
    private int id;
    private String date;
    private String name;
    private double budget;
    private LineUp lineUp;
    private LinkedList<Ticket> tickets;
    private int maxCapacity;
    public Event(int id, String date, String name, double budget, LineUp lineUp, int maxCapacity){
        tickets=new LinkedList<Ticket>();
        this.maxCapacity=maxCapacity;
        this.budget=budget;
        this.date=date;
        this.id=id;
        this.name=name;
        this.lineUp=lineUp;
    }
}

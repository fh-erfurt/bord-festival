package de.bord.festival;

import java.util.LinkedList;

public class Event {
    private int id;
    private String date;
    private String name;
    private double budget;
    private LineUp lineUp;
    private LinkedList<Ticket> tickets;
    public Event(int id, String date, String name, double budget, LineUp lineUp){
        tickets=new LinkedList<Ticket>();
        this.budget=budget;
        this.date=date;
        this.id=id;
        this.name=name;
        this.lineUp=lineUp;
    }
}

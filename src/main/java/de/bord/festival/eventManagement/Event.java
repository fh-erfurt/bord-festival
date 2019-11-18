package de.bord.festival.eventManagement;

import de.bord.festival.ticket.*;

import java.util.LinkedList;

public class Event {

    private PriceManager priceManager;
    private int id;
    private String date;
    private String name;
    private double budget;
    private LineUp lineUp;
    private LinkedList<Ticket> tickets;
    private int maxCapacity;
    //bur eine Band fur bestimmten TimeSlot
    public Event(int id, String date, String name, double budget, int maxCapacity, PriceManager priceManager){
        lineUp=new LineUp();
        tickets=new LinkedList<Ticket>();
        this.maxCapacity=maxCapacity;
        this.budget=budget;
        this.date=date;
        this.id=id;
        this.name=name;
        this.priceManager = priceManager;

    }

    public int sell(){
        int i=0;
        for (double percentageValue : this.priceManager.getPriceLevels()) {

            if(percentageValue < (100* this.tickets.size() / this.maxCapacity)){
                CampingTicket.setTicketPrice(this.priceManager.getPrices()[i][0]);
                DayTicket.setTicketPrice(this.priceManager.getPrices()[i][1]);
                VIPTicket.setTicketPrice(this.priceManager.getPrices()[i][2]);
                break;
            }
            i++;
        }
        return 1;
    }
}

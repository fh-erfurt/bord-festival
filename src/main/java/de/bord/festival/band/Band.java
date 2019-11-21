package de.bord.festival.band;


import java.util.LinkedList;

public class Band {
    private int id;
    private String name;
    private String phoneNumber;
    private double priceProEvent;
    private LinkedList<EventInfo> eventInfos;

    public Band(int id, String name, String phoneNumber, double priceProEvent) {
        eventInfos = new LinkedList<>();
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.priceProEvent = priceProEvent;
    }

    public double getPriceProEvent() {
        return priceProEvent;
    }

    public void addEventInfo(EventInfo eventInfo) {
        eventInfos.add(eventInfo);
    }


    public boolean isEqualTo(Band band) {
        //the same names for 2 different bands is not allowed
        return this.name.equals(band.getName());
    }

    public String getName() {
        return name;
    }
}

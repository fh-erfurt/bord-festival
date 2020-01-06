package de.bord.festival.band;


import java.time.LocalDate;

import java.time.LocalDateTime;
import java.time.LocalTime;
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

    /**
     * @return number of plays on certain event
     */
    public int getNumberOfEventInfo(){
        return eventInfos.size();
    }

    /**
     * @param eventInfo adds new time, date and place of play
     */
    public void addEventInfo(EventInfo eventInfo) {
        eventInfos.add(eventInfo);
    }


    /**
     * The same names for 2 different bands is not allowed
     * @param object another band should be compared
     * @return true, if the given band has the same name, as this
     */
    @Override
    public boolean equals(Object object) {
        if (object instanceof Band){
            Band band=(Band)object;
            return this.name.equals(band.getName());
        }
        return false;
    }

    public String getName() {
        return name;
    }

    /**
     * removes all event info from the list
     */
    public void removeEventInfo() {
        if (!eventInfos.isEmpty()){
            eventInfos.clear();
        }
    }

    /**
     * Removes an event info at certain date and time
     * @param dateAndTime
     */
    public void removeEventInfo(LocalDateTime dateAndTime){
        LocalDate date =dateAndTime.toLocalDate();
        LocalTime time =dateAndTime.toLocalTime();
        for (int i = 0; i < eventInfos.size(); i++) {
            EventInfo currentEventInfo = eventInfos.get(i);
            if (currentEventInfo.getDate().equals(date) && currentEventInfo.getTime().equals(time)){
                eventInfos.remove(currentEventInfo);
                break;
            }
        }
    }
}

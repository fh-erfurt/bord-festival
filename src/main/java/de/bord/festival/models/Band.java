package de.bord.festival.models;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;

@Entity
public class Band extends AbstractModel{
    private String name;
    private String phoneNumber;
    private double priceProEvent;
    private LinkedList<EventInfo> eventInfos;

    public Band(String name, String phoneNumber, double priceProEvent) {
        this.eventInfos = new LinkedList<>();
        this.name = name;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.priceProEvent = priceProEvent;
    }

    public double getPriceProEvent() { return priceProEvent; }
    public int getNumberOfEventInfo(){ return eventInfos.size(); }
    public String getName() {
        return name;
    }
    public String getPhoneNumber() {return phoneNumber; }

    public void setPhoneNumber(String newNumber) { this.phoneNumber = newNumber; }

    /**
     * @param eventInfo adds new time, date and place of play
     */
    public void addEventInfo(EventInfo eventInfo) {
        eventInfos.add(eventInfo);
    }

    /**
     * Checks if the given band-name is equal to band-name of this
     * @param object the band this band should be compared to
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

    /**
     * removes all event-infos from the list
     */
    public void removeEventInfo() {
        if (!eventInfos.isEmpty()){
            eventInfos.clear();
        }
    }

    /**
     * Removes an event-info at a certain date and time
     * @param dateAndTime
     */
    public void removeEventInfo(LocalDateTime dateAndTime){
        LocalDate date = dateAndTime.toLocalDate();
        LocalTime time = dateAndTime.toLocalTime();
        for (int i = eventInfos.size() - 1; i >= 0; i--) {
            EventInfo currentEventInfo = eventInfos.get(i);
            if (currentEventInfo.getDate().equals(date) && currentEventInfo.getTime().equals(time)){
                eventInfos.remove(currentEventInfo);
                break;
            }
        }
    }
}

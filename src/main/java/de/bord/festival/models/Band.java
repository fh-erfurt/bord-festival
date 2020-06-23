package de.bord.festival.models;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalDate;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Band extends AbstractModel {
    private String name;
    private String phoneNumber;
    private double pricePerEvent;
    @OneToMany(cascade = CascadeType.ALL)
    private List<EventInfo> eventInfos;
    @OneToMany(
            mappedBy = "band"
    )
    private List<TimeSlot> timeSlots;

    public Band(){};

    public Band(String name, String phoneNumber, double pricePerEvent) {
        eventInfos = new LinkedList<>();
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.pricePerEvent = pricePerEvent;
    }

    /**
     * @param eventInfo adds new time, date and place of play
     */
    public void addEventInfo(EventInfo eventInfo) {
        eventInfos.add(eventInfo);
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

    public int getNumberOfEventInfo(){
        return eventInfos.size();
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

    public void setName(String name) { this.name = name; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setPricePerEvent(double pricePerEvent) { this.pricePerEvent = pricePerEvent; }

    public List<EventInfo> getEventInfos(){
        return this.eventInfos;
    }
    public String getName() {
        return name;
    }
    public String getPhoneNumber() { return phoneNumber; }
    public double getPricePerEvent() {
        return pricePerEvent;
    }
}

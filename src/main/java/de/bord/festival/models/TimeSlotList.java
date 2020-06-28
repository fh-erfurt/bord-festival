package de.bord.festival.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.LinkedList;
import java.util.List;

@Entity
public class TimeSlotList extends AbstractModel {
    @OneToMany(cascade = CascadeType.ALL)
    private List<TimeSlot> timeSlots;

    public TimeSlotList() {

        this.timeSlots = new LinkedList<>();
    }


    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }
}

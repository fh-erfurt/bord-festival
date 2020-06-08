package de.bord.festival.models;


import javax.persistence.Entity;
import java.time.LocalTime;

@Entity
public class TimeSlot extends AbstractModel {
    private LocalTime time;
    private Band band;
    long minutesOnStage;

    public TimeSlot(LocalTime time, Band band, long minutesOnStage) {
        this.minutesOnStage = minutesOnStage;
        this.band = band;
        this.time = time;
    }

    public String getNameOfBand() { return band.getName(); }
    public LocalTime getTime() {
        return time;
    }
    public long getMinutesOnStage() {
        return minutesOnStage;
    }

    public void setBand(Band newBand) { this.band = newBand; }
    public void setMinutesOnStage(long minutesOnStage) { this.minutesOnStage = minutesOnStage; }

    @Override
    public boolean equals(Object object) {
        if(object instanceof TimeSlot) {
            TimeSlot timeSlot = (TimeSlot)object;
            return this.time.equals(timeSlot.getTime())
                    && this.band.equals(timeSlot.getNameOfBand())
                    && this.minutesOnStage == timeSlot.getMinutesOnStage();
        }
        return false;
    }
}

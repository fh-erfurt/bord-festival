package de.bord.festival.models;

import de.bord.festival.models.Band;
import de.bord.festival.models.AbstractModel;


import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalTime;


/**
 * Contains band, time band should play and stage
 */
@Entity
public class TimeSlot extends AbstractModel {
    private LocalTime time;
    @ManyToOne
    private Band band;
    long minutesOnStage;

    public TimeSlot(){}

    public TimeSlot(LocalTime time, Band band, long minutesOnStage) {
        this.minutesOnStage = minutesOnStage;
        this.band = band;
        this.time = time;

    }

    public String getNameOfBand() {
        return band.getName();
    }

    public LocalTime getTime() {
        return time;
    }

    public long getMinutesOnStage() {
        return minutesOnStage;
    }
}

package de.bord.festival.stageManagement;
import de.bord.festival.band.Band;

import java.time.LocalDateTime;
import java.time.LocalTime;


public class TimeSlot {
    private LocalTime time;
    private Band band;
    long minutesOnStage;

    public TimeSlot(LocalTime time, Band band, long minutesOnStage){
        this.minutesOnStage=minutesOnStage;
        this.band=band;
        this.time=time;

    }
    public String getNameOfBand(){return band.getName();};
    public LocalTime getTime(){
        return time;
    }
    public long getMinutesOnStage(){
        return minutesOnStage;
    }
}

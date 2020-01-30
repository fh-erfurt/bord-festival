package de.bord.festival.band;

import de.bord.festival.stageManagement.Stage;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Contains information, that should be saved in Band-class: date, time, stage
 */
public class EventInfo {
    private LocalDate date;
    private LocalTime time;
    private Stage stage;

    /**
     * date is not known, when the EventInfo-object is created in program
     * it will be added in lineUp-object, is known only there
     *
     * @param time
     * @param stage
     */
    public EventInfo(LocalTime time, Stage stage) {
        this.stage = stage;
        this.time = time;

    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public LocalDate getDate() {
        return date;
    }
}

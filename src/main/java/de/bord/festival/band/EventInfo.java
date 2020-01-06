package de.bord.festival.band;

import de.bord.festival.stageManagement.Stage;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Contains information should be saved in Band class: date, time, stage
 */
public class EventInfo {
    private LocalDate date;
    private LocalTime time;
    private Stage stage;

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

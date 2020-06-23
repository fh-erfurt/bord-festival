package de.bord.festival.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Contains information, that should be saved in Band-class: date, time, stage
 */
@Entity
public class EventInfo extends AbstractModel {
    private LocalDate date;
    private LocalTime time;
    @OneToOne
    private Stage stage;


    public EventInfo() {}

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

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public LocalTime getTime() {
        return time;
    }

    public LocalDate getDate() {
        return date;
    }

    public Stage getStage() {
        return this.stage;
    }


}

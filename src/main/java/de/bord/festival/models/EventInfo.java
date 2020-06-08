package de.bord.festival.models;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class EventInfo extends AbstractModel{
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

    public LocalTime getTime() { return time; }
    public LocalDate getDate() { return date; }
    public Stage getStage() { return stage; }

    public void setDate(LocalDate date) { this.date = date; }

    @Override
    public boolean equals(Object object) {
        if(object instanceof EventInfo) {
            EventInfo eventInfo = (EventInfo)object;
            return this.date.equals(eventInfo.getDate())
                    && this.time.equals(eventInfo.getTime())
                    && this.stage.equals(eventInfo.getStage());
        }
        return false;
    }
}

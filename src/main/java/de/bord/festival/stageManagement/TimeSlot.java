package de.bord.festival.stageManagement;

import de.bord.festival.stageManagement.Stage;

import java.util.Date;

public class TimeSlot {
    private Date date;
    private Stage stage;
    private boolean free;
    public TimeSlot(Date date, Stage stage, boolean free){
        this.date=date;
        this.free=free;
        this.stage=stage;
    }

}

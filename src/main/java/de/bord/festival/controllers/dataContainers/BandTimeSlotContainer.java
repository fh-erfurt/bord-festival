package de.bord.festival.controllers.dataContainers;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class BandTimeSlotContainer {
    private String bandId;
    private String dateTimeToDeleteBand;


    public void setBandId(String bandId) {
        this.bandId = bandId;
    }

    public void setDateTimeToDeleteBand(String dateTimeToDeleteBand) {
        this.dateTimeToDeleteBand = dateTimeToDeleteBand;
    }

    public String getBandId() {
        return bandId;
    }

    public String getDateTimeToDeleteBand() {
        return dateTimeToDeleteBand;
    }

}

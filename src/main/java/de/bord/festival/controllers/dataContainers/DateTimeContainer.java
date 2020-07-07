package de.bord.festival.controllers.dataContainers;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public class DateTimeContainer {
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    @NotNull
    @DateTimeFormat(pattern = "H:mm")
    private LocalTime startTime;
    @NotNull
    @DateTimeFormat(pattern = "H:mm")
    private LocalTime endTime;
    private long breakBetweenTwoBands;

    public void setBreakBetweenTwoBands(long breakBetweenTwoBands) {
        this.breakBetweenTwoBands = breakBetweenTwoBands;
    }

    public long getBreakBetweenTwoBands() {
        return breakBetweenTwoBands;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }


}

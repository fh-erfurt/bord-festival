package de.bord.festival.models;

import de.bord.festival.exception.TimeSlotCantBeFoundException;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;


/**
 * Is a class, which contains all important features: start and end date, times, minutes of breaks
 * between plays, all the stages, and bands and bands and required programs
 */
@Entity
public class LineUp extends AbstractModel {
    @OneToMany(cascade = CascadeType.ALL)
    @OrderBy("id")
    private Map<LocalDate, Program> dayPrograms;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Stage> stages;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Band> bands;
    @OneToOne(cascade = CascadeType.ALL)
    private Event event;
    @NotNull
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate startDate;
    @NotNull
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate endDate;
    @NotNull
    private LocalTime startTime; //= LocalTime.of(10, 30);
    @NotNull
    private LocalTime endTime; //= LocalTime.of(23, 59);
    @NotNull
    private long breakBetweenTwoBandsInMinutes;// = 30;

    public LineUp() {
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LineUp(LocalTime startTime, LocalTime endTime, long breakBetweenTwoBandsInMinute, LocalDate startDate, LocalDate endDate, Stage stage, Event event) {
        dayPrograms = new LinkedHashMap<>();
        stages = new LinkedList<>();
        stages.add(stage);//minimum one stage should exist
        bands = new LinkedList<>();
        this.startDate = startDate;
        this.endDate = endDate;
        createProgramsBetweenStartAndEndDates();
        this.event = event;
        this.startTime = startTime;
        this.endTime = endTime;
        this.breakBetweenTwoBandsInMinutes = breakBetweenTwoBandsInMinute;

    }

    /**
     * creates programs for each day of event and saves it to the collection
     */
    private void createProgramsBetweenStartAndEndDates() {
        if (startDate.equals(endDate)) {
            createProgramForOneDay();
        } else {
            createProgramForMoreDays();
        }

    }

    private void createProgramForOneDay() {
        createProgramPutIntoMapWithDays(startDate);
    }

    private void createProgramForMoreDays() {
        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            createProgramPutIntoMapWithDays(date);
        }
        createProgramPutIntoMapWithDays(endDate);
    }

    private void createProgramPutIntoMapWithDays(LocalDate date) {
        Program program = new Program(stages.get(0), this);
        dayPrograms.put(date, program);
    }

    /**
     * Adds new stage to the event
     *
     * @param stage the object should be added
     * @return true, if the stage with the same name doesn't exist in event, otherwise return false
     */
    public boolean addStage(Stage stage) {

        if (findStage(stage.getStageName()) != null) {
            return false;
        }
        for (Map.Entry<LocalDate, Program> entry : dayPrograms.entrySet()) {
            entry.getValue().addStage(stage);
        }
        stages.add(stage);
        return true;
    }


    /**
     * Adds band to the event, is a help method for Event class
     *
     * @param band object, which should be added
     * @return the information, which is relevant for band: stage, date, time, if the timeSlot is found,
     * otherwise null
     * @throws TimeSlotCantBeFoundException, if the band plays on another stage at the same time
     */
    public EventInfo addBand(Band band) throws TimeSlotCantBeFoundException {

        for (Map.Entry<LocalDate, Program> entry : dayPrograms.entrySet()) {
            Program programOnCurrentDate = entry.getValue();
            LocalDate currentDate = entry.getKey();
            //to work with the same object we take existing band from event list
            EventInfo timeSlotWithStage;
            if (event.containsBand(band)) {
                Band oldBand=event.getBand(band);
                oldBand.setMinutesOnStage(band.getMinutesOnStage());
                timeSlotWithStage = programOnCurrentDate.addBand(oldBand);
            } else {
                timeSlotWithStage = programOnCurrentDate.addBand(band);
            }

            if (timeSlotWithStage != null) {
                return actionIfTimeSlotFound(band, timeSlotWithStage, currentDate);
            }
        }
        return null;
    }

    /**
     * Generates a complete event info, which will be saved to band
     * Checks if band is already assigned a timeslot, if yes the band won't be paid extra
     * adds to the actual costs of event
     *
     * @param band
     * @param timeSlotWithStage time slot which has received time and stage from program
     * @param currentDate       date will be saved to timeSlotWithStage
     * @return complete event info
     */
    private EventInfo actionIfTimeSlotFound(Band band, EventInfo timeSlotWithStage, LocalDate currentDate) {
        if (!containsBand(band)) {
            bands.add(band);
            //the price for event should be changed only if the band is new
            this.event.addToTheActualCosts(band.getPricePerEvent());
        }
        timeSlotWithStage.setDate(currentDate);
        return timeSlotWithStage;
    }

    public int getNumberOfStages() {
        return stages.size();
    }

    public int getNumberOfBands() {
        return bands.size();
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public long getBreakBetweenTwoBandsInMinutes() {
        return breakBetweenTwoBandsInMinutes;
    }

    public int getNumberOfDays() {

        Period diff = Period.between(startDate, endDate);
        return diff.getDays() + 1;
    }

    public Stage getFirstStage() {
        return stages.get(0);
    }

    /**
     * Removes stage from all programs and from the list of stages
     * Removes stage, if it has no set time slots and the number of stages > 1
     *
     * @return true, if the stage is removed, otherwise false
     */
    public boolean removeStage(String stageName) {
        //in the event should exist minimum one stage
        if (isStageLast()) {
            return false;
        }
        Stage foundStage = findStage(stageName);
        if ((foundStage == null) || !isStageInAllProgramsEmpty(stageName)) {
            return false;
        }
        //if the stage exists and is free, than we can remove it
        removeStageFromAllPrograms(stageName);
        stages.remove(foundStage);
        return true;
    }

    private void removeStageFromAllPrograms(String stageName) {
        for (Map.Entry<LocalDate, Program> entry : dayPrograms.entrySet()) {
            entry.getValue().removeStage(stageName);
        }
    }

    private boolean isStageLast() {
        return (stages.size() == 1);
    }

    private boolean isStageInAllProgramsEmpty(String stageName) {

        for (Map.Entry<LocalDate, Program> entry : dayPrograms.entrySet()) {
            if (entry.getValue().existOnStageTimeSlots(stageName)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Looks for a stage with given id
     *
     * @return if stage exists in event: Stage, otherwise: null
     */
    public Stage findStage(String stageName) {
        for (int i = 0; i < stages.size(); i++) {
            if (stages.get(i).getStageName().equals(stageName)) {
                return stages.get(i);
            }
        }
        return null;
    }

    /**
     * removes band from all timeslots it was assigned on
     *
     * @param band band that should be removed
     * @return if the band exists in the event list: true, otherwise: false
     */
    public boolean removeBand(Band band) {

        boolean doesBandExistsInEventList = bands.remove(band);
        if (!doesBandExistsInEventList) {
            return false;
        }
        removeBandFromAllPrograms(band);
        return true;
    }

    private void removeBandFromAllPrograms(Band band) {
        for (Map.Entry<LocalDate, Program> entry : dayPrograms.entrySet()) {
            Program currentProgram = entry.getValue();
            currentProgram.removeBand(band);
        }
    }


    /**
     * removes band from specified timeslot
     *
     * @param band        band that should be removed
     * @param dateAndTime date and time the band should be removed
     * @return if the band is removed: true, otherwise: false
     */
    public boolean removeBand(Band band, LocalDateTime dateAndTime) {
        if (!containsBand(band)) {
            return false;
        }
        //take date the band should be removed
        LocalDate date = dateAndTime.toLocalDate();

        //find program the date should be removed
        Program program = dayPrograms.get(date);
        if (program == null) {
            return false;
        }
        //find time the band should be removed
        LocalTime time = dateAndTime.toLocalTime();
        if (program.removeBand(band, time)) {
            if (isBandLast(band)) {
                bands.remove(band);
            }
            return true;
        }
        return false;
    }

    private boolean isBandLast(Band band) {
        return (band.getNumberOfEventInfo() == 1);
    }

    /**
     * Checks if given band is currently participating in event
     *
     * @param band
     * @return if band is already subscribed to a timeslot: true, otherwise: false
     */
    public boolean containsBand(Band band) {
        boolean check = false;
        for (Band value : bands) {
            if (value.equals(band)) {
                check = true;
                break;
            }
        }
        return check;
    }
    public Band getBand(Band band) {
        boolean check = false;
        for (Band value : bands) {
            if (value.equals(band)) {
                return value;
            }
        }
        return null;
    }

    public List<Band> getBands() {
        return this.bands;
    }

    public Map<LocalDate, Program> getDayPrograms() {
        return this.dayPrograms;
    }

    public List<Stage> getStages() {
        return stages;
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

    public void setBreakBetweenTwoBandsInMinutes(long breakBetweenTwoBandsInMinutes) {
        this.breakBetweenTwoBandsInMinutes = breakBetweenTwoBandsInMinutes;
    }


}

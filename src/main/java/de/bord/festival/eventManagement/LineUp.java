package de.bord.festival.eventManagement;

import de.bord.festival.address.Address;
import de.bord.festival.band.Band;
import de.bord.festival.band.EventInfo;
import de.bord.festival.exception.TimeException;
import de.bord.festival.stageManagement.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;


/**
 * Is a class, which contains all important features: start and end date, times, minutes of breaks
 * between plays, all the stages, and bands and bands and required programs
 */
public class LineUp {

    private Map<LocalDate, Program> dayPrograms;
    private LinkedList<Stage> stages;
    private LinkedList<Band> bands;
    private Event event;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime = LocalTime.of(10, 30);
    private LocalTime endTime = LocalTime.of(23, 59);
    private long breakBetweenTwoBandsInMinutes = 30;

    public LineUp(LocalDate startDate, LocalDate endDate, Stage stage, Event event) {
        dayPrograms = new LinkedHashMap<>();
        stages = new LinkedList<>();
        stages.add(stage);//minimum one stage should exists
        bands = new LinkedList<>();
        this.startDate = startDate;
        this.endDate = endDate;
        createProgramsBetweenStartAndEndDates();
        this.event=event;

    }

    /**
     * creates programs for each day of event and saves it to the collection with corresponding dates
     */
    private void createProgramsBetweenStartAndEndDates() {
        if (startDate.equals(endDate)) {
            Program programForStartDate = new Program(stages.getFirst(), this);
            dayPrograms.put(startDate, programForStartDate);
        } else {
            for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
                Program program = new Program(stages.getFirst(), this);
                dayPrograms.put(date, program);
            }
            Program program = new Program(stages.getFirst(), this);
            dayPrograms.put(endDate, program);
        }

    }

    /**
     * Adds new stage to the event
     *
     * @param stage the object should be added
     * @return true, if the stage with the same doesn't exist in event, otherwise return false
     */
    public boolean addStage(Stage stage) {

        if (findStage(stage.getId())!=null){
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
     * @param band           object, which should be added
     * @param minutesOnStage minutes the given band wants play on the stage
     * @return the information, which is relevant for band: stage, date, time, if the timeSlot is found,
     * otherwise null
     * @throws TimeException, if the band plays on another stage at the same time
     */
    public EventInfo addBand(Band band, long minutesOnStage) throws TimeException {

        for (Map.Entry<LocalDate, Program> entry : dayPrograms.entrySet()) {
            Program programOnCurrentDate = entry.getValue();
            LocalDate currentDate = entry.getKey();
            EventInfo timeSlotWithStage = programOnCurrentDate.addBand(band, minutesOnStage);
            if (timeSlotWithStage != null) {
                boolean flag = false;

                //a band exists in list of bands only once
                if (!doesBandExistsInTheList(band)) {
                    bands.add(band);
                    //the price for event should be changed only if the band is new
                    // band get money for whole event
                    this.event.addToTheActualCosts(band.getPriceProEvent());
                }
                timeSlotWithStage.setDate(currentDate);
                return timeSlotWithStage;
            }
        }
        return null;
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
        return dayPrograms.size();
    }

    /**
     * Removes stage from all programs and from the list of stages
     * Condition: only one stage exists in the list with the same address
     * Removes stage, but only then, if it has no plays on it and if it is not a last stage
     *
     * @param id id, on which the stage should be removed
     * @return true, if the stage is removed, otherwise false
     */
    public boolean removeStage(int id) {
        //in the event should exist minimum one stage
        if (stages.size() == 1) {
            return false;
        }
        Stage foundStage = findStage(id);

        if (foundStage == null) {
            return false;
        }
        for (Map.Entry<LocalDate, Program> entry : dayPrograms.entrySet()) {
            if (entry.getValue().existOnStageTimeSlots(id)) {
                return false;
            }
        }
        //if the stage exists and is free, than we can remove it
        for (Map.Entry<LocalDate, Program> entry : dayPrograms.entrySet()) {
            entry.getValue().removeStage(id);
        }
        stages.remove(foundStage);
        return true;
    }

    /**
     * Looks for a stage with the address from the parameter
     *
     * @param id id if the stage of the stage
     * @return object of the class Stage, if the stage with the address is in the list of event
     * otherwise returns null
     */
    public Stage findStage(int id) {
        for (int i = 0; i < stages.size(); i++) {
            if (stages.get(i).getId()==id) {
                return stages.get(i);
            }
        }
        return null;
    }

    /**
     * It is a help method to removeBand from Event class
     * @param band band should be removed
     * @return true, if the band exists in the event list, false otherwise
     */
    public boolean removeBand(Band band) {

        boolean doesBandExistsInEventList=bands.remove(band);
        if (!doesBandExistsInEventList){
            return false;
        }

        for(Map.Entry<LocalDate, Program> entry: dayPrograms.entrySet()){
            Program currentProgram=entry.getValue();
            currentProgram.removeBand(band);
        }
        return true;
    }

    /**
     * It is a help method for Event class method removeBand
     * @param band band should be removed
     * @param dateAndTime date and time the band should be removed
     * @return true, if the timeslot is removed, otherwise false
     */
    public boolean removeBand(Band band, LocalDateTime dateAndTime) {
        if (!doesBandExistsInTheList(band)){
            return false;
        }
        //take date the band should be removed
        LocalDate date =dateAndTime.toLocalDate();

        //find program the date should be removed
        Program program =dayPrograms.get(date);
        //find time the band should be removed
        LocalTime time =dateAndTime.toLocalTime();
        if(program.removeBand(band, time)){
            if (band.getNumberOfEventInfo()==1){
                bands.remove(band);
            }
            return true;
        }
        return false;

    }
    private boolean doesBandExistsInTheList(Band band){
        boolean flag=false;
        for (int i = 0; i < bands.size(); i++) {
            if (bands.get(i).equals(band)) {
                flag = true;
                break;
            }
        }
        return flag;
    }
}

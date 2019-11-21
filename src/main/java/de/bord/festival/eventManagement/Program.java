package de.bord.festival.eventManagement;

import de.bord.festival.address.Address;
import de.bord.festival.band.Band;
import de.bord.festival.band.EventInfo;
import de.bord.festival.exception.TimeException;
import de.bord.festival.stageManagement.Stage;
import de.bord.festival.stageManagement.TimeSlot;

import static java.time.temporal.ChronoUnit.MINUTES;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * It is a help class to the class LineUp, should not be used outside of package
 * Contains collection of timeSlots with corresponding stages
 */
class Program {
    private Map<Stage, LinkedList<TimeSlot>> programsForStages;
    private LineUp lineUp;//to access the lineUp fields

    public Program(Stage stage, LineUp lineUp) {
        this.lineUp = lineUp;
        programsForStages = new HashMap<>();
        programsForStages.put(stage, new LinkedList<>());
    }

    /**
     * Adds new stage to the event
     *
     * @param stage the object should be added
     */
    public void addStage(Stage stage) {
        programsForStages.put(stage, new LinkedList<>());
    }

    /**
     * Adds band to the event, is a help method for LineUp class
     *
     * @param band           a band should be added
     * @param minutesOnStage minutes of play on the stage
     * @return an object EventInfo, which contains stage, and time
     * @throws TimeException if the band plays on another stage at the same time
     */
    public EventInfo addBand(Band band, long minutesOnStage) throws TimeException {
        /* searching timeSlot on stage */
        for (Map.Entry<Stage, LinkedList<TimeSlot>> entry : programsForStages.entrySet()) {
            Stage currentStage = entry.getKey();
            LinkedList<TimeSlot> currentListOfTimeSlots = entry.getValue();
            //if there is no timeSlots on current stage
            if (currentListOfTimeSlots.isEmpty()) {
                // It will be at start time of the first day of festival
                LocalTime time = this.lineUp.getStartTime();
                doesAlreadyPlay(band, time);//throws exception
                TimeSlot timeSlot = new TimeSlot(time, band, minutesOnStage);
                currentListOfTimeSlots.add(timeSlot);

                return new EventInfo(time, currentStage);
            }
            //else look after the previous timeSlot
            TimeSlot previousTimeSlot = currentListOfTimeSlots.getLast();
            LocalTime newTime = getNewTime(previousTimeSlot, minutesOnStage);
            if (newTime != null) {
                doesAlreadyPlay(band, newTime);
                TimeSlot newTimeSlot = new TimeSlot(newTime, band, minutesOnStage);
                currentListOfTimeSlots.add(newTimeSlot);
                return new EventInfo(newTime, currentStage);
            }
        }
        return null;
    }

    /**
     * Checks if the band plays on another stage at the same time
     *
     * @param band band should be checked
     * @param time time should be checked
     * @throws TimeException if the band plays on another stage at the same time
     */

    private void doesAlreadyPlay(Band band, LocalTime time) throws TimeException {

        for (Map.Entry<Stage, LinkedList<TimeSlot>> entry : programsForStages.entrySet()) {
            for (int i = 0; i < entry.getValue().size(); i++) {
                LocalTime timeInTimeSlot = entry.getValue().get(i).getTime();
                String nameOfBandInTimeSlot = entry.getValue().get(i).getNameOfBand();
                //if the same time and band name exist on another stage
                if ((time.compareTo(timeInTimeSlot) == 0) && (band.getName().equals(nameOfBandInTimeSlot))) {
                    throw new TimeException("This band plays already on another stage");
                }
            }
        }
    }

    /**
     * Provides new time on the given day
     *
     * @param previousTimeSlot given timeSlot after which the band should play
     * @param minutesOnStage   minutes the given band wants play on the stage
     * @return new time, if is found, otherwise null
     */
    private LocalTime getNewTime(TimeSlot previousTimeSlot, long minutesOnStage) {

        LocalTime previousTime = previousTimeSlot.getTime().plusMinutes(previousTimeSlot.getMinutesOnStage());
        LocalTime previousTimePlusBreak = previousTime.plusMinutes(this.lineUp.getBreakBetweenTwoBandsInMinutes());

        if (canPlayBeforeTheEndOfDay(minutesOnStage, previousTimePlusBreak)) {
            return previousTimePlusBreak;
        }
        return null;
    }

    /**
     * Compares offsets between (end time - last play) and duration of playing of new band
     * Checks if the band have time to play on the certain day, on the certain stage
     * until the end of the given time
     *
     * @param minutesOnStage
     * @param previousTimePlusBreak previous time and given break between plays
     * @return true, if th band has time to play, otherwise false
     */

    private boolean canPlayBeforeTheEndOfDay(long minutesOnStage, LocalTime previousTimePlusBreak) {
        LocalTime endTime = this.lineUp.getEndTime();

        LocalTime previousTime = previousTimePlusBreak;
        if (previousTime.until(endTime, MINUTES) > minutesOnStage) {
            return true;
        }
        return false;
    }

    /**
     * Checks if on the stage already plays someone
     *
     * @param address address of the stage
     * @return true if on the stage already plays someone, otherwise false
     */
    public boolean existOnStageTimeSlots(Address address) {
        for (Map.Entry<Stage, LinkedList<TimeSlot>> entry : programsForStages.entrySet()) {
            Stage currentStage = entry.getKey();
            if (currentStage.getAddress().equals(address)) {
                return entry.getValue().isEmpty();
            }
        }
        return false;
    }

    /**
     * Removes stage from program
     * Condition: only one stage exists in the list with the same address
     *
     * @param address address of the stage
     */
    public void removeStage(Address address) {
        for (Map.Entry<Stage, LinkedList<TimeSlot>> entry : programsForStages.entrySet()) {
            if (entry.getKey().getAddress().equals(address)) {
                programsForStages.remove(entry.getKey());
            }
        }
    }
}

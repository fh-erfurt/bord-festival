package de.bord.festival.eventManagement;

import de.bord.festival.band.Band;
import de.bord.festival.band.EventInfo;
import de.bord.festival.exception.TimeSlotCantBeFoundException;
import de.bord.festival.stageManagement.Stage;
import de.bord.festival.stageManagement.TimeSlot;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static java.time.temporal.ChronoUnit.MINUTES;

/**
 * It is a help class to the class LineUp, should not be used outside of package
 * Contains collection of timeSlots with corresponding stages
 *
 * @author klass
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
     * @throws TimeSlotCantBeFoundException if the band plays on another stage at the same time
     */
    public EventInfo addBand(Band band, long minutesOnStage) throws TimeSlotCantBeFoundException {
        /* searching timeSlot on stages */
        for (Map.Entry<Stage, LinkedList<TimeSlot>> entry : programsForStages.entrySet()) {
            Stage currentStage = entry.getKey();
            LinkedList<TimeSlot> currentListOfTimeSlots = entry.getValue();
            //if there is no timeSlots on current stage
            if (currentListOfTimeSlots.isEmpty()) {
                return createEventInfoWithTimeSlotAtStart(currentListOfTimeSlots, band, currentStage, minutesOnStage);
            }
            //else look after the previous timeSlot
            TimeSlot previousTimeSlot = currentListOfTimeSlots.getLast();
            LocalTime newTime = getNewTime(previousTimeSlot, minutesOnStage);
            if (newTimeIsFound(newTime)) {
                doesAlreadyPlay(band, newTime);
                TimeSlot newTimeSlot = new TimeSlot(newTime, band, minutesOnStage);
                currentListOfTimeSlots.add(newTimeSlot);
                return new EventInfo(newTime, currentStage);
            }
        }
        return null;
    }

    public boolean newTimeIsFound(LocalTime newTime) {
        return (newTime != null);
    }

    /**
     * Creates a new EventInfo object with time as start time od event
     *
     * @param currentListOfTimeSlots list in which should be added new one
     * @param band                   which should be checkt, if it does not play on another stage at same time
     * @param currentStage           stage which should be a part of EvenetInfo object
     * @param minutesOnStage         time will be checked if there is a matching gap till end of day
     * @return EventInfo if all requirements are met, null otherwise
     * @throws TimeSlotCantBeFoundException if band plays on another stage at same time
     */
    private EventInfo createEventInfoWithTimeSlotAtStart(LinkedList<TimeSlot> currentListOfTimeSlots, Band band, Stage currentStage, long minutesOnStage) throws TimeSlotCantBeFoundException {
        // It will be at start time of the first day of festival
        LocalTime time = this.lineUp.getStartTime();
        doesAlreadyPlay(band, time);//throws exception
        if (canPlayBeforeTheEndOfDay(minutesOnStage, time)) {
            TimeSlot timeSlot = new TimeSlot(time, band, minutesOnStage);
            currentListOfTimeSlots.add(timeSlot);
            return new EventInfo(time, currentStage);
        } else {
            return null;
        }
    }


    /**
     * Checks if the band plays on another stage at the same time
     *
     * @param band band should be checked
     * @param time time should be checked
     * @throws TimeSlotCantBeFoundException if the band plays on another stage at the same time
     */

    private void doesAlreadyPlay(Band band, LocalTime time) throws TimeSlotCantBeFoundException {

        for (Map.Entry<Stage, LinkedList<TimeSlot>> entry : programsForStages.entrySet()) {
            for (int i = 0; i < entry.getValue().size(); i++) {
                LocalTime timeInTimeSlot = entry.getValue().get(i).getTime();
                String nameOfBandInTimeSlot = entry.getValue().get(i).getNameOfBand();
                //if the same time and band name exist on another stage
                if ((time.compareTo(timeInTimeSlot) == 0) && (band.getName().equals(nameOfBandInTimeSlot))) {
                    throw new TimeSlotCantBeFoundException("This band plays already on another stage");
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
        if (previousTime.until(endTime, MINUTES) >= minutesOnStage) {
            return true;
        }
        return false;
    }

    /**
     * Checks if on the stage already plays someone
     *
     * @param id id of the stage
     * @return true if on the stage already plays someone, otherwise false
     */
    public boolean existOnStageTimeSlots(int id) {
        for (Map.Entry<Stage, LinkedList<TimeSlot>> entry : programsForStages.entrySet()) {
            Stage currentStage = entry.getKey();
            if (currentStage.getId() == id) {
                return entry.getValue().isEmpty();
            }
        }
        return false;
    }

    /**
     * Removes stage from program
     * Condition: only one stage exists in the list with the same address
     *
     * @param id id of the stage
     */
    public void removeStage(int id) {
        for (Map.Entry<Stage, LinkedList<TimeSlot>> entry : programsForStages.entrySet()) {
            if (entry.getKey().getId() == id) {
                programsForStages.remove(entry.getKey());
            }
        }
    }

    /**
     * Is a help method for LineUp class method removeBand
     * Removes band fromm all time slots
     *
     * @param band the band should be removed
     */
    public void removeBand(Band band) {

        for (Map.Entry<Stage, LinkedList<TimeSlot>> entry : programsForStages.entrySet()) {
            LinkedList<TimeSlot> timeSlotsPerStage = entry.getValue();
            for (int i = 0; i < timeSlotsPerStage.size(); i++) {
                if (isTheSameBand(timeSlotsPerStage.get(i), band)) {
                    timeSlotsPerStage.remove(timeSlotsPerStage.get(i));
                }

            }
        }
    }

    /**
     * Compares the names of two bands: given band and the band, which plays on certain time slot
     *
     * @param timeslot time slot in which the band should be checked
     * @param band     given band should be removed
     * @return true, if the names are equals
     */
    public boolean isTheSameBand(TimeSlot timeslot, Band band) {
        return timeslot.getNameOfBand().equals(band.getName());
    }

    /**
     * Removes band drom certain time slot at certain time
     *
     * @param band the band, which should be removed
     * @param time the time, the band should be removed
     * @return true, if a needed time slot found and the band is equal, otherwise false
     */
    public boolean removeBand(Band band, LocalTime time) {

        for (Map.Entry<Stage, LinkedList<TimeSlot>> entry : programsForStages.entrySet()) {
            LinkedList<TimeSlot> currentTimeSlotsOnStage = entry.getValue();
            if (currentTimeSlotsOnStage.isEmpty()) {
                return false;
            }
            if (removeTimeSlotIfTimeInList(currentTimeSlotsOnStage, band, time)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param currentTimeSlotsOnStage time slots which should be checked
     * @param band                    which should be found in time slots
     * @param time                    time which should be found in time slot
     * @return true if such a time slot is found, false otherwise
     */
    private boolean removeTimeSlotIfTimeInList(LinkedList<TimeSlot> currentTimeSlotsOnStage, Band band, LocalTime time) {
        for (int i = 0; i < currentTimeSlotsOnStage.size(); i++) {
            String nameOfCurrentBand = currentTimeSlotsOnStage.get(i).getNameOfBand();
            LocalTime timeInCurrentTimeSlot = currentTimeSlotsOnStage.get(i).getTime();
            //if time and band name correspond we can remove this time slot
            if (timeInCurrentTimeSlot.equals(time) && band.getName().equals(nameOfCurrentBand)) {
                currentTimeSlotsOnStage.remove(currentTimeSlotsOnStage.get(i));
                return true;
            }
        }
        return false;
    }


}

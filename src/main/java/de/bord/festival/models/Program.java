package de.bord.festival.models;

import de.bord.festival.exception.TimeSlotCantBeFoundException;
import org.springframework.security.core.parameters.P;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.*;

import static java.time.temporal.ChronoUnit.MINUTES;

/**
 * Help class of LineUp, should not be used outside of package
 * Contains collection of timeSlots with corresponding stages
 *
 * @author klass
 */
@Entity
public class Program extends AbstractModel {
    //hibernate can not save collection of collections
    @OneToMany(cascade = CascadeType.ALL)
    @OrderBy("createdAt")
    private Map<Stage, TimeSlotList> programsForStages;
    @OneToOne(cascade = CascadeType.ALL)
    private LineUp lineUp;//to access the lineUp fields


    public Program(){

    }
    public Program(Stage stage, LineUp lineUp) {
        this.lineUp = lineUp;
        programsForStages = new TreeMap<>((o1, o2) -> {
            if (o1.getCreatedAt()!=null && o2.getCreatedAt()!=null){
                return o1.getCreatedAt().compareTo(o2.getCreatedAt());
            }
            else{
                return 1;
            }

        });
        programsForStages.put(stage, new TimeSlotList());

    }

    /**
     * Adds new stage to the event
     *
     * @param stage
     */
    public void addStage(Stage stage) {

        programsForStages.put(stage, new TimeSlotList());
    }

    /**
     * Adds band to the event, is a help method for LineUp class
     *
     * @param band           band that should be added
     * @return an object EventInfo, which contains stage, and time
     * @throws TimeSlotCantBeFoundException if the band plays on another stage at the same time
     */
    public EventInfo addBand(Band band) throws TimeSlotCantBeFoundException {
        long minutesOnStage=band.getMinutesOnStage();
        /* searching timeSlot on stages */
        for (Map.Entry<Stage,TimeSlotList> entry : programsForStages.entrySet()) {
            Stage currentStage = entry.getKey();
            List<TimeSlot> currentListOfTimeSlots = entry.getValue().getTimeSlots();
            //if there is no timeSlots on current stage
            if (currentListOfTimeSlots.isEmpty()) {
                return createEventInfoWithTimeSlotAtStart(currentListOfTimeSlots, band, currentStage, minutesOnStage);
            }
            //else look after the previous timeSlot
            TimeSlot previousTimeSlot = currentListOfTimeSlots.get(currentListOfTimeSlots.size()-1);
            LocalTime newTime = getNewTime(previousTimeSlot, minutesOnStage);
            if (newTimeIsFound(newTime)) {
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
     * Creates a new EventInfo object with start time and stage
     *
     * @param currentListOfTimeSlots new EventInfo is added to this list
     * @param band                   cannot play on the same time at a different stage
     * @param currentStage           stage which should be a part of EvenetInfo object
     * @param minutesOnStage         will be checked for a matching time gap till end of day
     * @return EventInfo if all requirements are met, null otherwise
     * @throws TimeSlotCantBeFoundException if band plays on another stage at same time
     */
    private EventInfo createEventInfoWithTimeSlotAtStart(List<TimeSlot> currentListOfTimeSlots, Band band, Stage currentStage, long minutesOnStage) throws TimeSlotCantBeFoundException {
        // It will be at start time of the first day of festival
        LocalTime time = this.lineUp.getStartTime();
        if (canPlayBeforeTheEndOfDay(minutesOnStage, time)) {
            TimeSlot timeSlot = new TimeSlot(time, band, minutesOnStage);
            currentListOfTimeSlots.add(timeSlot);
            return new EventInfo(time, currentStage);
        } else {
            return null;
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
     * Compares offsets between (end time - last timeSlot) and duration on stage
     * Checks if the band has time to play until the day is over
     *
     * @param minutesOnStage
     * @param previousTimePlusBreak previous time and given break between plays
     * @return if the band has time to play: true, otherwise: false
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
     * Checks if there is a band subscribed to a stage
     *
     * @param id id of the stage
     * @return if a band plays on the stage: true, otherwise: false
     */
    public boolean existOnStageTimeSlots(int id) {
        for (Map.Entry<Stage, TimeSlotList> entry : programsForStages.entrySet()) {
            Stage currentStage = entry.getKey();
            if (currentStage.getIdentifier() == id) {
                return !entry.getValue().getTimeSlots().isEmpty();
            }
        }
        return false;
    }

    /**
     * Removes stage from program
     * Condition: only one stage exists in the list with the same id
     *
     * @param id id of the stage
     */
    public void removeStage(int id) {
        programsForStages.entrySet().removeIf(entry->entry.getKey().getIdentifier()==id);
    }

    /**
     * Help method for LineUp class method removeBand
     * Removes band from all time slots
     *
     * @param band the band should be removed
     */
    public void removeBand(Band band) {

        for (Map.Entry<Stage, TimeSlotList> entry : programsForStages.entrySet()) {
            List<TimeSlot> timeSlotsPerStage = entry.getValue().getTimeSlots();
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
     * Removes band from certain time slot
     *
     * @param band the band, which should be removed
     * @param time the time, the band should be removed
     * @return true, if a needed time slot found and the band is equal, otherwise false
     */
    public boolean removeBand(Band band, LocalTime time) {

        for (Map.Entry<Stage, TimeSlotList> entry : programsForStages.entrySet()) {
            List<TimeSlot> currentTimeSlotsOnStage = entry.getValue().getTimeSlots();
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
    private boolean removeTimeSlotIfTimeInList(List<TimeSlot> currentTimeSlotsOnStage, Band band, LocalTime time) {
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
    public Map<Stage, TimeSlotList> getProgramsForStages() {
        return programsForStages;
    }


}

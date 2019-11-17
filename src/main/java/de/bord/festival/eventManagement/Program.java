package de.bord.festival.eventManagement;

import de.bord.festival.band.Band;
import de.bord.festival.stageManagement.Stage;
import de.bord.festival.stageManagement.TimeSlot;
import static java.time.temporal.ChronoUnit.MINUTES;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Program {
    private Map<Stage, LinkedList<TimeSlot>> programsForStages;
    private LineUp lineUp;
    public Program(Stage stage, LineUp lineUp) {
        this.lineUp=lineUp;
        programsForStages = new HashMap<>();
        programsForStages.put(stage, new LinkedList<TimeSlot>());
    }

    public void addStage(Stage stage) {
        programsForStages.put(stage, new LinkedList<TimeSlot>());
    }

    public boolean addBand(Band band, long minutesOnStage, LocalDate currentDate)throws Exception {
        //searching timeSlot on stage
        for (Map.Entry<Stage, LinkedList<TimeSlot>> entry : programsForStages.entrySet()) {
            Stage currentStage = entry.getKey();
            LinkedList<TimeSlot> currentListOfTimeSlots = entry.getValue();
            //if there is no timeSlots on current stage
            if (currentListOfTimeSlots.isEmpty()) {
                // It will be at start time of the first day of festival
                LocalTime time = this.lineUp.getStartTime();
                doesAlreadyPlay(band, time);
                TimeSlot timeSlot = new TimeSlot(time, band, minutesOnStage);
                currentListOfTimeSlots.add(timeSlot);
                programsForStages.put(currentStage, currentListOfTimeSlots);
                return true;
            }
            //look after the previous timeSlot
            TimeSlot previousTimeSlot = currentListOfTimeSlots.getLast();
            LocalTime newTime=getNewTime(previousTimeSlot, minutesOnStage, currentDate);
            if ( newTime!= null) {

                doesAlreadyPlay(band, newTime);

                TimeSlot newTimeSlot = new TimeSlot(newTime, band, minutesOnStage);
                currentListOfTimeSlots.add(newTimeSlot);
                return true;
            }

        }
        return false;
    }
    //does the band plays on another stage at the same time
    private void doesAlreadyPlay(Band band, LocalTime time) throws Exception{

        for(Map.Entry<Stage, LinkedList<TimeSlot>> entry : programsForStages.entrySet()){
            for(int i=0; i<entry.getValue().size(); i++){
                LocalTime timeInTimeSlot=entry.getValue().get(i).getTime();
                String nameOfBandInTimeSlot=entry.getValue().get(i).getNameOfBand();
                //if the same time and band name exist on another stage
                if(time.compareTo(timeInTimeSlot)==0 && band.getName()==nameOfBandInTimeSlot){
                    throw new Exception("This band plays already on another stage");
                }
            }
        }
    }
    private LocalTime getNewTime(TimeSlot currentTimeSlot, long minutesOnStage, LocalDate currentDate) {

        LocalTime previousTime = currentTimeSlot.getTime().plusMinutes(currentTimeSlot.getMinutesOnStage());
        LocalTime previousTimePlusBreak= previousTime.plusMinutes(this.lineUp.getBreakBetweenTwoBandsInMinutes());
        LocalTime newTime = previousTimePlusBreak.plusMinutes(minutesOnStage);

        if (canPlayBeforeTheEndOfDay(minutesOnStage, previousTimePlusBreak)){
            return previousTimePlusBreak;
        }
        return null;
    }
    private boolean canPlayBeforeTheEndOfDay(long minutesOnStage, LocalTime previousTimePlusBreak){
        //comparing offsets between end time - last play and duration of playing of new band
        LocalTime endTime=this.lineUp.getEndTime();
        LocalTime previousTime=previousTimePlusBreak;

        if (previousTime.until(endTime, MINUTES)>minutesOnStage){
            return true;
        }
        return false;
    }
}

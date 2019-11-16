package de.bord.festival.eventManagement;

import de.bord.festival.band.Band;
import de.bord.festival.stageManagement.Stage;
import de.bord.festival.stageManagement.TimeSlot;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Program {
    private Map<Stage ,TimeSlot> timeSlots;
    public Program(){
        timeSlots =new HashMap<Stage, TimeSlot>();
    }

    public boolean addBand(Band band, long minutesOnStage, Stage stage, LocalTime startTime,
                           long breakBetweenTwoBandsInMinutes,LocalTime endTime){
        //if there is no timeSlot yet, we create it
        if(timeSlots.size()==0){
            // It will be at start time of the first day of festival
            LocalTime time=startTime;
            TimeSlot timeSlot=new TimeSlot(time, stage,band,minutesOnStage);
            timeSlots.put(stage, timeSlot);
            return true;
        }

        //else iterate all timeSlots and search place
        for(int i = 0; i< timeSlots.size()-1; i++){
            TimeSlot currentTimeSlot= timeSlots.get(i);
            LocalTime newTime=findEmptySpaceBetweenTwoTimeSlots(currentTimeSlot, timeSlots.get(i+1), breakBetweenTwoBandsInMinutes);
            if(newTime!=null){
                TimeSlot newTimeSlot=new TimeSlot(newTime, stage, band, minutesOnStage);
                timeSlots.put(stage, newTimeSlot);
                return true;
            }

        }
        return false;



    }
    private LocalTime findEmptySpaceBetweenTwoTimeSlots( TimeSlot currentTimeSlot,  TimeSlot nextTimeSlot, long pause){
        //current timeslot time+ minutes the band needs to play + 2 breaks
        LocalTime endOfLastTimeSlot= currentTimeSlot.getTime().plusMinutes(currentTimeSlot.getMinutesOnStage()+2*pause);
        if(endOfLastTimeSlot.isBefore(nextTimeSlot.getTime())){
            LocalTime nextTime=endOfLastTimeSlot.minusMinutes(pause);
            return nextTime;
        }
        return null;
    }
}

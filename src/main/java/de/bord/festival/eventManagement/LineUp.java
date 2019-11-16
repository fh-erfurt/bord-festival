package de.bord.festival.eventManagement;
import de.bord.festival.band.Band;
import de.bord.festival.stageManagement.Stage;


import java.time.LocalDate;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class LineUp {
   private Map<LocalDate, Program> lineUp;
   LinkedList<Stage> stages;
   LinkedList<Band> bands;

    private static LocalDate startDate;
    private static LocalDate endDate;
    private static LocalTime startTime=LocalTime.of(10, 30);
    private static LocalTime endTime=LocalTime.of(00, 00);
    private static long breakBetweenTwoBandsInMinutes=30;

    public LineUp(LocalDate _startDate, LocalDate _endDate, Stage stage){
       lineUp=new HashMap<LocalDate, Program>();//Map is not a collection, it is an interface
       stages=new LinkedList<Stage>();
       stages.add(stage);
       bands=new LinkedList<Band>();

       startDate=_startDate;
       endDate =_endDate;

    }

    public boolean addBand(Band band, long minutesOnStage){
        //if the lineUp is still empty, we create new program on startDate on the first stage
        if (lineUp.size()==0){
            Program program=new Program();
            program.addBand(band, minutesOnStage, stages.getFirst(), startTime,breakBetweenTwoBandsInMinutes, endTime);
            lineUp.put(startDate, program);
            bands.add(band);

        }

        //iterates all programs to find a timeslot for a band
        for(Map.Entry<LocalDate, Program> entry: lineUp.entrySet() ){
            //iterates all stages
           for(int i=0; i<lineUp.size(); i++) {

               if (entry.getValue().addBand(band, minutesOnStage, stages.get(i), startTime, breakBetweenTwoBandsInMinutes, endTime)) {
                   return true;
               }
           }
       }


        return false;
    }


}

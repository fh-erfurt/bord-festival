package de.bord.festival.eventManagement;
import de.bord.festival.band.Band;
import de.bord.festival.stageManagement.Stage;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class LineUp {
    private Map<LocalDate, Program> lineUp;
    private LinkedList<Stage> stages;
    private LinkedList<Band> bands;

    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime = LocalTime.of(10, 30);
    private LocalTime endTime = LocalTime.of(23, 59);
    private long breakBetweenTwoBandsInMinutes = 30;

    public LineUp(LocalDate startDate, LocalDate endDate, Stage stage) {
        lineUp = new HashMap<>();//Map is not a collection, it is an interface
        stages = new LinkedList<>();
        stages.add(stage);//minimum one stage should exists
        bands = new LinkedList<>();
        this.startDate = startDate;
        this.endDate = endDate;

        createProgramsBetweenStartAndEndDates();

    }

    private void createProgramsBetweenStartAndEndDates() {
        if (startDate.equals(endDate)) {
            Program programForStartDate = new Program(stages.getFirst(), this);
            lineUp.put(startDate, programForStartDate);
        }
        else{
            for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
                Program program = new Program(stages.getFirst(), this);
                lineUp.put(date, program);
            }
            Program program = new Program(stages.getFirst(), this);
            lineUp.put(endDate, program);
        }

    }
    public void addStage(Stage stage) {
        for (Map.Entry<LocalDate, Program> entry : lineUp.entrySet()) {
            entry.getValue().addStage(stage);
        }
        stages.add(stage);

    }
    public boolean addBand(Band band, long minutesOnStage)throws Exception {

        for (Map.Entry<LocalDate, Program> entry : lineUp.entrySet()) {
            Program programOnCurrentDate = entry.getValue();
            LocalDate currentDate=entry.getKey();
            boolean timeSlotFound = programOnCurrentDate.addBand(band,minutesOnStage,currentDate);
            if (timeSlotFound) {
                boolean flag=false;
                //a band exists in list of bands only once
                for(int i=0; i<bands.size(); i++){
                    if(bands.get(i).isEqualTo(band)){
                        flag=true;
                        break;
                    }
                }
                if(!flag){bands.add(band);}
                return true;
            }
        }
        return false;
    }
    public int getNumberOfStages(){return stages.size();}
    public int getNumberOfBands(){
        return bands.size();
    }

    public LocalDate getStartDate(){
        return startDate;
    }
    public LocalDate getEndDate(){
        return endDate;
    }
    public LocalTime getStartTime(){
        return startTime;
    }
    public LocalTime getEndTime(){
        return endTime;
    }
    public long getBreakBetweenTwoBandsInMinutes(){return breakBetweenTwoBandsInMinutes;};

}

package de.bord.festival.eventManagement;

import de.bord.festival.band.Band;
import de.bord.festival.stageManagement.Stage;
import de.bord.festival.ticket.Ticket;

import java.time.LocalDate;
import java.util.LinkedList;

public class Event {

    private int id;
    private String name;
    private double budget;
    private double actualBudget=0;
    private LineUp lineUp;
    private LinkedList<Ticket> tickets;

    private int maxCapacity;
    //bur eine Band fur bestimmten TimeSlot
    public Event(int id, LocalDate startDate, LocalDate endDate, String name,
                 double budget, int maxCapacity, Stage stage)throws Exception{
        if(endDate.isBefore(startDate)){
            throw new Exception("End date can't be before start date");
        }
        lineUp=new LineUp(startDate, endDate, stage);
        tickets=new LinkedList<Ticket>();

        this.maxCapacity=maxCapacity;
        this.budget=budget;
        this.id=id;
        this.name=name;

    }
    public int getNumberOfBands(){
        return lineUp.getNumberOfBands();
    }
    public int getNumberOfStages(){
        return lineUp.getNumberOfStages();
    }
    private boolean isNewBandAffordable(Band band){return actualBudget+band.getPriceProEvent()<=budget; }

    public void addStage(Stage stage){
        lineUp.addStage(stage);
    }

    public boolean addBand(Band band, long minutesOnStage) throws Exception{
        if(!isNewBandAffordable(band)){
            throw new Exception("The budget is not enough for this band");
        }
        return lineUp.addBand(band, minutesOnStage);
    }

}

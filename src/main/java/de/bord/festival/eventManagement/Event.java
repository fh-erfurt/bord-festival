package de.bord.festival.eventManagement;

import de.bord.festival.address.Address;
import de.bord.festival.band.Band;
import de.bord.festival.band.EventInfo;
import de.bord.festival.exception.BudgetException;
import de.bord.festival.exception.DateException;
import de.bord.festival.exception.TimeException;
import de.bord.festival.stageManagement.Stage;
import de.bord.festival.ticket.Ticket;
import de.bord.festival.ticket.TicketManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;

/**
 * Represents an festival with required features
 */
public class Event {

    private TicketManager ticketManager;
// bool automaticPriceLevelChange;    //
// wenn ich auf automatik wechsel muss das preislevel aktualisiert werden     TODO
 // im manuellen modus kann ich selbst das pricelevel angeben
    private int id;
    private String name;
    private final double budget;//budget for bands
    private double actualCosts = 0;
    private LineUp lineUp;
    private LinkedList<Ticket> tickets;//bought tickets
    private int maxCapacity;


    public Event(int id, LocalDate startDate, LocalDate endDate, String name,
                 double budget, int maxCapacity, Stage stage) throws DateException {
        if (endDate.isBefore(startDate)) {
            throw new DateException("End date can't be before start date");
        }
        lineUp = new LineUp(startDate, endDate, stage, this);
        tickets = new LinkedList<>();
        this.maxCapacity = maxCapacity;
        this.budget = budget;
        this.id = id;
        this.name = name;

    }


    public int getNumberOfBands() {
        return lineUp.getNumberOfBands();
    }

    public int getNumberOfStages() {
        return lineUp.getNumberOfStages();
    }

    public int getNumberOfDays() {
        return lineUp.getNumberOfDays();
    }

    /**
     * detects if the price of band is affordable for the event budget
     *
     * @param band provide the price (with the function getPrice), which should be compared in the method
     * @return true if the band is affordable for the budget of an event, otherwise false
     */
    private boolean isNewBandAffordable(Band band) {
        return actualCosts + band.getPriceProEvent() <= budget;
    }

    /**
     * Adds new stage to the event
     * Condition: only one stage exists in the list with the same address
     *
     * @param stage the object should be added
     * @return true, if the stage with the same doesn't exist in event, otherwise return false
     */
    public boolean addStage(Stage stage) {
        return lineUp.addStage(stage);
    }

    /**
     * Removes stage, but only then, if it has no plays on it and if it is not a last stage
     * Condition: only one stage exists in the list with the same address
     *
     * @param address the address, on which the stage should be removed
     * @return true, if the stage is removed, otherwise false
     */
    public boolean removeStage(Address address) {
        return lineUp.removeStage(address);
    }

    /**
     * Adds band to the event
     *
     * @param band           object, which should be added
     * @param minutesOnStage minutes the given band wants play on the stage
     * @return the information, which is relevant for band: stage, date, time, if the timeSlot is found,
     * otherwise null
     * @throws BudgetException, if the band is to expensive
     * @throws TimeException,   if the band plays on another stage at the same time
     */
    public EventInfo addBand(Band band, long minutesOnStage) throws BudgetException, TimeException {
        if (!isNewBandAffordable(band)) {
            throw new BudgetException("The budget is not enough for this band");
        }
        EventInfo eventInfo = lineUp.addBand(band, minutesOnStage);
        if (eventInfo != null) {
            band.addEventInfo(eventInfo);

        }
        return eventInfo;
    }

    /**
     * Removes the band from entire event: from all programs and timeslots
     * @param band the band, that should be removed
     * @return true, if the band is removed, otherwise false
     */
    public boolean removeBand(Band band){
        if(lineUp.removeBand(band)){
            actualCosts-=band.getPriceProEvent();
            band.removeEventInfo();
            return true;
        }
        return false;
    }

    /**
     * Removes the band only from given date, time and stage
     * @param band the band, that should be removed
     * @param dateAndTime date and time, on which the band should be removed
     * @return
     */
    public boolean removeBand(Band band, LocalDateTime dateAndTime){

        if(this.lineUp.removeBand(band, dateAndTime)){
            band.removeEventInfo(dateAndTime);
            //if band does not play on event anymore
            if (band.getNumberOfEventInfo()==0){
                actualCosts-=band.getPriceProEvent();
            }
            return true;
        }
        return false;
    }

    /**
     * Adds to costs to the actual costs variable
     * LineUp calls it, if the band is new on event, because band receives money for entire event
     * @param amount
     */
    public void addToTheActualCosts(double amount){
        actualCosts += amount;
    }
}

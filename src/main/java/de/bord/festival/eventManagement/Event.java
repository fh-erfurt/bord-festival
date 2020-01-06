package de.bord.festival.eventManagement;

import de.bord.festival.address.Address;
import de.bord.festival.band.Band;
import de.bord.festival.band.EventInfo;
import de.bord.festival.client.Client;
import de.bord.festival.exception.BudgetException;
import de.bord.festival.exception.DateException;
import de.bord.festival.exception.TicketManagerException;
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
    private int id;
    private String name;
    private final double budget;//budget for bands
    private double actualCosts = 0;
    private LineUp lineUp;
    private LinkedList<Client> client;  ///// KANN WEG ????????
    private int maxCapacity;
    private Address address;

    public Event(int id, LocalDate startDate, LocalDate endDate, String name,
                 double budget, int maxCapacity, Stage stage, TicketManager ticketManager, Address address) throws DateException {
        if (endDate.isBefore(startDate)) {
            throw new DateException("End date can't be before start date");
        }
        lineUp = new LineUp(startDate, endDate, stage, this);
        client = new LinkedList<>();
        this.maxCapacity = maxCapacity;
        this.budget = budget;
        this.id = id;
        this.name = name;
        this.ticketManager = ticketManager;
        this.address=address;

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
     * @param id id, on which the stage should be removed
     * @return true, if the stage is removed, otherwise false
     */
    public boolean removeStage(int id) {
        return lineUp.removeStage(id);
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
     *
     * @param band the band, that should be removed
     * @return true, if the band is removed, otherwise false
     */
    public boolean removeBand(Band band) {
        if (lineUp.removeBand(band)) {
            actualCosts -= band.getPriceProEvent();
            band.removeEventInfo();
            return true;
        }
        return false;
    }

    /**
     * Removes the band only from given date, time and stage
     *
     * @param band        the band, that should be removed
     * @param dateAndTime date and time, on which the band should be removed
     * @return
     */
    public boolean removeBand(Band band, LocalDateTime dateAndTime) {

        if (this.lineUp.removeBand(band, dateAndTime)) {
            band.removeEventInfo(dateAndTime);
            //if band does not play on event anymore
            if (band.getNumberOfEventInfo() == 0) {
                actualCosts -= band.getPriceProEvent();
            }
            return true;
        }
        return false;
    }

    /**
     * Adds to costs to the actual costs variable
     * LineUp calls it, if the band is new on event, because band receives money for entire event
     *
     * @param amount
     */
    public void addToTheActualCosts(double amount) {
        actualCosts += amount;
    }

    /**
     * number of tickets
     * @return
     */
    public int getnDaytickets() { return ticketManager.getnDaytickets(); }
    public int getnCampingtickets() { return ticketManager.getnCampingtickets(); }
    public int getnViptickets() { return ticketManager.getnViptickets(); }
    public int totalNumberOfTickets(){return ticketManager.totalNumberOfTickets();}

    /**
     *  number of sold Tickets
     * @return
     */
    public int getnSoldDaytickets(){ return ticketManager.getnSoldDaytickets();}
    public int getnSoldCampingtickets(){ return ticketManager.getnSoldCampingtickets();}
    public int getnSoldViptickets(){ return ticketManager.getnSoldViptickets();}
    public int totalNumberOfSoldTickets(){ return ticketManager.totalNumberOfSoldTickets();}
    public double totalNumberOfSoldTicketsInPercent(){return ticketManager.totalNumberOfSoldTicketsInPercent();}

    /**
     *  number of tickets left
     *
     */
    public int getnDayticketsLeft() { return ticketManager.getnDayticketsLeft(); }
    public int getnCampingticketsLeft() { return ticketManager.getnCampingticketsLeft(); }
    public int getnVipticketsLeft() { return ticketManager.getnVipticketsLeft(); }
    public int totalNumberOfTicketsLeft(){return ticketManager.totalNumberOfTicketsLeft();}


    public Client sellTickets(Client client /* ,LocalDate date */) throws TicketManagerException{
        return ticketManager.sellTickets(client /* ,LocalDate date */);
    }


    /**
     *  shows the income from sold tickets
     */
    public double getIncomeTicketSales(){
        return ticketManager.getIncomeTicketSales();
    }

    /**
     *  the index of the actual price level
     */
    public int getActualPriceLevelIndex(){return ticketManager.getActualPriceLevelIndex();}


    /**
     *
     * @param isPriceLevelChangeAutomatic true for automatic, false for manually price level change
     */
    public void setAutomaticPriceLevelChange(boolean isPriceLevelChangeAutomatic) throws TicketManagerException {
        ticketManager.setAutomaticPriceLevelChange(isPriceLevelChangeAutomatic);
    }

    /**
     * shows whether the price level changes automatically
     * @return
     */
    public boolean getAutomaticPriceLevelChange(){
        return ticketManager.getAutomaticPriceLevelChange();
    }

    public boolean setPriceLevel(int index){return ticketManager.setPriceLevel(index);}

}

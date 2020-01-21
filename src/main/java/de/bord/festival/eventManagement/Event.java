package de.bord.festival.eventManagement;

import de.bord.festival.address.Address;
import de.bord.festival.band.Band;
import de.bord.festival.band.EventInfo;
import de.bord.festival.client.Client;
import de.bord.festival.exception.TicketManagerException;
import de.bord.festival.exception.BudgetOverflowException;
import de.bord.festival.exception.DateDisorderException;
import de.bord.festival.exception.TimeSlotCantBeFoundException;
import de.bord.festival.stageManagement.Stage;
import de.bord.festival.ticket.Ticket;
import de.bord.festival.ticket.TicketManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;

/**
 * Represents a festival with required features
 *
 * example (ArrayList<PriceLevel> priceLevels) for sensible TicketManager creation:
 * TicketManager
 * PriceLevels[0]: PercentageForPricelevel=25.00, dayTicketPrice=30.00, CampingTicketPrice=40.00, VipTicketPrice=60.00
 * PriceLevels[1]: PercentageForPricelevel=52.25, dayTicketPrice=30.00, CampingTicketPrice=49.00, VipTicketPrice=80.00
 * PriceLevels[2]: PercentageForPricelevel=89.99, dayTicketPrice=40.99, CampingTicketPrice=51.49, VipTicketPrice=89.55
 * PriceLevels[3]: PercentageForPricelevel=100.00, dayTicketPrice=45.00, CampingTicketPrice=55.00, VipTicketPrice=101.12
 *
 * it is recommended to set the last PercentageForPricelevel to 100.00
 */
public class Event {


    private TicketManager ticketManager;
    private int id;
    private String name;
    private final double budget;//budget for bands
    private double actualCosts = 0;
    private LineUp lineUp;
    private LinkedList<Client> client;
    private int maxCapacity;
    private Address address;

    private Event(int id, LocalDate startDate, LocalDate endDate, String name,
                 double budget, int maxCapacity, Stage stage, TicketManager ticketManager, Address address){

        lineUp = new LineUp(startDate, endDate, stage, this);
        client = new LinkedList<>();
        this.maxCapacity = maxCapacity;
        this.budget = budget;
        this.id = id;
        this.name = name;
        this.ticketManager = ticketManager;
        this.address=address;

    }
    public  static Event getNewEvent(int id, LocalDate startDate, LocalDate endDate, String name,
                                     double budget, int maxCapacity, Stage stage, TicketManager ticketManager, Address address) throws DateDisorderException{
        if (endDate.isBefore(startDate)) {
            throw new DateDisorderException("End date can't be before start date");
        }

        return new Event(id, startDate, endDate, name, budget, maxCapacity, stage, ticketManager, address);

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
     * @throws BudgetOverflowException, if the band is to expensive
     * @throws TimeSlotCantBeFoundException,   if the band plays on another stage at the same time
     */
    public EventInfo addBand(Band band, long minutesOnStage) throws BudgetOverflowException, TimeSlotCantBeFoundException {
        if (!isNewBandAffordable(band)) {
            throw new BudgetOverflowException("The budget is not enough for this band");
        }
        EventInfo eventInfo = lineUp.addBand(band, minutesOnStage);
        if (eventInfo != null) {
            band.addEventInfo(eventInfo);
            return eventInfo;

        }
        else{
            throw new TimeSlotCantBeFoundException("There is not found any time slot");
        }

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
            if (playsBandOnEvent(band)) {
                actualCosts -= band.getPriceProEvent();
            }
            return true;
        }
        return false;
    }
    public boolean playsBandOnEvent(Band band){
        return (band.getNumberOfEventInfo() == 0);
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
    public int getNumberOfDayTickets() { return ticketManager.getNumberOfDayTickets(); }
    public int getNumberOfCampingTickets() { return ticketManager.getNumberOfCampingTickets(); }
    public int getNumberOfVipTickets() { return ticketManager.getNumberOfVipTickets(); }
    public int totalNumberOfTickets(){return ticketManager.totalNumberOfTickets();}

    /**
     *  number of sold Tickets
     * @return
     */
    public int getNumberOfSoldDaytickets(){ return ticketManager.getNumberOfSoldDayTickets();}
    public int getNumberOfSoldCampingtickets(){ return ticketManager.getNumberOfSoldCampingTickets();}
    public int getNumberOfSoldViptickets(){ return ticketManager.getNumberOfSoldVipTickets();}
    public int totalNumberOfSoldTickets(){ return ticketManager.totalNumberOfSoldTickets();}
    public double totalNumberOfSoldTicketsInPercent(){return ticketManager.totalNumberOfSoldTicketsInPercent();}

    /**
     *  number of tickets left
     *
     */
    public int getNumberOfDayTicketsLeft() { return ticketManager.getNumberOfDayTicketsLeft(); }
    public int getNumberOfCampingTicketsLeft() { return ticketManager.getNumberOfCampingTicketsLeft(); }
    public int getNumberOfVipTicketsLeft() { return ticketManager.getNumberOfVipTicketsLeft(); }
    public int totalNumberOfTicketsLeft(){return ticketManager.totalNumberOfTicketsLeft();}


    public void setTicketStdPrice(double stdPrice, Ticket.TicketType type){
        this.ticketManager.setTicketStdPrice(stdPrice, type);
    }

    public void setTicketDescription(String description, Ticket.TicketType type){
        this.ticketManager.setTicketDescription(description, type);
    }

    public boolean sellTickets(Client client) throws TicketManagerException{
        return ticketManager.sellTickets(client);
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

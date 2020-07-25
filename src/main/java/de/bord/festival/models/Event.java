package de.bord.festival.models;

import de.bord.festival.eventManagement.IEvent;
import de.bord.festival.exception.*;
import de.bord.festival.ticket.DayTicket;
import de.bord.festival.ticket.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static javax.persistence.TemporalType.DATE;

/**
 * Represents a festival with required features
 * <p>
 * example (ArrayList<PriceLevel> priceLevels) for sensible TicketManager creation:
 * TicketManager
 * PriceLevels[0]: PercentageForPricelevel=25.00, dayTicketPrice=30.00, CampingTicketPrice=40.00, VipTicketPrice=60.00
 * PriceLevels[1]: PercentageForPricelevel=52.25, dayTicketPrice=30.00, CampingTicketPrice=49.00, VipTicketPrice=80.00
 * PriceLevels[2]: PercentageForPricelevel=89.99, dayTicketPrice=40.99, CampingTicketPrice=51.49, VipTicketPrice=89.55
 * PriceLevels[3]: PercentageForPricelevel=100.00, dayTicketPrice=45.00, CampingTicketPrice=55.00, VipTicketPrice=101.12
 * <p>
 * it is recommended to set the last PercentageForPricelevel to 100.00
 */
@Entity
public class Event extends AbstractModel implements IEvent {
    @OneToOne(cascade = CascadeType.ALL)
    private TicketManager ticketManager;
    @NotNull
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters")
    private String name;
    @NotNull
    @Min(value=1, message = "Budget should be more than zero")
    private BigDecimal budget;
    @Transient
    private double actualCosts = 0;
    @OneToOne(cascade = CascadeType.ALL)
    private LineUp lineUp;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Client> clients;

    public void addClient(Client client){
        this.clients.add(client);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }


    public double getActualCosts() {
        return actualCosts;
    }


    //@NotNull
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;


    public Event() {
    }

    private Event(LocalTime startTime, LocalTime endTime, long breakBetweenTwoBandsInMinute,
                  LocalDate startDate, LocalDate endDate, String name,
                  BigDecimal budget, Stage stage,
                  TicketManager ticketManager, Address address) {

        lineUp = new LineUp(startTime, endTime, breakBetweenTwoBandsInMinute, startDate, endDate, stage, this);
        clients = new LinkedList<>();
        this.budget = budget;
        this.name = name;
        this.ticketManager = ticketManager;
        this.address = address;

    }

    /**
     * Using static function to avoid exception in constructor of event
     *
     * @throws DateDisorderException if end date<start date
     */
    public static Event getNewEvent(LocalTime startTime, LocalTime endTime,
                                    long breakBetweenTwoBandsInMinute, LocalDate startDate,
                                    LocalDate endDate, String name,
                                    BigDecimal budget, Stage stage,
                                    TicketManager ticketManager, Address address) throws DateDisorderException, TimeDisorderException {
        if (endDate.isBefore(startDate)) {
            throw new DateDisorderException("End date can't be before start date");
        }
        if (endTime.isBefore(startTime)) {
            throw new TimeDisorderException("End time can not be over 23 : 59 and can not be before start time");
        }

        return new Event(startTime, endTime, breakBetweenTwoBandsInMinute, startDate, endDate, name, budget, stage, ticketManager, address);

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
     * @param band provides the price (with the function getPrice), which should be compared in method
     * @return true if the band is affordable for the budget of an event, otherwise false
     */
    private boolean isNewBandAffordable(Band band) {
        return actualCosts + band.getPricePerEvent() <= budget.doubleValue();
    }

    /**
     * see lineUp.addStage(Stage stage)
     *
     * @param stage
     * @return true, if the stage already exist in event, otherwise return false
     */
    public boolean addStage(Stage stage) {
        return lineUp.addStage(stage);
    }

    /**
     * Removes stage from all programs and from the list of stages
     * Removes stage, if it has no set time slots and the number of stages > 1
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
     * @return the information, which is relevant for band: stage, date, time, if the timeSlot is found,
     * otherwise null
     * @throws BudgetOverflowException,      if the band is to expensive
     * @throws TimeSlotCantBeFoundException, if the band plays on another stage at the same time
     */
    public EventInfo addBand(Band band) throws BudgetOverflowException, TimeSlotCantBeFoundException {
        if (!isNewBandAffordable(band)) {
            throw new BudgetOverflowException("The budget is not enough for this band");
        }
        EventInfo eventInfo = lineUp.addBand(band);
        if (eventInfo != null) {
            band.addEventInfo(eventInfo);
            return eventInfo;

        } else {
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
            actualCosts -= band.getPricePerEvent();
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
                actualCosts -= band.getPricePerEvent();
            }
            return true;
        }
        return false;
    }

    public boolean playsBandOnEvent(Band band) {
        return (band.getNumberOfEventInfo() == 0);
    }

    /**
     * Adds to the actual costs variable
     * called by Lineup when a new band is added to the event
     *
     * @param amount
     */
    public void addToTheActualCosts(double amount) {
        actualCosts += amount;
    }

    public int getNumberOfDayTickets() {
        return ticketManager.getNumberOfDayTickets();
    }

    public int getNumberOfCampingTickets() {
        return ticketManager.getNumberOfCampingTickets();
    }

    public int getNumberOfVipTickets() {
        return ticketManager.getNumberOfVipTickets();
    }

    public int totalNumberOfTickets() {
        return ticketManager.totalNumberOfTickets();
    }

    public int getNumberOfSoldDayTickets() {
        return ticketManager.getNumberOfSoldDayTickets();
    }

    public int getNumberOfSoldCampingTickets() {
        return ticketManager.getNumberOfSoldCampingTickets();
    }

    public int getNumberOfSoldVipTickets() {
        return ticketManager.getNumberOfSoldVipTickets();
    }

    public int totalNumberOfSoldTickets() {
        return ticketManager.totalNumberOfSoldTickets();
    }

    public double totalNumberOfSoldTicketsInPercent() {
        return ticketManager.totalNumberOfSoldTicketsInPercent();
    }

    public int getNumberOfDayTicketsLeft() {
        return ticketManager.getNumberOfDayTicketsLeft();
    }

    public int getNumberOfCampingTicketsLeft() {
        return ticketManager.getNumberOfCampingTicketsLeft();
    }

    public int getNumberOfVipTicketsLeft() {
        return ticketManager.getNumberOfVipTicketsLeft();
    }

    public int totalNumberOfTicketsLeft() {
        return ticketManager.totalNumberOfTicketsLeft();
    }

    public Ticket getTicket(Type type) {
        return this.ticketManager.getNewTicket(type);
    }

    public void setTicketStdPrice(double stdPrice, Type type) {
        this.ticketManager.setTicketStdPrice(stdPrice, type);
    }

    public LineUp getLineUp() {
        return lineUp;
    }

    public void setTicketDescription(String description, Type type) {
        this.ticketManager.setTicketDescription(description, type);
    }

    public void sellTickets(Client client) throws TicketNotAvailableException {
        ticketManager.sellTickets(client);
    }

    public double getIncomeTicketSales() {
        return ticketManager.getIncomeTicketSales();
    }

    public int getActualPriceLevelIndex() {
        return ticketManager.getActualPriceLevelIndex();
    }

    /**
     * @param index
     * @return the percentage of the selected priceLevel that must be exceeded to activate the next price level
     */
    public double getPercentageForPriceLevel(int index) throws PriceLevelNotAvailableException {
        return ticketManager.getPercentageForPriceLevel(index);
    }

    /**
     * @param isPriceLevelChangeAutomatic true for automatic, false for manually price level change
     */
    public void setAutomaticPriceLevelChange(boolean isPriceLevelChangeAutomatic) {
        ticketManager.setAutomaticPriceLevelChange(isPriceLevelChangeAutomatic);
    }

    /**
     * shows whether the price level changes automatically
     *
     * @return
     */
    public boolean getAutomaticPriceLevelChange() {
        return ticketManager.getAutomaticPriceLevelChange();
    }

    public boolean setPriceLevel(int index) throws PriceLevelNotAvailableException {
        return ticketManager.setPriceLevel(index);
    }

    public PriceLevel getTheActualPricelevel(){
        return ticketManager.getTheActualPriceLevel();
    }

    public List<PriceLevel> getPriceLevelsForEvent() {
        return ticketManager.getPriceLevels();
    }

    public String getName() {
        return name;
    }


    public BigDecimal getBudget() {
        return budget;
    }

    public LocalTime getStartTime() {
        return this.lineUp.getStartTime();
    }

    public LocalTime getEndTime() {
        return lineUp.getEndTime();
    }

    public Address getAddress() {
        return address;
    }

    public List<Band> getBands() {
        return lineUp.getBands();
    }

    public long getBreakBetweenTwoBandsInMinutes() {
        return lineUp.getBreakBetweenTwoBandsInMinutes();
    }

    public LocalDate getStartDate() {
        return this.lineUp.getStartDate();
    }

    public LocalDate getEndDate() {
        return this.lineUp.getEndDate();
    }

    public Map<LocalDate, Program> getPrograms() {
        return this.lineUp.getDayPrograms();
    }

    public List<Stage> getStages() {
        return this.lineUp.getStages();
    }


    public TicketManager getTicketManager(){
        return ticketManager;
    }

    public Stage getFirstStage() {
        return this.lineUp.getFirstStage();
    }
}

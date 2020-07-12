package de.bord.festival.models;

import de.bord.festival.exception.PriceLevelNotAvailableException;
import de.bord.festival.exception.TicketNotAvailableException;
import de.bord.festival.ticket.*;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.*;

/**
 * The class is created together with the event
 * <p>
 * It uses the following types of tickets: DAY, CAMPING, VIP
 * <p>
 * The class gives information about:
 * -total number of tickets
 * -current number of available tickets,
 * -all price-levels for the Event
 * -the total number of price-levels
 * -the actual price-level (index)
 * * Integer from 0 to n
 * * example: 3 pricelevels with index 0,1,2
 * -income from tickets sold
 * -the change of the price level (automatic / manual)
 * example: boolean automaticPriceLevelChange = true (for automatic change)
 * boolean automaticPriceLevelChange = false (for manuel change with setPriceLevel(int index) )
 * <p>
 * Every price level has its own ticket prices.
 * The individual price levels are sorted in ascending order
 * when the class is created according to the percentage of the price level
 * (tickets sold as a percentage).
 * The user is responsible for a sensible distribution of ticket prices
 * when creating the event (price level creation).
 * <p>
 * The attributes DayTicket dayTicket, CampingTicket campingTicket, VIPTicket vipTicket
 * give information about the actual ticket prices and ticket description.
 * The ticket attributes change in sync with the price level.
 */
@Entity
public class TicketManager extends AbstractModel implements ITicketManager {

    @OneToMany(cascade = CascadeType.ALL)
    private List<PriceLevel> priceLevels;

    public TicketManager() {
    }

    @Transient
    private DayTicket dayTicket;
    @Transient
    private CampingTicket campingTicket;
    @Transient
    private VIPTicket vipTicket;

    private int actualPriceLevel = 0;
    private int numberOfDayTicketsLeft;
    private int numberOfCampingTicketsLeft;
    private int numberOfVipTicketsLeft;

    private int numberOfDayTickets;
    private int numberOfCampingTickets;
    private int numberOfVipTickets;

    private double incomeTicketSales = 0.0;
    private boolean automaticPriceLevelChange = true;

    public TicketManager(List<PriceLevel> priceLevels,
                         int numberOfDayTickets,
                         int numberOfCampingTickets, int numberOfVipTickets,
                         DayTicket dayTicket, CampingTicket campingTicket, VIPTicket vipTicket) {

        this.dayTicket = dayTicket;
        this.campingTicket = campingTicket;
        this.vipTicket = vipTicket;
        this.priceLevels = priceLevels;
        Collections.sort(priceLevels);
        this.numberOfDayTicketsLeft = numberOfDayTickets;
        this.numberOfCampingTicketsLeft = numberOfCampingTickets;
        this.numberOfVipTicketsLeft = numberOfVipTickets;
        this.numberOfDayTickets = numberOfDayTickets;
        this.numberOfCampingTickets = numberOfCampingTickets;
        this.numberOfVipTickets = numberOfVipTickets;
        setTicketPrices();
    }

    /**
     * @param type
     * @return the ticket of the corresponding type of the actual price level
     */
    public Ticket getTicket(Ticket.TicketType type) {
        if (type == Ticket.TicketType.DAY) {
            return dayTicket;
        } else if (type == Ticket.TicketType.CAMPING) {
            return campingTicket;
        } else if (type == Ticket.TicketType.VIP) {
            return vipTicket;
        } else {
            return null;
        }
    }


    /**
     * Checks, if enough tickets of the given TicketType are available for purchasing
     *
     * @param type
     * @return boolean whether there are enough tickets available
     */
    public boolean isAvailable(Ticket.TicketType type, int numberOfCartTickets) {
        if (type == Ticket.TicketType.DAY && this.numberOfDayTicketsLeft - numberOfCartTickets >= 0) {
            return true;
        } else if (type == Ticket.TicketType.CAMPING && this.numberOfCampingTicketsLeft - numberOfCartTickets >= 0) {
            return true;
        } else if (type == Ticket.TicketType.VIP && this.numberOfVipTicketsLeft - numberOfCartTickets >= 0) {
            return true;
        }
        return false;
    }

    /**
     * changes the description for the ticket of the corresponding type from the actual pricelevel
     *
     * @param description
     * @param type
     */
    public void setTicketDescription(String description, Ticket.TicketType type) {
        if (type == Ticket.TicketType.DAY) {
            this.dayTicket.setDescription(description);
        } else if (type == Ticket.TicketType.CAMPING) {
            this.campingTicket.setDescription(description);
        } else if (type == Ticket.TicketType.VIP) {
            this.vipTicket.setDescription(description);
        }
    }

    /**
     * changes the standard price for the ticket of the corresponding type from the actual price level
     *
     * @param stdPrice
     * @param type
     */
    public void setTicketStdPrice(double stdPrice, Ticket.TicketType type) {
        if (type == Ticket.TicketType.DAY) {
            this.dayTicket.setStdPrice(stdPrice);
        } else if (type == Ticket.TicketType.CAMPING) {
            this.campingTicket.setStdPrice(stdPrice);
        } else if (type == Ticket.TicketType.VIP) {
            this.vipTicket.setStdPrice(stdPrice);
        }
    }

    /**
     * Change The ticket attributes in sync with the price level.
     */
    private void setTicketPrices() {
        dayTicket.setStdPrice(priceLevels.get(actualPriceLevel).getDayTicketPrice());
        campingTicket.setStdPrice(priceLevels.get(actualPriceLevel).getCampingTicketPrice());
        vipTicket.setStdPrice(priceLevels.get(actualPriceLevel).getVipTicketPrice());
    }

    /**
     * will be queried after every ticket sale
     * Once a fixed percentage has been exceeded, the next price level starts.
     */
    private void updatePriceLevel() {

        if (isPercentageOfSoldTicketsExceededAndIsTheNextPriceLevelExisting()) {
            this.actualPriceLevel++;
            setTicketPrices();
        }

    }

    /**
     * helper function for if statement
     *
     * @return returns whether the expected percentage has been reached
     * @see #updatePriceLevel
     */
    private boolean isPercentageOfSoldTicketsExceededAndIsTheNextPriceLevelExisting() {

        if (totalNumberOfSoldTicketsInPercent() > priceLevels.get(this.actualPriceLevel).getPercentageForPriceLevel()
                && this.priceLevels.size() > this.actualPriceLevel + 1) {

            return true;

        }
        return false;
    }

    public int getNumberOfDayTickets() {
        return numberOfDayTickets;
    }

    public int getNumberOfCampingTickets() {
        return numberOfCampingTickets;
    }

    public int getNumberOfVipTickets() {
        return numberOfVipTickets;
    }

    public int totalNumberOfTickets() {
        return numberOfDayTickets + numberOfCampingTickets + numberOfVipTickets;
    }  //variable???

    public int getNumberOfDayTicketsLeft() {
        return numberOfDayTicketsLeft;
    }

    public int getNumberOfCampingTicketsLeft() {
        return numberOfCampingTicketsLeft;
    }

    public int getNumberOfVipTicketsLeft() {
        return numberOfVipTicketsLeft;
    }

    public int totalNumberOfTicketsLeft() {
        return numberOfDayTicketsLeft + numberOfCampingTicketsLeft + numberOfVipTicketsLeft;
    }

    public int getNumberOfSoldDayTickets() {
        return numberOfDayTickets - numberOfDayTicketsLeft;
    }

    public int getNumberOfSoldCampingTickets() {
        return numberOfCampingTickets - numberOfCampingTicketsLeft;
    }

    public int getNumberOfSoldVipTickets() {
        return numberOfVipTickets - numberOfVipTicketsLeft;
    }

    public int totalNumberOfSoldTickets() {
        return totalNumberOfTickets() - totalNumberOfTicketsLeft();
    }

    public double totalNumberOfSoldTicketsInPercent() {
        double totalNumberOfTicketsLeft = (double) totalNumberOfTicketsLeft();   // /totalNumberOfTickets()*100;
        double totalNumberOfTickets = (double) totalNumberOfTickets();
        return 100 - ((totalNumberOfTicketsLeft / totalNumberOfTickets) * 100);
    }

    private void updateIncomeTicketSales(double ticketPrice) {
        this.incomeTicketSales += ticketPrice;
    }

    public double getIncomeTicketSales() {
        return incomeTicketSales;
    }

    public int getActualPriceLevelIndex() {   //Exception
        return actualPriceLevel;
    }

    public double getPercentageForPriceLevel(int index) throws PriceLevelNotAvailableException {
        if (PriceLevelIndexInOnlyValueArea(index)) {
            return priceLevels.get(index).getPercentageForPriceLevel();
        } else {
            throw new PriceLevelNotAvailableException("The index is invalid");
        }
    }


    /**
     * determines whether the price level changes automatically.
     * If this is not the case, it can only be changed manually with
     *
     * @param isPriceLevelChangeAutomatic true for automatic, false for manually price level change
     * @see #setPriceLevel(int).
     */
    public void setAutomaticPriceLevelChange(boolean isPriceLevelChangeAutomatic) {

        if (isPriceLevelChangeAutomatic) {
            actualPriceLevel = 0;
            int i = 0;
            while (i < priceLevels.size()) {
                updatePriceLevel();
                i++;
            }
        }
        this.automaticPriceLevelChange = isPriceLevelChangeAutomatic;

    }

    public boolean getAutomaticPriceLevelChange() {
        return automaticPriceLevelChange;
    }

    /**
     * @param index
     * @return returns whether the change was successful
     */
    public boolean setPriceLevel(int index) throws PriceLevelNotAvailableException {
        if (isPriceLevelUpdateManualAndThePriceLevelIndexInOnlyValueArea(index)) {
            actualPriceLevel = index;
            setTicketPrices();
            return true;
        } else {
            return false;
        }
    }

    private boolean isPriceLevelUpdateManualAndThePriceLevelIndexInOnlyValueArea(int index) throws PriceLevelNotAvailableException {
        if (!PriceLevelIndexInOnlyValueArea(index)) {
            throw new PriceLevelNotAvailableException("The index is invalid");
        } else if (!getAutomaticPriceLevelChange()) {

            return true;
        } else {
            return false;
        }
    }

    private boolean PriceLevelIndexInOnlyValueArea(int index) {
        if (index >= 0 && index < priceLevels.size()) {
            return true;
        } else {
            return false;
        }
    }

    public List<PriceLevel> getPriceLevels() {
        return priceLevels;
    }

    public PriceLevel getTheActualPriceLevel() {
        return priceLevels.get(actualPriceLevel);
    }

    public void setPriceLevels(List<PriceLevel> priceLevels) {
        this.priceLevels = priceLevels;
    }

    /**
     * Tickets inside the customer's shopping cart are removed
     * when tickets are sold and added to his inventory.
     * The attribute ...ticketsLeft is decreased by the number of tickets sold.
     * The income in TicketManager and the expenditure in client
     * are increased by the total price of the tickets sold.
     * At the end it is checked whether the price level should change and is if necessary adjusted.
     *
     * @param client
     * @return returns whether the tickets from the customer's basket were available
     */
    public void sellTickets(Client client) throws TicketNotAvailableException {

        int numberOfDayTicketsSold = 0;
        int numberOfCampingTicketsSold = 0;
        int numberOfVipTicketsSold = 0;
        double ticketIncome = 0.0;

        int index = 0;
        while (index < client.getCartSize()) {
            Ticket.TicketType ticketType = client.getCartItem(index).getTicketType();
            if (ticketType == Ticket.TicketType.DAY) {
                if (isAvailable(ticketType, numberOfDayTicketsSold)) {
                    numberOfDayTicketsSold++;
                    ticketIncome += client.getCartItem(index).getStdPrice();
                } else {
                    throw new TicketNotAvailableException("Not enough day-tickets available");
                }
            } else if (ticketType == Ticket.TicketType.CAMPING) {
                if (isAvailable(ticketType, numberOfCampingTicketsSold)) {
                    numberOfCampingTicketsSold++;
                    ticketIncome += client.getCartItem(index).getStdPrice();
                } else {
                    throw new TicketNotAvailableException("Not enough camping-tickets available");
                }
            } else if (ticketType == Ticket.TicketType.VIP) {
                if (isAvailable(ticketType, numberOfVipTicketsSold)) {
                    numberOfVipTicketsSold++;
                    ticketIncome += client.getCartItem(index).getStdPrice();
                } else {
                    throw new TicketNotAvailableException("Not enough VIP-tickets available");
                }
            }
            index++;
        }
        this.numberOfDayTicketsLeft -= numberOfDayTicketsSold;
        this.numberOfCampingTicketsLeft -= numberOfCampingTicketsSold;
        this.numberOfVipTicketsLeft -= numberOfVipTicketsSold;

        client.addCartToInventory();
        client.clearCart();
        updateIncomeTicketSales(ticketIncome);
        client.setExpenditure(ticketIncome);

        if (automaticPriceLevelChange) {
            updatePriceLevel();
        }
    }
}


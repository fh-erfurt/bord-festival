package de.bord.festival.ticket;

import de.bord.festival.client.Client;
import de.bord.festival.exception.ClientNameException;
import de.bord.festival.exception.PriceLevelNotAvailableException;
import de.bord.festival.exception.TicketManagerException;
import de.bord.festival.exception.TicketNotAvailableException;

import java.util.*;

/**
 *  The class is created together with the event
 *
 *  It uses the following types of tickets: DAY, CAMPING, VIP
 *
 *  The class gives information about:
 *  -total number of tickets
 *  -current number of available tickets,
 *  -all price-levels for the Event
 *  -the total number of price-levels
 *  -the actual price-level (index)
 *     * Integer from 0 to n
 *     * example: 3 pricelevels with index 0,1,2
 *  -income from tickets sold
 *  -the change of the price level (automatic / manual)
 *   example: boolean automaticPriceLevelChange = true (for automatic change)
 *            boolean automaticPriceLevelChange = false (for manuel change with setPriceLevel(int index) )
 *
 *  Every price level has its own ticket prices.
 *  The individual price levels are sorted in ascending order
 *  when the class is created according to the percentage of the price level
 *  (tickets sold as a percentage).
 *  The user is responsible for a sensible distribution of ticket prices
 *  when creating the event (price level creation).
 *
 *  The attributes DayTicket dayTicket, CampingTicket campingTicket, VIPTicket vipTicket
 *  give information about the actual ticket prices and ticket description.
 *  The ticket attributes change in sync with the price level.
 *
 *
 *
 */

public class TicketManager {

    private ArrayList<PriceLevel> priceLevels;

    private DayTicket dayTicket;
    private CampingTicket campingTicket;
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

    public TicketManager(ArrayList<PriceLevel> priceLevels,
                         int numberOfDayTickets,
                         int numberOfCampingTickets,int numberOfVipTickets,
                         DayTicket dayTicket, CampingTicket campingTicket, VIPTicket vipTicket){

        this.dayTicket = dayTicket;
        this.campingTicket = campingTicket;
        this.vipTicket = vipTicket;
        this.priceLevels = priceLevels;
        Collections.sort(priceLevels);   //exception
        this.numberOfDayTicketsLeft = numberOfDayTickets;
        this.numberOfCampingTicketsLeft = numberOfCampingTickets;
        this.numberOfVipTicketsLeft = numberOfVipTickets;
        this.numberOfDayTickets = numberOfDayTickets;
        this.numberOfCampingTickets = numberOfCampingTickets;
        this.numberOfVipTickets = numberOfVipTickets;
        setTicketPrices();
    }

    /**
     *
     * @param type
     * @return the ticket of the appropriate type of the actual price level
     */
    public Ticket getTicket(Ticket.TicketType type){
        if (type == Ticket.TicketType.DAY) {
            return dayTicket;
        } else if (type == Ticket.TicketType.CAMPING) {
            return campingTicket;
        } else if(type == Ticket.TicketType.VIP) {
            return vipTicket;
        }
        else{
            return null;
        }
    }


    public boolean isAvailable(Ticket.TicketType type) {
        if (type == Ticket.TicketType.DAY && this.numberOfDayTicketsLeft > 0) {
            return true;
        } else if (type == Ticket.TicketType.CAMPING && this.numberOfCampingTicketsLeft > 0) {
            return true;
        } else if (type == Ticket.TicketType.VIP && this.numberOfVipTicketsLeft > 0) {
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * changes the description for the ticket of the appropriate type from the actual pricelevel
     * @param description
     * @param type
     */
    public void setTicketDescription(String description, Ticket.TicketType type){
        if (type == Ticket.TicketType.DAY) {
            this.dayTicket.setDescription(description);
        } else if (type == Ticket.TicketType.CAMPING) {
            this.campingTicket.setDescription(description);
        } else if (type == Ticket.TicketType.VIP) {
            this.vipTicket.setDescription(description);
        }
    }

    /**
     *
     *  changes the standard price for the ticket of the appropriate type from the actual price level
     *  @param stdPrice
     *  @param type
     */
    public void setTicketStdPrice(double stdPrice, Ticket.TicketType type){
        if (type == Ticket.TicketType.DAY) {
            this.dayTicket.setStdPrice(stdPrice);
        } else if (type == Ticket.TicketType.CAMPING) {
            this.campingTicket.setStdPrice(stdPrice);
        } else if (type == Ticket.TicketType.VIP) {
            this.vipTicket.setStdPrice(stdPrice);
        }
    }

    /**
     *  Change The ticket attributes in sync with the price level.
     */
    private void setTicketPrices(){
        dayTicket.setStdPrice(priceLevels.get(actualPriceLevel).getDayTicketPrice());
        campingTicket.setStdPrice(priceLevels.get(actualPriceLevel).getCampingTicketPrice());
        vipTicket.setStdPrice(priceLevels.get(actualPriceLevel).getVipTicketPrice());
    }

    /**
     * will be queried after every ticket sale
     * Once a fixed percentage has been exceeded, the next price level starts.
     */
    private void updatePriceLevel(){

        if(isPercentageOfSoldTicketsExceededAndIsTheNextPriceLevelExisting()){
            this.actualPriceLevel++;
            setTicketPrices();
        }

    }

    /**
     * helper function for if statement
     * @return returns whether the expected percentage has been reached
     */
   private boolean isPercentageOfSoldTicketsExceededAndIsTheNextPriceLevelExisting(){

        if(totalNumberOfSoldTicketsInPercent() > priceLevels.get(this.actualPriceLevel).getPercentageForPriceLevel()
                && this.priceLevels.size() > this.actualPriceLevel+1){

            return true;

        }
        return false;
    }


    /**
     * total number of Tickets
     *
     */
    public int getNumberOfDayTickets() { return numberOfDayTickets; }
    public int getNumberOfCampingTickets() { return numberOfCampingTickets; }
    public int getNumberOfVipTickets() { return numberOfVipTickets; }
    public int totalNumberOfTickets(){return numberOfDayTickets + numberOfCampingTickets + numberOfVipTickets;}  //variable???



    /**
     * get tickets left
     *
     */
    public int getNumberOfDayTicketsLeft() { return numberOfDayTicketsLeft; }
    public int getNumberOfCampingTicketsLeft() { return numberOfCampingTicketsLeft; }
    public int getNumberOfVipTicketsLeft() { return numberOfVipTicketsLeft; }
    public int totalNumberOfTicketsLeft(){return numberOfDayTicketsLeft + numberOfCampingTicketsLeft + numberOfVipTicketsLeft;}


    /**
     * sold tickets
     *
     */
    public int getNumberOfSoldDayTickets(){ return numberOfDayTickets - numberOfDayTicketsLeft;}
    public int getNumberOfSoldCampingTickets(){ return numberOfCampingTickets - numberOfCampingTicketsLeft;}
    public int getNumberOfSoldVipTickets(){ return numberOfVipTickets - numberOfVipTicketsLeft;}
    public int totalNumberOfSoldTickets(){ return totalNumberOfTickets()-totalNumberOfTicketsLeft();}
    public double totalNumberOfSoldTicketsInPercent(){
        double totalNumberOfTicketsLeft = (double)totalNumberOfTicketsLeft();   // /totalNumberOfTickets()*100;
        double totalNumberOfTickets = (double)totalNumberOfTickets();
        return 100-((totalNumberOfTicketsLeft/totalNumberOfTickets)*100);
    }


    private void updateIncomeTicketSales(double ticketPrice){
        this.incomeTicketSales += ticketPrice;
    }

    public double getIncomeTicketSales(){
        return incomeTicketSales;
    }


    public int getActualPriceLevelIndex(){   //Exception
        return actualPriceLevel;
    }

    public double getPercentageForPriceLevel(int index) throws PriceLevelNotAvailableException {
        if(PriceLevelIndexInOnlyValueArea(index)){
            return priceLevels.get(index).getPercentageForPriceLevel();
        }
        else{
            throw new PriceLevelNotAvailableException("The index is invalid"); ////ist das richtig?
        }
    }


    /**
     * determines whether the price level changes automatically.
     * If this is not the case, it can only be changed manually with
     * @see #setPriceLevel(int).
     * @param isPriceLevelChangeAutomatic true for automatic, false for manually price level change
     */
    public void setAutomaticPriceLevelChange(boolean isPriceLevelChangeAutomatic) throws TicketManagerException {

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

    public boolean getAutomaticPriceLevelChange(){
        return automaticPriceLevelChange;
    }

    /**
     *
     * @param index
     * @return returns whether the change was successful
     */
        public boolean setPriceLevel(int index) throws PriceLevelNotAvailableException {
        if(isPriceLevelUpdateManualAndThePriceLevelIndexInOnlyValueArea(index)){
            actualPriceLevel = index;
            setTicketPrices();
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isPriceLevelUpdateManualAndThePriceLevelIndexInOnlyValueArea(int index) throws PriceLevelNotAvailableException {
        if(!PriceLevelIndexInOnlyValueArea(index)){
            throw new PriceLevelNotAvailableException("The index is invalid");
        }
       else if(!getAutomaticPriceLevelChange()){

            return true;
        }
        else {
            return false;
        }
    }

    private boolean PriceLevelIndexInOnlyValueArea(int index){
        if(index >= 0 && index < priceLevels.size()){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     *
     * If the tickets are available from the customer's shopping cart, the tickets are removed from the customer's shopping cart
     * when tickets are sold and added to his inventory.
     * The attributes ticketsLeft are adjusted by the number of tickets sold.
     * The income from tickets sold to the ticket manager and the expenditure from tickets to the client
     * are adjusted by the total price of the tickets sold.
     * At the end it is checked whether the price level should change and if necessary adjusted.
     *
     * @param client
     * @return returns whether the tickets from the customer's basket were available
     * @throws TicketManagerException
     */
    public boolean sellTickets(Client client) throws TicketManagerException, TicketNotAvailableException {

        int numberOfDayTicketsSold = 0;
        int numberOfCampingTicketsSold = 0;
        int numberOfVipTicketsSold = 0;
        double ticketIncome = 0.0;

        int index = 0;
        while (index < client.getCartSize()) {
            if (ticketTypeIsDayAndTicketIsAvailable(client, index)) {
                numberOfDayTicketsSold++;
                ticketIncome += client.getCartItem(index).getStdPrice();
            } else if (ticketTypeIsCampingAndTicketIsAvailable(client, index)) {
                numberOfCampingTicketsSold++;
                ticketIncome += client.getCartItem(index).getStdPrice();
            } else if (ticketTypeIsVipAndTicketIsAvailable(client, index)) {
                numberOfVipTicketsSold++;
                ticketIncome += client.getCartItem(index).getStdPrice();
            } else {
                throw new TicketNotAvailableException("There are Tickets not Available in order");
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

        if(automaticPriceLevelChange){
            updatePriceLevel();
        }
        return true;
    }


    /**
     * helper functions for if statement
     * @param client
     * @param index
     * @return true if the ticket type is correct and the corresponding ticket type is available
     */
    private boolean ticketTypeIsDayAndTicketIsAvailable(Client client, int index){
        if(client.getCartItem(index).getTicketType() == Ticket.TicketType.DAY && isAvailable(Ticket.TicketType.DAY)){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean ticketTypeIsCampingAndTicketIsAvailable(Client client, int index){
        if(client.getCartItem(index).getTicketType() == Ticket.TicketType.CAMPING && isAvailable(Ticket.TicketType.CAMPING)){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean ticketTypeIsVipAndTicketIsAvailable(Client client, int index){
        if(client.getCartItem(index).getTicketType() == Ticket.TicketType.VIP && isAvailable(Ticket.TicketType.VIP)){
            return true;
        }
        else{
            return false;
        }
    }




}


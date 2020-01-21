package de.bord.festival.ticket;

import de.bord.festival.client.Client;
import de.bord.festival.exception.TicketManagerException;

import java.time.LocalDate;
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
    private int nDayticketsLeft;
    private int nCampingticketsLeft;
    private int nVipticketsLeft;

    private int nDaytickets;
    private int nCampingtickets;
    private int nViptickets;

    private double incomeTicketSales;
    private boolean automaticPriceLevelChange;

    public TicketManager(ArrayList<PriceLevel> priceLevels,
                         int nDaytickets,
                         int nCampingtickets,int nViptickets,
                         DayTicket dayTicket, CampingTicket campingTicket, VIPTicket vipTicket){

        this.dayTicket = dayTicket;
        this.campingTicket = campingTicket;
        this.vipTicket = vipTicket;
        this.priceLevels = priceLevels;
        Collections.sort(priceLevels);   //exception
        this.incomeTicketSales = 0.0;
        this.automaticPriceLevelChange = true;
        this.nDayticketsLeft = nDaytickets;
        this.nCampingticketsLeft = nCampingtickets;
        this.nVipticketsLeft = nViptickets;
        this.nDaytickets = nDaytickets;
        this.nCampingtickets = nCampingtickets;
        this.nViptickets = nViptickets;
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
        if (type == Ticket.TicketType.DAY && this.nDayticketsLeft > 0) {
            return true;
        } else if (type == Ticket.TicketType.CAMPING && this.nCampingticketsLeft > 0) {
            return true;
        } else if (type == Ticket.TicketType.VIP && this.nVipticketsLeft > 0) {
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
    public void setTicketPrices(){
        dayTicket.setStdPrice(priceLevels.get(actualPriceLevel).getDayTicketPrice());
        campingTicket.setStdPrice(priceLevels.get(actualPriceLevel).getCampingTicketPrice());
        vipTicket.setStdPrice(priceLevels.get(actualPriceLevel).getVipTicketPrice());
    }

    /**
     * will be queried after every ticket sale
     * Once a fixed percentage has been exceeded, the next price level starts.
     */
    private void updatePriceLevel() throws TicketManagerException {


        if(isPercentageOfSoldTicketsExceeded()&& this.priceLevels.size() > this.actualPriceLevel+1){
            this.actualPriceLevel++;
            setTicketPrices();
        }
      /*  else if(){
        }
        else{
            throw new TicketManagerException("no valid price level available");
            }
      */  }

    /**
     *
     * @return returns whether the expected percentage has been reached
     */
    private boolean isPercentageOfSoldTicketsExceeded(){

        if(totalNumberOfSoldTicketsInPercent() > priceLevels.get(this.actualPriceLevel).getPercentageForPricelevel()){
            /*getPriceLevel(this.actualPriceLevel).getPercentageForPricelevel()*/
            return true;
        }
        return false;
    }


    /**
     * total number of Tickets
     *
     */
    public int getnDaytickets() { return nDaytickets; }
    public int getnCampingtickets() { return nCampingticketsLeft; }
    public int getnViptickets() { return nViptickets; }
    public int totalNumberOfTickets(){return nDaytickets+nCampingtickets+nViptickets;}  //variable???



    /**
     * get tickets left
     *
     */
    public int getnDayticketsLeft() { return nDayticketsLeft; }
    public int getnCampingticketsLeft() { return nCampingticketsLeft; }
    public int getnVipticketsLeft() { return nVipticketsLeft; }
    public int totalNumberOfTicketsLeft(){return nDayticketsLeft+nCampingticketsLeft+nVipticketsLeft;}


    /**
     * sold tickets
     *
     */
    public int getnSoldDaytickets(){ return nDaytickets - nDayticketsLeft;}
    public int getnSoldCampingtickets(){ return nCampingtickets - nCampingticketsLeft;}
    public int getnSoldViptickets(){ return nViptickets - nVipticketsLeft;}
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

    public PriceLevel getPriceLevel(int index){return priceLevels.get(index);}


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
    public boolean setPriceLevel(int index){    /////////exception
        if(!getAutomaticPriceLevelChange() && index >= 0 && index < priceLevels.size()){
            actualPriceLevel = index;
            setTicketPrices();
            return true;
        }
        else{
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
    public boolean sellTickets(Client client) throws TicketManagerException {

        int nDayTicketsSold = 0;
        int nCampingTicketsSold = 0;
        int nVIPTicketsSold = 0;
        double ticketIncome = 0.0;

        int i = 0;
        while (i < client.getCartSize()) {
            if (client.getCartItem(i).getTicketType() == Ticket.TicketType.DAY && getnDayticketsLeft() >= 1) {
                nDayTicketsSold++;
                ticketIncome += client.getCartItem(i).getStdPrice();
            } else if (client.getCartItem(i).getTicketType() == Ticket.TicketType.CAMPING && getnCampingticketsLeft() >= 1) {
                nCampingTicketsSold++;
                ticketIncome += client.getCartItem(i).getStdPrice();
            } else if (client.getCartItem(i).getTicketType() == Ticket.TicketType.VIP && getnVipticketsLeft() >= 1) {
                nVIPTicketsSold++;
                ticketIncome += client.getCartItem(i).getStdPrice();
            } else {
                return false;
            }
            i++;
        }
        this.nDayticketsLeft -= nDayTicketsSold;
        this.nCampingticketsLeft -= nCampingTicketsSold;
        this.nVipticketsLeft -= nVIPTicketsSold;

        client.addCartToInventory();
        client.clearCart();
        updateIncomeTicketSales(ticketIncome);
        client.setExpenditure(ticketIncome);

        if(automaticPriceLevelChange){
            updatePriceLevel();
        }
        return true;
    }
}
package de.bord.festival.ticket;

import de.bord.festival.client.Client;
import de.bord.festival.exception.TicketManagerException;

import java.time.LocalDate;
import java.util.*;

/**
 *  The class gives information about total number of tickets, current number of tickets,
 *  the total number of price-levels and the actual price-level. It consists all price-levels for the Event.
 *
 */

public class TicketManager {
    private ArrayList<PriceLevel> priceLevels;
    private int nPriceLevels;
    // private double actualPriceLevelPercentage;

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
    private boolean automaticPriceLevelChange;    //
    // wenn ich auf automatik wechsel muss das preislevel aktualisiert werden     TODO
    // im manuellen modus kann ich selbst das pricelevel angeben


    public TicketManager(ArrayList<PriceLevel> priceLevels,
                         int nPriceLevels,
                         int nDaytickets,
                         int nCampingtickets,int nViptickets,
                         DayTicket dayTicket, CampingTicket campingTicket, VIPTicket vipTicket){

        this.dayTicket = dayTicket;
        this.campingTicket = campingTicket;
        this.vipTicket = vipTicket;
        this.priceLevels = priceLevels;
        Collections.sort(priceLevels);   //exception
        this.nPriceLevels = nPriceLevels;
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

    public Ticket getTicket(Ticket.TicketType type){
        if (type == Ticket.TicketType.DAY) {
            ticketIdCounter(type);
            return dayTicket;
        } else if (type == Ticket.TicketType.CAMPING) {
            ticketIdCounter(type);
            return campingTicket;
        } else if(type == Ticket.TicketType.VIP) {
            ticketIdCounter(type);
            return vipTicket;
        }
        else{
            return null;
        }
    }

    public void ticketIdCounter(Ticket.TicketType type){
        if (type == Ticket.TicketType.DAY) {
            dayTicket.idCounter();
        } else if (type == Ticket.TicketType.CAMPING) {
            campingTicket.idCounter();
        } else if (type == Ticket.TicketType.VIP) {
            vipTicket.idCounter();
        }
    }

    public void setAvailable(boolean available, Ticket.TicketType type){
        if (type == Ticket.TicketType.DAY) {
            this.dayTicket.setAvailable(available);
        } else if (type == Ticket.TicketType.CAMPING) {
            this.campingTicket.setAvailable(available);
        } else if (type == Ticket.TicketType.VIP) {
            this.vipTicket.setAvailable(available);
        }
    }

    public void setDescription(String description, Ticket.TicketType type){
        if (type == Ticket.TicketType.DAY) {
            this.dayTicket.setDescription(description);
        } else if (type == Ticket.TicketType.CAMPING) {
            this.campingTicket.setDescription(description);
        } else if (type == Ticket.TicketType.VIP) {
            this.vipTicket.setDescription(description);
        }
    }
    public void setStdPrice(double stdPrice, Ticket.TicketType type){
        if (type == Ticket.TicketType.DAY) {
            this.dayTicket.setStdPrice(stdPrice);
        } else if (type == Ticket.TicketType.CAMPING) {
            this.campingTicket.setStdPrice(stdPrice);
        } else if (type == Ticket.TicketType.VIP) {
            this.vipTicket.setStdPrice(stdPrice);
        }
    }



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
    public int totalNumberOfTicketsLeft(){return nDayticketsLeft+nCampingtickets+nVipticketsLeft;}


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
        return 100 - (totalNumberOfTicketsLeft/totalNumberOfTickets*100);
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
     *
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

    /////////////////////client zurückgeben
    public Client sellTickets(Client client /*, LocalDate date*/) throws TicketManagerException {

        int nDayTicketsSold = 0;
        int nCampingTicketsSold = 0;
        int nVIPTicketsSold = 0;
        double ticketIncome = 0.0;

        int i = 0;
        while (i < client.get_cartSize()) {
            if (client.get_cartItem(i).getTicketType() == Ticket.TicketType.DAY && getnDayticketsLeft() >= 1) {
                nDayTicketsSold++;
                /*ticketIncome += priceLevels.get(actualPriceLevel).getDayTicketPrice();*/
                ticketIncome += client.get_cartItem(i).getStdPrice();
            } else if (client.get_cartItem(i).getTicketType() == Ticket.TicketType.CAMPING && getnCampingticketsLeft() >= 1) {
                nCampingTicketsSold++;
                /*ticketIncome += priceLevels.get(actualPriceLevel).getCampingTicketPrice();*/
                ticketIncome += client.get_cartItem(i).getStdPrice();
            } else if (client.get_cartItem(i).getTicketType() == Ticket.TicketType.VIP && getnVipticketsLeft() >= 1) {
                nVIPTicketsSold++;
                /*ticketIncome += priceLevels.get(actualPriceLevel).getVipTicketPrice();*/
                ticketIncome += client.get_cartItem(i).getStdPrice();
            } else {
                return null;
            }
            i++;
        }
        this.nDayticketsLeft -= nDayTicketsSold;
        this.nCampingticketsLeft -= nCampingTicketsSold;
        this.nVipticketsLeft -= nVIPTicketsSold;

        client.add_cartToTickets();
        client.clear_cart();
        updateIncomeTicketSales(ticketIncome);
        client.setExpenditure(ticketIncome);

        if(automaticPriceLevelChange){
            updatePriceLevel();
        }
        return client;
    }
}
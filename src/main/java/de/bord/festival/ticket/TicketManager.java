package de.bord.festival.ticket;

import de.bord.festival.eventManagement.Event;

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
    private int actualPriceLevel;
    private int nDayticketsLeft;
    private int nCampingticketsLeft;
    private int nVipticketsLeft;

    private int nDaytickets;
    private int nCampingtickets;
    private int nViptickets;

    public TicketManager(ArrayList<PriceLevel> priceLevels,
                         int nPriceLevels,
                         int nDaytickets,
                         int nCampingtickets,int nViptickets ){

        this.priceLevels = priceLevels;
        Collections.sort(priceLevels);   //exception
        this.nPriceLevels = nPriceLevels;
        this.actualPriceLevel = 0;
        //this.actualPriceLevelPercentage = priceLevels.get(0).getPercentageOfSoldTickets();
        this.nDayticketsLeft = nDaytickets;
        this.nCampingticketsLeft = nCampingtickets;
        this.nVipticketsLeft = nViptickets;
        this.nDaytickets = nDaytickets;
        this.nCampingtickets = nCampingtickets;
        this.nViptickets = nViptickets;

    }

    /**
     * will be queried after every ticket sale
     * Once a fixed percentage has been exceeded, the next price level starts.
     */
    public void updatePriceLevel(){


        if(isPercentageOfSoldTicketsExceeded()&& this.priceLevels.size() < this.actualPriceLevel+1){
            this.actualPriceLevel++;
            }
        }

        public boolean isPercentageOfSoldTicketsExceeded(){

            if(totalNumberOfSoldTicketsInPercent() < getPriceLevel(this.actualPriceLevel).getValidPercentageOfSoldTicketsForPricelevel()){
                return true;
            }
            return false;
        }

    public int getnDaytickets() {
        return nDaytickets;
    }

    public int getnCampingtickets() {
        return nCampingticketsLeft;
    }

    public int getnViptickets() {
        return nViptickets;
    }

    public int getnDayticketsLeft() {
        return nDayticketsLeft;
    }

    public int getnCampingticketsLeft() {
        return nCampingticketsLeft;
    }

    public int getnVipticketsLeft() {
        return nVipticketsLeft;
    }

    public int totalNumberOfTickets(){return nDaytickets+nCampingtickets+nViptickets;}  //variable???

    public int totalNumberOfTicketsLeft(){return nDayticketsLeft+nCampingtickets+nVipticketsLeft;}

    public int totalNumberOfSoldTickets(){
        return totalNumberOfTickets()-totalNumberOfTicketsLeft();}

    public double totalNumberOfSoldTicketsInPercent(){return (totalNumberOfTicketsLeft()/totalNumberOfTickets())*100;}

    public PriceLevel getPriceLevel(int index){
        return priceLevels.get(index);
    }



    public Ticket sellTicket(Ticket.TicketType type){
        switch(type){

            case CAMPING:
                CampingTicket campingTicket = new CampingTicket(type, totalNumberOfSoldTickets()+1, "?????",
                        true, this.priceLevels.get(actualPriceLevel).getCampingTicketPrice(),
                        "?????", null);
                updatePriceLevel();
                this.nCampingticketsLeft--;
                return campingTicket;


        }
        return null;} ///////return the ticket TODO


}

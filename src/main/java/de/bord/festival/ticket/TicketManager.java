package de.bord.festival.ticket;

import de.bord.festival.eventManagement.Event;

import java.util.ArrayList;

/**
 *  The class gives information about total number of tickets, current number of tickets,
 *  the total number of price-levels and the actual price-level. It consists all price-levels for the Event.
 *
 */

public class TicketManager {
    private ArrayList<PriceLevel> priceLevels;
    private int nPriceLevels;
    private int actualPriceLevel;

    private int nDayticketsLeft;
    private int nCampingticketsLeft;
    private int nVipticketsLeft;

    private int nDaytickets;
    private int nCampingtickets;
    private int nViptickets;

    /**
     * will be queried after every ticket sale
     * Once a fixed percentage has been exceeded, the next price level starts.
     */
    public void updatePriceLevel(){
        if(isPercentageOfSoldTicketsExceeded()){
            this.actualPriceLevel++;
            }
        }

        public boolean isPercentageOfSoldTicketsExceeded(){

            if(totalNumberOfSoldTicketsInPercent() < priceLevels.get(actualPriceLevel).getPercentageOfSoldTickets()){
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

    public int totalNumberOfTickets(){return getnDaytickets()+getnCampingtickets()+getnViptickets();}

    public int totalNumberOfTicketsLeft(){return getnDayticketsLeft()+getnCampingtickets()+getnVipticketsLeft();}

    public int totalNumberOfSoldTickets(){
        return totalNumberOfTickets()-totalNumberOfTicketsLeft();}

    public double totalNumberOfSoldTicketsInPercent(){return (totalNumberOfTicketsLeft()/totalNumberOfTickets())*100;}
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

package de.bord.festival.ticket;

import de.bord.festival.client.Client;
import de.bord.festival.exception.TicketManagerException;

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
    private boolean automaticPriceLevelChange = true;    //
    // wenn ich auf automatik wechsel muss das preislevel aktualisiert werden     TODO
    // im manuellen modus kann ich selbst das pricelevel angeben

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
    public void updatePriceLevel() throws TicketManagerException {


        if(isPercentageOfSoldTicketsExceeded()&& this.priceLevels.size() < this.actualPriceLevel+1){
            this.actualPriceLevel++;
            }
      /*  else if(){

        }
        else{
            throw new TicketManagerException("no valid price level available");
            }
      */  }

        public boolean isPercentageOfSoldTicketsExceeded(){

            if(totalNumberOfSoldTicketsInPercent() < getPriceLevel(this.actualPriceLevel).getPercentageForPricelevel()){
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
    public int totalNumberOfSoldTickets(){ return totalNumberOfTickets()-totalNumberOfTicketsLeft();}
    public double totalNumberOfSoldTicketsInPercent(){return (totalNumberOfTicketsLeft()/totalNumberOfTickets())*100;}


    /**
     *
     * @param  index of the Pricelevel
     * @return the Pricelevel
     */
    public PriceLevel getPriceLevel(int index){   //Exception
        return priceLevels.get(index);
    }

    /**
     *
     * @param isPriceLevelChangeAutomatic true for automatic, false for manually price level change
     */
    public void setAutomaticPriceLevelChange(boolean isPriceLevelChangeAutomatic) {
        this.automaticPriceLevelChange = isPriceLevelChangeAutomatic;
    }

    public boolean sellTickets(Client client) throws TicketManagerException {

        List<Ticket> ticketList = client.get_tickets();
        Ticket ticket = ticketList.get(0);
        int nDayTicketsSold = 0;
        int nCampingTicketsSold = 0;
        int nVIPTicketsSold = 0;

        int i = 0;
        while (i < ticketList.size()) {
            if (ticketList.get(i).getTicketType() == Ticket.TicketType.DAY && getnDayticketsLeft() >= 1) {
                nDayTicketsSold++;
            } else if (ticketList.get(i).getTicketType() == Ticket.TicketType.CAMPING && getnCampingticketsLeft() >= 1) {
                nCampingTicketsSold++;
            } else if (ticketList.get(i).getTicketType() == Ticket.TicketType.VIP && getnVipticketsLeft() >= 1) {
                nVIPTicketsSold++;
            } else {
                return false;
            }
        }
        this.nDayticketsLeft -= nDayTicketsSold;
        this.nCampingticketsLeft -= nCampingTicketsSold;
        this.nVipticketsLeft -= nVIPTicketsSold;
        updatePriceLevel();
        return true;   // boolean oder client mit Warenkorb
    }
}

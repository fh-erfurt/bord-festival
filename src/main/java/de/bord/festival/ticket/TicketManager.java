package de.bord.festival.ticket;

import de.bord.festival.eventManagement.Event;

import java.util.ArrayList;

public class TicketManager {
    private ArrayList<PriceLevel> priceLevels;////////
    private int nPriceLevels;/////////////
    private int actualPriceLevel;/////////////

    private int nDayticketsLeft;////////////
    private int nCampingticketsLeft;//////////
    private int nVipticketsLeft;//////////////

    private int nDaytickets;////////////
    private int nCampingtickets;//////////
    private int nViptickets;//////////////

    public void updatePriceLevel(){
        if(totalNumberOfSoldTicketsInPercent() < priceLevels.get(actualPriceLevel).getPercentageOfSoldTickets()){
            this.actualPriceLevel++;
            }
        }
    public int totalNumberOfSoldTickets(){return 0;} //////////TODO
    public double totalNumberOfSoldTicketsInPercent(){return 0.0;} //////////TODO
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

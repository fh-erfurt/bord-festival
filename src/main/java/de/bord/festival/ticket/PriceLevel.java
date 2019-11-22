package de.bord.festival.ticket;



public class PriceLevel {



   private double dayTicketPrice;
   private double CampingTicketPrice;
   private double VipTicketPrice;
   private double percentageOfSoldTickets; /* percentage for pricelevel
                                              the price level is valid until the percentage of sold tickets
                                              is reached*/
    private int priceLevel;

    public PriceLevel(double dayTicketPrice) {
        this.dayTicketPrice = dayTicketPrice;
    }

    public double getCampingTicketPrice() {
        return CampingTicketPrice;
    }

    public double getPercentageOfSoldTickets() {
        return percentageOfSoldTickets;
    }
}

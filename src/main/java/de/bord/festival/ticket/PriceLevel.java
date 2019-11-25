package de.bord.festival.ticket;



public class PriceLevel {

    /**
     *  Contains all ticket prices for the price level and a percentage for the price level.
     * if the percentage is exceeded, the next price level starts.
     */

   private double dayTicketPrice;
   private double CampingTicketPrice;
   private double VipTicketPrice;

    /**
     * the price level is valid until the percentage of sold tickets is reached
     */
    private double percentageOfSoldTickets;

    private int priceLevel;

    public PriceLevel(double dayTicketPrice, double CampingTicketPrice, double VipTicketPrice,
                      double percentageOfSoldTickets, int priceLevel) {

        this.dayTicketPrice = dayTicketPrice;
        this.CampingTicketPrice = CampingTicketPrice;
        this.VipTicketPrice = VipTicketPrice;
        this.percentageOfSoldTickets = percentageOfSoldTickets;
        this.priceLevel = priceLevel;
    }

    public double getCampingTicketPrice() {
        return CampingTicketPrice;
    }

    public double getPercentageOfSoldTickets() {
        return percentageOfSoldTickets;
    }
}

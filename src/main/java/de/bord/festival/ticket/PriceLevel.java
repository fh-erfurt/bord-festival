package de.bord.festival.ticket;




public class PriceLevel implements Comparable<PriceLevel>{

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
    private double validPercentageOfSoldTicketsForPricelevel;

    //private int priceLevel;

    public PriceLevel(double dayTicketPrice, double CampingTicketPrice, double VipTicketPrice,
                      double validPercentageOfSoldTicketsForPricelevel) {

        this.dayTicketPrice = dayTicketPrice;
        this.CampingTicketPrice = CampingTicketPrice;
        this.VipTicketPrice = VipTicketPrice;
        this.validPercentageOfSoldTicketsForPricelevel = validPercentageOfSoldTicketsForPricelevel;
       // this.priceLevel = priceLevel;
    }

    /**
     * for sorting the pricelevel array
     * java.lang.Integer.valueOf because a Object is needed
     * @param
     * @return
     */
    @Override
    public int compareTo(PriceLevel p) {

        return  java.lang.Double.valueOf(this.validPercentageOfSoldTicketsForPricelevel).compareTo(java.lang.Double.valueOf(p.validPercentageOfSoldTicketsForPricelevel));
    }

    public double getCampingTicketPrice() {
        return CampingTicketPrice;
    }

    public double getValidPercentageOfSoldTicketsForPricelevel() {
        return validPercentageOfSoldTicketsForPricelevel;
    }
    //public  int getPriceLevel(){return priceLevel;}

}

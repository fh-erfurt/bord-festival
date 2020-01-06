package de.bord.festival.ticket;

import de.bord.festival.exception.PriceLevelException;
import de.bord.festival.exception.PriceLevelException;

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
    private double PercentageForPricelevel;

    //private int priceLevel;

    public PriceLevel(double dayTicketPrice, double CampingTicketPrice, double VipTicketPrice,
                      double PercentageForPricelevel) throws PriceLevelException {

        if(PercentageForPricelevel > 100 || PercentageForPricelevel < 0){
            throw new PriceLevelException("PercentageForPricelevel not valid");
        }
        this.PercentageForPricelevel = PercentageForPricelevel;
        this.dayTicketPrice = dayTicketPrice;
        this.CampingTicketPrice = CampingTicketPrice;
        this.VipTicketPrice = VipTicketPrice;



       // this.priceLevel = priceLevel;
    }

    /**
     * for sorting the pricelevel array
     * java.lang.Integer.valueOf because a Object is needed
     * @param
     * @return
     */
    @Override
    public int compareTo(PriceLevel priceLevel) {

        return  java.lang.Double.valueOf(this.PercentageForPricelevel).compareTo(java.lang.Double.valueOf(priceLevel.PercentageForPricelevel));
    }


    public double getPercentageForPricelevel() {
        return PercentageForPricelevel;
    }
    //public  int getPriceLevel(){return priceLevel;}

    public double getDayTicketPrice(){return dayTicketPrice;}
    public double getCampingTicketPrice(){return CampingTicketPrice;}
    public double getVipTicketPrice(){return VipTicketPrice;}

}

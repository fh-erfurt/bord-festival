package de.bord.festival.ticket;

import de.bord.festival.exception.PriceLevelException;
import de.bord.festival.exception.PriceLevelException;

import java.util.Collections;

/**
 *  Contains all ticket prices for the price level and a percentage for the price level.
 *  if the percentage is exceeded, the next price level starts.
 */

public class PriceLevel implements Comparable<PriceLevel>{


   private double dayTicketPrice;
   private double CampingTicketPrice;
   private double VipTicketPrice;

    /**
     * the price level is valid until the percentage of sold tickets is exceeded
     */
    private double PercentageForPriceLevel;

    public PriceLevel(double dayTicketPrice, double CampingTicketPrice, double VipTicketPrice,
                      double PercentageForPriceLevel) throws PriceLevelException {

        if(PercentageForPriceLevel > 100 || PercentageForPriceLevel < 0){
            throw new PriceLevelException("PercentageForPricelevel not valid");
        }
        this.PercentageForPriceLevel = PercentageForPriceLevel;
        this.dayTicketPrice = dayTicketPrice;
        this.CampingTicketPrice = CampingTicketPrice;
        this.VipTicketPrice = VipTicketPrice;

    }

    /**
     * -for sorting the pricelevel arrayList in TicketManager constructor
     * -java.lang.double.valueOf because an object is needed for Collections.sort()
     * @param  priceLevel
     * @return returns a double value as an object so that they can be compared and sorted
     * @see TicketManager Collections.sort(priceLevels)
     */
    @Override
    public int compareTo(PriceLevel priceLevel) {

        return  java.lang.Double.valueOf(this.PercentageForPriceLevel).compareTo(java.lang.Double.valueOf(priceLevel.PercentageForPriceLevel));
    }


    public double getPercentageForPriceLevel() {
        return PercentageForPriceLevel;
    }

    public double getDayTicketPrice(){return dayTicketPrice;}
    public double getCampingTicketPrice(){return CampingTicketPrice;}
    public double getVipTicketPrice(){return VipTicketPrice;}

}

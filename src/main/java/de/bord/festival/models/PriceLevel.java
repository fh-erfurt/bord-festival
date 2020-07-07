package de.bord.festival.models;

import de.bord.festival.exception.PriceLevelException;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


/**
 *  Contains for all tickets: prices for certain price levels and a percentage at which the next price level starts
 */
@Entity
public class PriceLevel extends AbstractModel implements Comparable<PriceLevel>{
    @NotNull
    @Min(1)
    private double dayTicketPrice;
    @NotNull
    @Min(1)
    private double campingTicketPrice;
    @NotNull
    @Min(1)
    private double vipTicketPrice;
    /**
     * the price level is valid until the percentage of sold tickets is exceeded
     */
    @Min(1)
    @NotNull
    private double percentageForPriceLevel;
    public PriceLevel(){}
    @ManyToOne
    private TicketManager actualTicketPrices;


    public PriceLevel(double dayTicketPrice, double CampingTicketPrice, double VipTicketPrice,
                      double PercentageForPriceLevel) throws PriceLevelException {

        if(PercentageForPriceLevel > 100 || PercentageForPriceLevel < 0){
            throw new PriceLevelException("PercentageForPricelevel not valid");
        }
        this.percentageForPriceLevel = PercentageForPriceLevel;
        this.dayTicketPrice = dayTicketPrice;
        this.campingTicketPrice = CampingTicketPrice;
        this.vipTicketPrice = VipTicketPrice;

    }

    /**
     * for sorting the pricelevel arrayList in TicketManager constructor
     * java.lang.double.valueOf because an object is needed for Collections.sort()
     * @param  priceLevel
     * @return returns a double value as an object so that they can be compared and sorted
     * @see TicketManager Collections.sort(priceLevels)
     */
    @Override
    public int compareTo(PriceLevel priceLevel) {

        return  java.lang.Double.valueOf(this.percentageForPriceLevel).compareTo(java.lang.Double.valueOf(priceLevel.percentageForPriceLevel));
    }


    public double getPercentageForPriceLevel() {
        return percentageForPriceLevel;
    }

    public double getDayTicketPrice(){return dayTicketPrice;}
    public double getCampingTicketPrice(){return campingTicketPrice;}

    public void setCampingTicketPrice(double campingTicketPrice) {
        this.campingTicketPrice = campingTicketPrice;
    }

    public void setVipTicketPrice(double vipTicketPrice) {
        this.vipTicketPrice = vipTicketPrice;
    }

    public void setPercentageForPriceLevel(double percentageForPriceLevel) {
        this.percentageForPriceLevel = percentageForPriceLevel;
    }

    public void setActualTicketPrices(TicketManager actualTicketPrices) {
        this.actualTicketPrices = actualTicketPrices;
    }

    public double getVipTicketPrice(){return vipTicketPrice;}

    public void setDayTicketPrice(double dayTicketPrice) {
        this.dayTicketPrice = dayTicketPrice;
    }
}

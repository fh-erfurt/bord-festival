package de.bord.festival.controllers.dataContainers;

import de.bord.festival.models.PriceLevel;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedList;
import java.util.List;

public class TicketManagerContainer {

    @NotNull
    @Min(10)
    private int numberOfDayTickets;
    @NotNull
    @Min(10)
    private int numberOfCampingTickets;
    @NotNull
    @Min(10)
    private int numberOfVipTickets;

    @Valid
    List<PriceLevel> priceLevels;
    @NotNull
    @Size(min = 2, max = 100)
    private String dayTicketDescription;
    @NotNull
    @Size(min = 2, max = 100)
    private String campingTicketDescription;
    @NotNull
    @Size(min = 2, max = 100)
    private String vipTicketDescription;

    public TicketManagerContainer() {
        this.priceLevels=new LinkedList<>();
        priceLevels.add(new PriceLevel());
        priceLevels.add(new PriceLevel());
        priceLevels.add(new PriceLevel());

    }
    public void setNumberOfDayTickets(int numberOfDayTickets) {
        this.numberOfDayTickets = numberOfDayTickets;
    }

    public void setNumberOfCampingTickets(int numberOfCampingTickets) {
        this.numberOfCampingTickets = numberOfCampingTickets;
    }

    public void setNumberOfVipTickets(int numberOfVipTickets) {
        this.numberOfVipTickets = numberOfVipTickets;
    }

    public void setPriceLevels(List<PriceLevel> priceLevels) {
        this.priceLevels = priceLevels;
    }

    public void setDayTicketDescription(String dayTicketDescription) {
        this.dayTicketDescription = dayTicketDescription;
    }

    public void setCampingTicketDescription(String campingTicketDescription) {
        this.campingTicketDescription = campingTicketDescription;
    }

    public void setVipTicketDescription(String vipTicketDescription) {
        this.vipTicketDescription = vipTicketDescription;
    }

    public int getNumberOfDayTickets() {
        return numberOfDayTickets;
    }

    public int getNumberOfCampingTickets() {
        return numberOfCampingTickets;
    }

    public int getNumberOfVipTickets() {
        return numberOfVipTickets;
    }

    public List<PriceLevel> getPriceLevels() {
        return priceLevels;
    }

    public String getDayTicketDescription() {
        return dayTicketDescription;
    }

    public String getCampingTicketDescription() {
        return campingTicketDescription;
    }

    public String getVipTicketDescription() {
        return vipTicketDescription;
    }









}

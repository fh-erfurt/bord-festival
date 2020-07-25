package de.bord.festival.models;

import de.bord.festival.ticket.Type;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * gives information about ticket price and current price level
 * for the selected ticket type (DAY, CAMPING, VIP)
 */
@Entity
@Inheritance (strategy= InheritanceType.JOINED)
public abstract class Ticket extends AbstractModel {

    public enum TicketType {DAY, CAMPING, VIP}

    protected String description;
    protected double stdPrice;

    public Ticket(String description, double standardPrice) {

        this.description = description;
        this.stdPrice = standardPrice;
    }

    public Ticket(){}

    abstract public Type getTicketType();

    public void setDescription(String description){
        this.description = description;
    }
    public void setStdPrice(double stdPrice){
        this.stdPrice = stdPrice;
    }

    public double getStdPrice() {
        return this.stdPrice;
    }
    public String getDescription(){
        return this.description;
    }
}
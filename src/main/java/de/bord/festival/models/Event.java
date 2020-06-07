package de.bord.festival.models;

import javax.persistence.Entity;

@Entity
public class Event extends AbstractModel {
    private String eventName;
    private double budget;
    private double actualCosts = 0;
    //private LineUp lineUp;
    private int maxCapacity;
    private Address address;

    public Event(){}
    public Event(String eventName, double budget, int maxCapacity){
        this.eventName = eventName;
        this.budget = budget;
        this.maxCapacity = maxCapacity;
    }

    public String getEventName() {
        return this.eventName;
    }
    public double getBudget() {
        return this.budget;
    }
    public double getActualCosts() {
        return this.actualCosts;
    }
    public int getMaxCapacity() {
        return this.maxCapacity;
    }
    public Address getAddress() {
        return this.address;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    public void setBudget(double budget) {
        this.budget = budget;
    }
    public void setActualCosts(double actualCosts) {
        this.actualCosts = actualCosts;
    }
    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
}

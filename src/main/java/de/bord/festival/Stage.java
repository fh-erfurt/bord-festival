package de.bord.festival;

public class Stage {
    private Address address;
    private int id;
    private String name;
    private int capacity;
    public Stage(int id, String name, int capacity, Address address){
        this.address=address;
        this.capacity=capacity;
        this.id=id;
        this.name=name;
    }
}

package de.bord.festival.stageManagement;

import de.bord.festival.address.Address;
import de.bord.festival.band.Band;

import java.sql.Time;
import java.util.Map;

public class Stage {

    private int id;
    private String name;
    private int capacity;

    public Stage(int id, String name, int capacity) {
        this.capacity = capacity;
        this.id = id;
        this.name = name;
    }
    public int getId(){
        return this.id;
    }

}

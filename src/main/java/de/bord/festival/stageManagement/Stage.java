package de.bord.festival.stageManagement;


public class Stage {

    private int id;
    private String name;


    public Stage(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public int getId(){
        return this.id;
    }

}

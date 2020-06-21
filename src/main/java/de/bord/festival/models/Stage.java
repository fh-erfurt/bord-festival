package de.bord.festival.models;

import de.bord.festival.models.EventInfo;
import de.bord.festival.models.AbstractModel;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Stage extends AbstractModel {
    private int identifier;
    private String name;

    public Stage(){};

    public Stage(int identifier, String name) {
        this.identifier = identifier;
        this.name = name;
    }

    public void setName(String name) { this.name = name; }

    public int getIdentifier() { return this.identifier; }

    public String getName() { return this.name; }
}

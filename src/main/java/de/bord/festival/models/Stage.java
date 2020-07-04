package de.bord.festival.models;

import de.bord.festival.models.EventInfo;
import de.bord.festival.models.AbstractModel;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Stage extends AbstractModel {
    @NotNull
    private int identifier;
    @NotNull
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

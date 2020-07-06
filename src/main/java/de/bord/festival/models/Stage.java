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
    private String stageName;

    public Stage(){};

    public Stage(int identifier, String name) {
        this.identifier = identifier;
        this.stageName = name;
    }

    public void setStageName(String name) { this.stageName = name; }

    public int getIdentifier() { return this.identifier; }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public String getStageName() { return this.stageName; }
}

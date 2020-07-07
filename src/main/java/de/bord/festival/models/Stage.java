package de.bord.festival.models;

import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Stage extends AbstractModel {
    @NotNull
    @Min(1)
    private int identifier;
    @NotNull
    @Size(min = 2, max = 50, message = "Name of the stage should be between 2 and 50 characters")
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

package de.bord.festival.models;

import javax.persistence.Entity;

@Entity
public class Stage extends AbstractModel{
    private String name;

    public Stage(String name) {
        this.name = name;
    }

    public String getName() { return this.name; }

    public void setName(String newName) { this.name = newName; }

    @Override
    public boolean equals(Object object) {
        if(object instanceof Stage) {
            Stage stage = (Stage)object;
            return this.name.equals(stage.getName());
        }
        return false;
    }
}

package de.bord.festival.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Address extends AbstractModel {
    @Size(min = 2, max = 30)
    @NotNull
    private String country;
    @Size(min = 2, max = 30)
    @NotNull
    private String city;
    @Size(min = 2, max = 30)
    @NotNull
    private String street;
    @Size(min = 4, max = 10)
    @NotNull
    private String zip;

    public Address() {
    }

    public Address(String country, String city, String street, String zip) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.zip = zip;
    }


    @Override
    public boolean equals(Object object) {
        if (object instanceof Address) {
            Address address = (Address) object;
            return this.country.equals(address.getCountry())
                    && this.city.equals(address.getCity())
                    && this.zip.equals(address.getZip())
                    && this.street.equals(address.getStreet());
        }
        return false;
    }

    public String getStreet() {
        return this.street;
    }

    public String getZip() {
        return this.zip;
    }

    public String getCity() {
        return this.city;
    }

    public String getCountry() {
        return this.country;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void update(Address address) {
        this.city=address.getCity();
        this.country=address.getCountry();
        this.street=address.getStreet();
        this.zip=address.getZip();
    }
}

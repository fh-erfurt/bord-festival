package de.bord.festival.address;

import java.util.Objects;

public class Address {
    private String country;
    private String city;
    private String street;
    private String zip;

    public Address(String country, String city, String street, String zip) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.zip = zip;
    }


    @Override
    public boolean equals(Object object) {
        if(object instanceof Address) {
            Address address = (Address)object;
            return this.country.equals(address.getCountry()) && this.city.equals(address.getCity()) &&
                    this.zip.equals(address.getZip()) && this.street.equals(address.getStreet());
        }
        return false;
    }

    private String getStreet() {
        return this.street;
    }

    private String getZip() {
        return this.zip;
    }

    private String getCity() {
        return this.city;
    }

    private String getCountry() {
        return this.country;
    }
}

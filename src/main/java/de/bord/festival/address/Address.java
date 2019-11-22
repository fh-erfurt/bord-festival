package de.bord.festival.address;

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

    public boolean equals(Address address) {
        return this.country.equals(address.getCountry()) && this.city.equals(address.getCity()) &&
                this.zip.equals(address.getZip()) && this.street.equals(address.getStreet());
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

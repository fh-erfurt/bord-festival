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


    @Override
    public boolean equals(Object object) {
        if(object instanceof Address) {
            Address address = (Address)object;
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
    
    public void setStreet(String street){
        this.street=street;
    }
    public void setZip(String zip){
        this.zip=zip;
    }
    public void setCity(String city){
        this.city=city;
    }
    public void setCountry(String country){
        this.country=country;
    }
}

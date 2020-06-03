package de.bord.festival.database;

import de.bord.festival.models.Address;
import de.bord.festival.repository.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressJPATest {
    AddressRepository addressRepository;
    Address address;
    @BeforeEach
    void initialize(){
        this.address= new Address("Germany", "Berlin", "Plumb", "2211");
        this.addressRepository=new AddressRepository();
    }
    @Test
    void should_create_new_address_in_database(){
        addressRepository.create(address);
        Address databaseAddress=addressRepository.findOne(this.address);
        assertEquals("Germany", databaseAddress.getCountry());
        assertEquals("Berlin", databaseAddress.getCity());
        assertEquals("Plumb", databaseAddress.getStreet());
        assertEquals("2211", databaseAddress.getZip());

        assertEquals(1, databaseAddress.getId());
    }
}

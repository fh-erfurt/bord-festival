package de.bord.festival.database;

import de.bord.festival.models.Address;
import de.bord.festival.repository.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressJPATest {
    AddressRepository addressRepository;
    Address address;

    @BeforeEach
    void initialize() {
        this.address = new Address("Germany", "Berlin", "Plumb", "2211");
        this.addressRepository = new AddressRepository();
    }

    @Test
    void should_create_new_address_in_database() {
        addressRepository.create(address);
        Address databaseAddress = addressRepository.findOne(this.address);
        assertEquals("Germany", databaseAddress.getCountry());
        assertEquals("Berlin", databaseAddress.getCity());
        assertEquals("Plumb", databaseAddress.getStreet());
        assertEquals("2211", databaseAddress.getZip());

        assertEquals(1, databaseAddress.getId());
    }

    @Test
    void should_change_street_in_database() {
        addressRepository.create(address);
        addressRepository.update(address, "Totoro");
        Address databaseAddress = addressRepository.findOne(this.address);

        assertEquals("Totoro", databaseAddress.getStreet());
        assertEquals(1, databaseAddress.getId());
    }

    @Test
    void should_delete_address() {
        addressRepository.create(address);
        addressRepository.delete(address);
        List<Address> addresses = addressRepository.findAll("Address");
        assertEquals(0, addresses.size());
    }
}

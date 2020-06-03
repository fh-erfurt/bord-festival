package de.bord.festival.repository;

import de.bord.festival.models.Address;

public class AddressRepository extends AbstractRepository<Address> {
    @Override
    protected void updateOperation(Address model, String argument) {
        model.setStreet(argument);
    }
}

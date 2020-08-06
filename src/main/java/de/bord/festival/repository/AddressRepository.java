package de.bord.festival.repository;

import de.bord.festival.models.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long>{
    Address findById(long id);
}

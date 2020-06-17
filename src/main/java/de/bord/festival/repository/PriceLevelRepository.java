package de.bord.festival.repository;

import de.bord.festival.ticket.PriceLevel;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;

public interface PriceLevelRepository extends CrudRepository<PriceLevel, Long> {

    PriceLevel findById(long id);
}

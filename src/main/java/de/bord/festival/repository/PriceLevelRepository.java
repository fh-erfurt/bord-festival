package de.bord.festival.repository;

import de.bord.festival.models.PriceLevel;


import org.springframework.data.repository.CrudRepository;

public interface PriceLevelRepository extends CrudRepository<PriceLevel, Long> {

    PriceLevel findById(long id);
}

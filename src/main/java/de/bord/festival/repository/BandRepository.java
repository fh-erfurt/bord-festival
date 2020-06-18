package de.bord.festival.repository;

import de.bord.festival.models.Band;
import org.springframework.data.repository.CrudRepository;

public interface BandRepository extends CrudRepository<Band, Long>{
    Band findById(long id);
}

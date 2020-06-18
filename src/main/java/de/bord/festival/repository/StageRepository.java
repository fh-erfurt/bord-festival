package de.bord.festival.repository;

import de.bord.festival.models.Stage;
import org.springframework.data.repository.CrudRepository;

public interface StageRepository extends CrudRepository<Stage, Long> {
    Stage findById(long id);
}

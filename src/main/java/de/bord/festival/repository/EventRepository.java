package de.bord.festival.repository;

import de.bord.festival.models.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Long> {
    Event findById(long id);
}

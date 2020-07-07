package de.bord.festival.repository;

import de.bord.festival.models.Event;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventRepository extends CrudRepository<Event, Long> {
    Event findById(long id);
    List<Event> findAll();
}

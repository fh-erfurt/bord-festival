package de.bord.festival.repository;

import de.bord.festival.models.TimeSlot;
import org.springframework.data.repository.CrudRepository;

public interface TimeSlotRepository extends CrudRepository<TimeSlot, Long> {
    TimeSlot findById(long id);
}

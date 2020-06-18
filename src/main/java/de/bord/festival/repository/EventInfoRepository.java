package de.bord.festival.repository;

import de.bord.festival.models.EventInfo;
import org.springframework.data.repository.CrudRepository;

public interface EventInfoRepository extends CrudRepository<EventInfo, Long> {
    EventInfo findById(long id);
}

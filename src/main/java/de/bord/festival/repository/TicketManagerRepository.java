package de.bord.festival.repository;

import de.bord.festival.models.TicketManager;
import org.springframework.data.repository.CrudRepository;

public interface TicketManagerRepository extends CrudRepository<TicketManager, Long> {

    TicketManager findById(long id);

    }

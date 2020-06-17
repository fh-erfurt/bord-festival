package de.bord.festival.repository;

import de.bord.festival.ticket.PriceLevel;
import de.bord.festival.ticket.TicketManager;
import org.springframework.data.repository.CrudRepository;

public interface TicketManagerRepository extends CrudRepository<TicketManager, Long> {

    TicketManager findById(long id);

    }

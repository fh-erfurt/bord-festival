package de.bord.festival.repository;

import de.bord.festival.models.Event;
import de.bord.festival.models.Order;
import de.bord.festival.models.PriceLevel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findAll();
}

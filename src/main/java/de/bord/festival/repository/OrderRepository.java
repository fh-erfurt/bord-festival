package de.bord.festival.repository;

import de.bord.festival.models.Order_;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order_, Long> {
    List<Order_> findAll();
}

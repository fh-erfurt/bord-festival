package de.bord.festival.repository;

import de.bord.festival.models.Client;
import de.bord.festival.models.LineUp;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {
    Client findById(long id);
    Client findByMail(String mail);
}

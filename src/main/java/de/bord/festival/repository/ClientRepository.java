package de.bord.festival.repository;

import de.bord.festival.models.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client, Long> {
    Client findById(long id);
    List<Client> findAll();
    Optional<Client> findByMail(String mail);
}

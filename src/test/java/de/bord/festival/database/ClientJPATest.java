package de.bord.festival.database;


import de.bord.festival.help.HelpClasses;
import de.bord.festival.models.Client;
import de.bord.festival.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientJPATest {
    ClientRepository clientRepository;
    Client client;

   @BeforeEach
    void initialize() {
       HelpClasses helper = new HelpClasses();
        this.client = new Client("Max", "Mustermann", "max.muster@mann.de", helper.getAddress());
        this.clientRepository = new ClientRepository();
    }

    @Test
    void should_create_new_client_in_database() {
        clientRepository.create(client);
        Client databaseClient = clientRepository.findOne(this.client);
        assertEquals("Max", databaseClient.getFirstname());
        assertEquals("Mustermann", databaseClient.getLastname());
        assertEquals("max.muster@mann.de", databaseClient.getMail());

        assertEquals(1, databaseClient.getId());
    }

    @Test
    void should_change_lastname_in_database() {
        clientRepository.create(client);
        clientRepository.update(client, "Mastermann");
        Client databaseClient = clientRepository.findOne(this.client);

        assertEquals("Mastermann", databaseClient.getLastname());
        assertEquals(1, databaseClient.getId());
    }

    @Test
    void should_delete_client() {
        clientRepository.create(client);
        clientRepository.delete(client);
        List<Client> addresses = clientRepository.findAll("Client");
        assertEquals(0, addresses.size());
    }
}


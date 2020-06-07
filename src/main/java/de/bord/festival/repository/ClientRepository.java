package de.bord.festival.repository;

import de.bord.festival.models.Client;


public class ClientRepository extends AbstractRepository<Client>{

    @Override
    protected void updateOperation(Client model, String argument) { model.setLastname(argument); }
}

package de.bord.festival.repository;

import de.bord.festival.models.Event;

public class EventRepository extends AbstractRepository<Event> {

    @Override
    protected void updateOperation(Event model, String argument) {
        model.setEventName(argument);
    }
}

package de.bord.festival.database;

import de.bord.festival.models.Event;
import de.bord.festival.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class EventJPATest {
    EventRepository eventRepository;
    Event event;

    @BeforeEach
    void initialize() {
        this.event = new Event("BORD-Testival", 1000000, 50000);
        this.eventRepository = new EventRepository();
    }

    @Test
    void should_create_new_event_in_database() {
        eventRepository.create(event);
        Event databaseEvent = eventRepository.findOne(this.event);

        assertEquals("BORD-Testival", databaseEvent.getEventName());
        assertEquals(0, databaseEvent.getActualCosts());
        assertEquals(1000000, databaseEvent.getBudget());
        assertEquals(50000, databaseEvent.getMaxCapacity());
    }

    @Test
    void should_change_festivalname_in_database() {
        eventRepository.create(event);
        eventRepository.update(event, "Testname");

        Event databaseEvent = eventRepository.findOne(this.event);
        assertEquals("Testname", databaseEvent.getEventName());
    }

    @Test
    void should_delete_event() {
        eventRepository.create(event);
        eventRepository.delete(event);

        Event databaseEvent = eventRepository.findOne(this.event);
        assertNull(databaseEvent);
    }
}

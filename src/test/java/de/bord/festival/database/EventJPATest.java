package de.bord.festival.database;

import de.bord.festival.exception.BudgetOverflowException;
import de.bord.festival.exception.DateDisorderException;
import de.bord.festival.exception.PriceLevelException;
import de.bord.festival.exception.TimeSlotCantBeFoundException;
import de.bord.festival.help.HelpClasses;
import de.bord.festival.models.Event;
import de.bord.festival.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class EventJPATest {
    @Autowired
    EventRepository eventRepository;
    HelpClasses helper;
    Event event;
    @BeforeEach
    void initialize() throws DateDisorderException, PriceLevelException {
        this.helper=new HelpClasses();
        this.event= helper.getValidNDaysEvent(1);
    }

    @Test
    void should_save_event_into_database() {
        //when
        Event databaseEvent= eventRepository.save(this.event);
       //then
        assertEquals( "Bord",databaseEvent.getName());
        assertEquals( 2019, databaseEvent.getBudget());
        assertEquals( "Germany", databaseEvent.getAddress().getCountry());
        assertEquals( "Berlin", databaseEvent.getAddress().getCity());
        assertEquals( "Nordwez 1", databaseEvent.getAddress().getStreet());
        assertEquals( LocalTime.of(10, 30), databaseEvent.getStartTime());
        assertEquals( LocalTime.of(23, 59), databaseEvent.getEndTime());
        assertEquals( 1, databaseEvent.getNumberOfDays());
        assertEquals(1, databaseEvent.getId());

        assertEquals(1, databaseEvent.getNumberOfStages());
        assertEquals(0, databaseEvent.getNumberOfBands());

    }
    @Test
    void should_update_event_in_database() throws BudgetOverflowException, TimeSlotCantBeFoundException {
        //when
        eventRepository.save(this.event);
        Event databaseEvent=eventRepository.findById(1);
        event.addBand(helper.getBand(),50);
        event.addStage(helper.getStage(2));
        databaseEvent=eventRepository.save(event);
        //then

        assertEquals( "Bord",databaseEvent.getName());
        assertEquals( 2019, databaseEvent.getBudget());
        assertEquals( LocalTime.of(10, 30), databaseEvent.getStartTime());
        assertEquals( LocalTime.of(23, 59), databaseEvent.getEndTime());

        assertEquals(1, databaseEvent.getId());

        assertEquals(1, databaseEvent.getNumberOfBands());
        assertEquals(2, databaseEvent.getNumberOfStages());

    }

    @Test
    void should_update_event_in_database_1() throws BudgetOverflowException, TimeSlotCantBeFoundException {
        //when
        event.addBand(helper.getBand(),50);
        event.addStage(helper.getStage(2));
        Event databaseEvent=eventRepository.save(this.event);

        event.removeBand(helper.getBand());
        event.removeStage(1);
        eventRepository.save(event);
        databaseEvent=eventRepository.findById(1);
        //then
        assertEquals( "Bord",databaseEvent.getName());
        assertEquals( 2019, databaseEvent.getBudget());
        assertEquals( LocalTime.of(10, 30), databaseEvent.getStartTime());
        assertEquals( LocalTime.of(23, 59), databaseEvent.getEndTime());

        assertEquals(1, databaseEvent.getId());

        assertEquals(0, databaseEvent.getNumberOfBands());
        assertEquals(1, databaseEvent.getNumberOfStages());

    }

    @Test
    void should_delete_event_in_database() throws BudgetOverflowException, TimeSlotCantBeFoundException {
        //when
        eventRepository.save(this.event);
        Event databaseEvent=eventRepository.findById(1);
        assertEquals(1, databaseEvent.getId());

        eventRepository.delete(event);
        databaseEvent=eventRepository.findById(1);

        //then
        assertNull( databaseEvent);


    }
}

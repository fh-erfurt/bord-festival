package de.bord.festival.database;

import de.bord.festival.helper.HelpClasses;
import de.bord.festival.exception.*;
import de.bord.festival.models.Band;
import de.bord.festival.models.Event;
import de.bord.festival.models.EventInfo;
import de.bord.festival.repository.BandRepository;
import de.bord.festival.repository.EventInfoRepository;
import de.bord.festival.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


/*@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
@ActiveProfiles("test")*/
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class EventJPATest {
    @Autowired
    EventRepository eventRepository;
    HelpClasses helper;
    Event event;
    @Autowired
    BandRepository bandRepository;
    @Autowired
    EventInfoRepository eventInfoRepository;
    @BeforeEach
    void initialize() throws DateDisorderException, PriceLevelException, TimeDisorderException {
        this.helper = new HelpClasses();
        this.event = helper.getValidNDaysEvent(1);
    }

    @Test
    void should_save_event_into_database() {
        //when
        Event databaseEvent = eventRepository.save(this.event);
        //then
        assertEquals("Bord", databaseEvent.getName());
        assertEquals(BigDecimal.valueOf(2019), databaseEvent.getBudget());
        assertEquals("Germany", databaseEvent.getAddress().getCountry());
        assertEquals("Berlin", databaseEvent.getAddress().getCity());
        assertEquals("Nordwez 1", databaseEvent.getAddress().getStreet());
        assertEquals(LocalTime.of(10, 30), databaseEvent.getStartTime());
        assertEquals(LocalTime.of(23, 59), databaseEvent.getEndTime());
        assertEquals(1, databaseEvent.getNumberOfDays());
        assertEquals(1, databaseEvent.getId());

        assertEquals(1, databaseEvent.getNumberOfStages());
        assertEquals(0, databaseEvent.getNumberOfBands());

    }
    @Test
    void findEvent(){
        Event e= eventRepository.findById(35);
    }

    @Test
    void should_update_event() throws BudgetOverflowException, TimeSlotCantBeFoundException {
        //when
        eventRepository.save(this.event);
        Event databaseEvent = eventRepository.findById(1);
        event.addBand(helper.getBand());
        event.addStage(helper.getStage("Stage2"));
        databaseEvent = eventRepository.save(event);
        //then

        assertEquals("Bord", databaseEvent.getName());
        assertEquals(BigDecimal.valueOf(2019), databaseEvent.getBudget());
        assertEquals(LocalTime.of(10, 30), databaseEvent.getStartTime());
        assertEquals(LocalTime.of(23, 59), databaseEvent.getEndTime());

        assertEquals(1, databaseEvent.getId());

        assertEquals(1, databaseEvent.getNumberOfBands());
        assertEquals(2, databaseEvent.getNumberOfStages());

    }

    @Test
    void should_update_event_1() throws BudgetOverflowException, TimeSlotCantBeFoundException {
        //when
        event.addBand(helper.getBand());
        event.addStage(helper.getStage("Stage2"));


        eventRepository.save(this.event);

        Event databaseEvent = eventRepository.findById(1);
        event.removeBand(helper.getBand());
        event.removeStage("Stage1");
        databaseEvent = eventRepository.save(event);
        //then
        assertEquals("Bord", databaseEvent.getName());
        assertEquals(BigDecimal.valueOf(2019), databaseEvent.getBudget());
        assertEquals(LocalTime.of(10, 30), databaseEvent.getStartTime());
        assertEquals(LocalTime.of(23, 59), databaseEvent.getEndTime());

        assertEquals(1, databaseEvent.getId());

        assertEquals(0, databaseEvent.getNumberOfBands());
        assertEquals(1, databaseEvent.getNumberOfStages());

    }

    @Test
    void should_update_event_2() throws TimeSlotCantBeFoundException, BudgetOverflowException {
        //when
        Event eventDatabase = eventRepository.save(event);

        Band band1 = helper.getBand("Lolo", 60, 60);
        Band band2 = helper.getBand("Loloo", 60, 60);
        Band band3 = helper.getBand("Lolooo", 60, 60);
        Band band4 = helper.getBand("Loloooo", 60, 60);

        EventInfo eventInfo1 = event.addBand(band1);
        EventInfo eventInfo2 = event.addBand(band2);
        EventInfo eventInfo3 = event.addBand(band3);
        EventInfo eventInfo4 = event.addBand(band4);


        eventDatabase = eventRepository.save(event);

        List<Band> bands = eventDatabase.getBands();
        //then
        assertEquals(1, eventDatabase.getId());
        assertEquals(4, eventDatabase.getNumberOfBands());
        //compare time for band from event object and from eventDatabase object
        assertEquals(eventInfo1.getTime(), bands.get(0).getEventInfos().get(0).getTime());
        assertEquals(eventInfo2.getTime(), bands.get(1).getEventInfos().get(0).getTime());
        assertEquals(eventInfo3.getTime(), bands.get(2).getEventInfos().get(0).getTime());
        assertEquals(eventInfo4.getTime(), bands.get(3).getEventInfos().get(0).getTime());

    }

    @Test
    void should_delete_event_in_database() throws BudgetOverflowException, TimeSlotCantBeFoundException {
        //when
        eventRepository.save(this.event);
        Event databaseEvent = eventRepository.findById(1);
        assertEquals(1, databaseEvent.getId());

        eventRepository.delete(event);
        databaseEvent = eventRepository.findById(1);

        //then
        assertNull(databaseEvent);


    }

    @Test
    void should_compare_eventInfo_of_band_and_of_band_from_database() throws BudgetOverflowException, TimeSlotCantBeFoundException {
        //when
        Band band = helper.getBand();
        event.addBand(band);
        EventInfo eventInfo = band.getEventInfos().get(0);
        Event databaseEvent = eventRepository.save(this.event);

        //then
        List<Band> bands = databaseEvent.getBands();
        Band databaseBand = bands.get(0);
        EventInfo databaseEventInfo = databaseBand.getEventInfos().get(0);

        assertEquals(eventInfo.getDate(), databaseEventInfo.getDate());
        assertEquals(eventInfo.getTime(), databaseEventInfo.getTime());
        assertEquals(eventInfo.getStage(), databaseEventInfo.getStage());
    }
}

package de.bord.festival.eventManagement;

import de.bord.festival.exception.PriceLevelException;
import de.bord.festival.help.HelpClasses;
import de.bord.festival.address.Address;
import de.bord.festival.band.Band;
import de.bord.festival.band.EventInfo;
import de.bord.festival.exception.BudgetException;
import de.bord.festival.exception.DateException;
import de.bord.festival.exception.TimeException;
import de.bord.festival.stageManagement.Stage;
import de.bord.festival.ticket.PriceLevel;
import de.bord.festival.ticket.TicketManager;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {
    HelpClasses help=new HelpClasses();
    @Test
    void should_throw_exception_start_end_date() {
        Stage stage = help.getStage();

        try {
            //invalid date couple(start date> end date)
            Event event = new Event(1, LocalDate.of(2018, 1, 1),
                    LocalDate.of(2017, 1, 1), "Bord", 2019, 1000,
                    stage, help.exampleTicketManager());

        } catch (DateException | PriceLevelException exception) {
            assertEquals("End date can't be before start date", exception.getMessage());
        }
    }

    @Test
    void should_return_1_number_of_stages() throws DateException {

        Event event = help.getValidNDaysEvent(3);
        assertEquals(1, event.getNumberOfStages());
    }

    @Test
    void should_return_2_number_of_stages() throws DateException {
        Event event = help.getValidNDaysEvent(3);
        Stage stage = help.getStage();
        event.addStage(stage);
        assertEquals(1, event.getNumberOfStages());
    }

    @Test
    void should_return_1_number_of_stages_because_of_removed_one() throws DateException {
        Event event = help.getValidNDaysEvent(3);
        Stage stage = help.getStage();
        event.addStage(stage);
        event.removeStage(stage.getAddress());
        assertEquals(1, event.getNumberOfStages());
    }

    @Test
    void should_return_1_number_of_bands() throws TimeException, BudgetException, DateException {
        Event event = help.getValidNDaysEvent(3);
        Band band = help.getBand();
        event.addBand(band, 60);
        assertEquals(1, event.getNumberOfBands());
    }

    @Test
    void should_return_0_number_of_bands() throws DateException {
        Event event = help.getValidNDaysEvent(3);
        assertEquals(0, event.getNumberOfBands());
    }

    @Test
    void should_throw_exception_budget() throws TimeException, DateException {
        Event event = help.getValidNDaysEvent(3);
        Band band = help.getBand("The Doors", 5000);
        try {
            event.addBand(band, 60);
        } catch (BudgetException exception) {

            assertEquals("The budget is not enough for this band", exception.getMessage());
        }
    }

    @Test
    void should_throw_exception_band() throws BudgetException, DateException {
        Event event = help.getValidNDaysEvent(3);
        Stage stage = help.getStage();
        event.addStage(stage);
        try {
            for (int i = 0; i < 4; i++) {
                Band band = help.getBand();
                event.addBand(band, 300);
            }
        } catch (TimeException exception) {
            assertEquals("This band plays already on another stage", exception.getMessage());
        }
    }

    @Test
    void should_return_3_days() throws DateException {
        Event event = help.getValidNDaysEvent(3);
        assertEquals(3, event.getNumberOfDays());
    }

    @Test
    void should_return_1_day() throws DateException {
        Event event = help.getValidNDaysEvent(1);
        assertEquals(1, event.getNumberOfDays());
    }

    @Test
    void should_return_false() throws TimeException, BudgetException, DateException {
        //Given
        Event event = help.getValidNDaysEvent(1);
        Band band1 = help.getBand("first band", 30);
        Band band2 = help.getBand("second band", 30);
        Band band3 = help.getBand("third band", 30);
        //if
        event.addBand(band1, 300);
        event.addBand(band2, 300);
        //then
        assertNull(event.addBand(band3, 300));


    }

    @Test
    void should_return_false_because_on_stage_should_play_band() throws TimeException, BudgetException, DateException {
        //Given
        Event event = help.getValidNDaysEvent(1);
        Band band1 = help.getBand("first band", 30);
        Band band2 = help.getBand("second band", 30);
        //if
        event.addBand(band1, 300);
        event.addBand(band2, 300);
        //then
        Address address = new Address("Moldawien", "Chisinau", "Heln 12", "7829");
        assertFalse(event.removeStage(address));


    }

    @Test
    void should_return_true() throws TimeException, BudgetException, DateException {
        //Given
        Event event = help.getValidNDaysEvent(1);
        Band band1 = help.getBand("first band", 30);
        Band band3 = help.getBand("third band", 30);
        //if
        event.addBand(band1, 300);
        //then
        assertNotNull(event.addBand(band3, 300));

    }


    @Test
    void should_return_date2020_03_01_time10_30() throws TimeException, DateException {
        //Given 3 days of festival
        LineUp lineUp = help.getLineUp(LocalDate.of(2020, 3, 1),
                LocalDate.of(2020, 3, 3));
        Band band = help.getBand();
        LocalDate resultDate = LocalDate.of(2020, 3, 1);
        LocalTime resultTime = LocalTime.of(10, 30);
        LocalDateTime resultDateTime = LocalDateTime.of(resultDate, resultTime);
        //when
        EventInfo eventInfo = lineUp.addBand(band, 30);
        LocalDateTime actualDateTime = LocalDateTime.of(eventInfo.getDate(), eventInfo.getTime());
        //then
        assertEquals(resultDateTime, actualDateTime);
    }

    @Test
    void should_return_date2020_03_01_time12_05() throws TimeException, DateException {
        //Given 3 days of festival with 1 stage
        LineUp lineUp = help.getLineUp(LocalDate.of(2020, 3, 1),
                LocalDate.of(2020, 3, 3));
        Band band = help.getBand();
        Band band2 = help.getBand();
        LocalDate resultDate = LocalDate.of(2020, 3, 1);
        LocalTime resultTime = LocalTime.of(12, 5);
        LocalDateTime resultDateTime = LocalDateTime.of(resultDate, resultTime);
        //when
        lineUp.addBand(band, 65);
        EventInfo eventInfo = lineUp.addBand(band2, 30);
        LocalDateTime actualDateTime = LocalDateTime.of(eventInfo.getDate(), eventInfo.getTime());
        //then
        assertEquals(resultDateTime, actualDateTime);
    }

    @Test
    void should_return_false_for_added_stage() throws DateException{

        LineUp lineUp = help.getLineUp(LocalDate.of(2020, 12, 12),
                LocalDate.of(2020, 12, 12));
        Stage stage = help.getStage();
        //this function is used in creation of lineUp
        assertFalse(lineUp.addStage(stage));
    }

    @Test
    void should_return_true_for_added_stage() throws DateException {

        LineUp lineUp = help.getLineUp(LocalDate.of(2020, 12, 12),
                LocalDate.of(2020, 12, 12));
        Address address = new Address("G", "b", "f", "93");
        assertTrue(lineUp.addStage(new Stage(1, "name", 12, address)));
    }

    @Test
    void should_return_2_for_number_of_stages() throws DateException{
        //Given
        LineUp lineUp = help.getLineUp(LocalDate.of(2020, 12, 12),
                LocalDate.of(2020, 12, 12));
        Address address = new Address("G", "b", "f", "93");
        Stage stage = new Stage(1, "name", 12, address);
        //When
        lineUp.addStage(stage);
        //Than
        assertEquals(2, lineUp.getNumberOfStages());
    }

    @Test
    void should_return_date2020_03_01_time21_30() throws TimeException, BudgetException, DateException {
        //Given 3 days of festival with 2 stages and 4 bands
        LineUp lineUp = help.exampleLineUp();

        //When
        Band band = help.getBand();
        EventInfo eventInfo = lineUp.addBand(band, 30);

        LocalDate resultDate = LocalDate.of(2020, 3, 1);
        LocalTime resultTime = LocalTime.of(21, 30);
        LocalDateTime resultDateTime = LocalDateTime.of(resultDate, resultTime);
        LocalDateTime actualDateTime = LocalDateTime.of(eventInfo.getDate(), eventInfo.getTime());
        //then
        assertEquals(resultDateTime, actualDateTime);

    }
    @Test
    void should_return_null_because_of_long_playing_time_of_band() throws TimeException, DateException {
        //Given a new Program
        Program program = new Program(help.getStage(), help.getLineUp(LocalDate.of(2020, 12, 12),
                LocalDate.of(2020, 12, 12)));
        //When
        EventInfo eventInfo = program.addBand(help.getBand(), 5000);
        //Then
        assertNull(eventInfo);
    }


    private Band getBand() {
        return new Band(1, "GOD IS AN ASTRONAUT", "920", 500);

    }

    @Test
    void should_return_not_null_because_the_playing_time_of_band_is_not_till_end_of_day() throws TimeException, DateException{
        //Given a new Program
        Program program =new Program(help.getStage(), help.getLineUp(LocalDate.of(2020,12,12),
                LocalDate.of(2020, 12, 12)));
        //When
        EventInfo eventInfo= program.addBand(help.getBand(), 809);
        //Then
        assertNotNull(eventInfo);

    }

    @Test
    void should_return_false_because_the_band_doesnt_exist_in_event_list() throws DateException{
        //Given
        Event event =help.getValidNDaysEvent(1);
        //When
        boolean check=event.removeBand(help.getBand());
        assertFalse(check);
    }

    @Test
    void should_return_true_because_the_band_exists_in_event_list() throws DateException, BudgetException, TimeException{
        //Given
        Event event =help.getValidNDaysEvent(1);
        Band band=help.getBand();
        event.addBand(band, 45);
        //When
        boolean check=event.removeBand(band);
        assertTrue(check);
    }

    @Test
    void should_remove_band_because_time_and_date_are_valid_returns_true() throws DateException, BudgetException, TimeException{
        Event event =help.getValidNDaysEvent(1);
        Band band = help.getBand("Band1", 60);
        Band band2 = help.getBand("band2", 60);
        event.addBand(band, 60);
        event.addBand(band2, 60);
        event.addBand(band, 60);
        event.addBand(band2, 60);
        LocalDateTime dateAndTime= LocalDateTime.of(2018, 01,01, 12, 00);
        assertTrue(event.removeBand(band2, dateAndTime));
    }
    @Test
    void should_not_remove_band_because_time_is_not_valid_returns_false() throws DateException, BudgetException, TimeException{
        Event event =help.getValidNDaysEvent(1);
        Band band = help.getBand("Band1", 60);
        Band band2 = help.getBand("band2", 60);
        event.addBand(band, 60);
        event.addBand(band2, 60);
        event.addBand(band, 60);
        event.addBand(band2, 60);
        LocalDateTime dateAndTime= LocalDateTime.of(2018, 01,01, 12, 30);
        assertFalse(event.removeBand(band2, dateAndTime));
    }




}

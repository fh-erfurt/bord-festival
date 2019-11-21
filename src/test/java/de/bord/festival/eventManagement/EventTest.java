package de.bord.festival.eventManagement;

import de.bord.festival.address.Address;
import de.bord.festival.band.Band;
import de.bord.festival.band.EventInfo;
import de.bord.festival.exception.BudgetException;
import de.bord.festival.exception.DateException;
import de.bord.festival.exception.TimeException;
import de.bord.festival.stageManagement.Stage;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {
    @Test
    void should_throw_exception_start_end_date() {
        Stage stage = getStage();
        try {
            //invalid date couple(start date> end date)
            Event event = new Event(1, LocalDate.of(2018, 1, 1),
                    LocalDate.of(2017, 1, 1), "Bord", 2019, 1000,
                    stage);

        } catch (DateException exception) {
            assertEquals("End date can't be before start date", exception.getMessage());
        }
    }

    @Test
    void should_return_1_number_of_stages() throws DateException {

        Event event = getValidNDaysEvent(3);
        assertEquals(1, event.getNumberOfStages());
    }

    @Test
    void should_return_2_number_of_stages() throws DateException {
        Event event = getValidNDaysEvent(3);
        Stage stage = getStage();
        event.addStage(stage);
        assertEquals(1, event.getNumberOfStages());
    }

    @Test
    void should_return_1_number_of_stages_because_of_removed_one() throws DateException {
        Event event = getValidNDaysEvent(3);
        Stage stage = getStage();
        event.addStage(stage);
        event.removeStage(stage.getAddress());
        assertEquals(1, event.getNumberOfStages());
    }

    @Test
    void should_return_1_number_of_bands() throws TimeException, BudgetException, DateException {
        Event event = getValidNDaysEvent(3);
        Band band = getBand();
        event.addBand(band, 60);
        assertEquals(1, event.getNumberOfBands());
    }

    @Test
    void should_return_0_number_of_bands() throws DateException {
        Event event = getValidNDaysEvent(3);
        assertEquals(0, event.getNumberOfBands());
    }

    @Test
    void should_throw_exception_budget() throws TimeException, DateException {
        Event event = getValidNDaysEvent(3);
        Band band = getBand("The Doors", 5000);
        try {
            event.addBand(band, 60);
        } catch (BudgetException exception) {

            assertEquals("The budget is not enough for this band", exception.getMessage());
        }
    }

    @Test
    void should_throw_exception_band() throws BudgetException, DateException, DateException {
        Event event = getValidNDaysEvent(3);
        Stage stage = getStage();
        event.addStage(stage);
        try {
            for (int i = 0; i < 4; i++) {
                Band band = getBand();
                event.addBand(band, 300);
            }
        } catch (TimeException exception) {
            assertEquals("This band plays already on another stage", exception.getMessage());
        }
    }

    @Test
    void should_return_3_days() throws DateException {
        Event event = getValidNDaysEvent(3);
        assertEquals(3, event.getNumberOfDays());
    }

    @Test
    void should_return_1_day() throws DateException {
        Event event = getValidNDaysEvent(1);
        assertEquals(1, event.getNumberOfDays());
    }

    @Test
    void should_return_false() throws TimeException, BudgetException, DateException {
        //Given
        Event event = getValidNDaysEvent(1);
        Band band1 = getBand("first band", 30);
        Band band2 = getBand("second band", 30);
        Band band3 = getBand("third band", 30);
        //if
        event.addBand(band1, 300);
        event.addBand(band2, 300);
        //then
        assertNull(event.addBand(band3, 300));


    }

    @Test
    void should_return_false_because_on_stage_should_play_band() throws TimeException, BudgetException, DateException {
        //Given
        Event event = getValidNDaysEvent(1);
        Band band1 = getBand("first band", 30);
        Band band2 = getBand("second band", 30);
        Band band3 = getBand("third band", 30);
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
        Event event = getValidNDaysEvent(1);
        Band band1 = getBand("first band", 30);
        Band band3 = getBand("third band", 30);
        //if
        event.addBand(band1, 300);
        //then
        assertNotNull(event.addBand(band3, 300));

    }


    @Test
    void should_return_date2020_03_01_time10_30() throws TimeException, DateException {
        //Given 3 days of festival
        LineUp lineUp = getLineUp(LocalDate.of(2020, 3, 1),
                LocalDate.of(2020, 3, 3));
        Band band = getBand();
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
    void should_return_date2020_03_01_time12_05() throws TimeException {
        //Given 3 days of festival with 1 stage
        LineUp lineUp = getLineUp(LocalDate.of(2020, 3, 1),
                LocalDate.of(2020, 3, 3));
        Band band = getBand();
        Band band2 = getBand();
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
    void should_return_false_for_added_stage() {

        LineUp lineUp = getLineUp(LocalDate.of(2020, 12, 12),
                LocalDate.of(2020, 12, 12));
        Stage stage = getStage();
        //this function is used in creation of lineUp
        assertFalse(lineUp.addStage(stage));
    }

    @Test
    void should_return_true_for_added_stage() {

        LineUp lineUp = getLineUp(LocalDate.of(2020, 12, 12),
                LocalDate.of(2020, 12, 12));
        Address address = new Address("G", "b", "f", "93");
        assertTrue(lineUp.addStage(new Stage(1, "name", 12, address)));
    }

    @Test
    void should_return_2_for_number_of_stages() {
        //Given
        LineUp lineUp = getLineUp(LocalDate.of(2020, 12, 12),
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
        LineUp lineUp = exampleLineUp();

        //When
        Band band = getBand();
        EventInfo eventInfo = lineUp.addBand(band, 30);

        LocalDate resultDate = LocalDate.of(2020, 3, 1);
        LocalTime resultTime = LocalTime.of(21, 30);
        LocalDateTime resultDateTime = LocalDateTime.of(resultDate, resultTime);
        LocalDateTime actualDateTime = LocalDateTime.of(eventInfo.getDate(), eventInfo.getTime());
        //then
        assertEquals(resultDateTime, actualDateTime);
    }

    private Band getBand() {
        return new Band(1, "GOD IS AN ASTRONAUT", "920", 500);

    }

    private LineUp getLineUp(LocalDate startDate, LocalDate endDate) {
        Stage stage = getStage();
        return new LineUp(startDate, endDate, stage);

    }

    private Band getBand(String name, double priceProEvent) {
        return new Band(5, name, "911", priceProEvent);

    }

    private Stage getStage() {
        Address address = new Address("Moldawien", "Chisinau", "Heln 12", "7829");
        return new Stage(1, "stage1", 300, address);

    }

    private Event getValidNDaysEvent(int numberOfDays) throws DateException {

        return new Event(1, LocalDate.of(2018, 01, 01),
                LocalDate.of(2018, 01, numberOfDays), "Bord", 2019, 1000,
                getStage());


    }

    private LineUp exampleLineUp() throws TimeException, BudgetException, DateException {
        //lineUp for 3 days with 2 stages and 4 bands. Each band plays 5 hours
        LineUp lineUp = getLineUp(LocalDate.of(2020, 3, 1),
                LocalDate.of(2020, 3, 3));
        lineUp.addStage(getStage());


        Band band1 = getBand("band1", 40);
        Band band2 = getBand("band2", 40);
        Band band3 = getBand("band3", 40);
        Band band4 = getBand("band4", 40);
        lineUp.addBand(band1, 300);
        lineUp.addBand(band2, 300);
        lineUp.addBand(band3, 300);
        lineUp.addBand(band4, 300);
        return lineUp;
    }
}

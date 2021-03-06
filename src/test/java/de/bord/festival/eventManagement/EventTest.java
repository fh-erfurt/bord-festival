package de.bord.festival.eventManagement;

import de.bord.festival.helper.HelpClasses;
import de.bord.festival.exception.*;
import de.bord.festival.models.*;
import org.junit.jupiter.api.Test;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


import static org.junit.jupiter.api.Assertions.*;

class EventTest {
    HelpClasses help = new HelpClasses();

    @Test
    void should_throw_exception_start_end_date() {

        //invalid date couple(start date> end date)
        assertThrows(DateDisorderException.class, () -> {
            Stage stage = help.getStage();
            Event event = Event.getNewEvent(LocalTime.of(10, 30), LocalTime.of(23, 59), 30, LocalDate.of(2018, 1, 1),
                    LocalDate.of(2017, 1, 1), "Bord", BigDecimal.valueOf(2019),
                    stage, help.exampleTicketManager(), help.getAddress());
        });


    }

    @Test
    void should_return_1_number_of_stages() throws DateDisorderException, PriceLevelException, TimeDisorderException {

        Event event = help.getValidNDaysEvent(3);
        assertEquals(1, event.getNumberOfStages());
    }

    @Test
    void should_return_2_number_of_stages() throws DateDisorderException, PriceLevelException, TimeDisorderException {
        Event event = help.getValidNDaysEvent(3);
        Stage stage = help.getStage();
        event.addStage(stage);
        assertEquals(1, event.getNumberOfStages());
    }

    @Test
    void should_return_1_number_of_stages_because_of_removed_one() throws DateDisorderException, PriceLevelException, TimeDisorderException {
        Event event = help.getValidNDaysEvent(3);
        Stage stage = help.getStage();
        event.addStage(stage);
        event.removeStage(stage.getStageName());
        assertEquals(1, event.getNumberOfStages());
    }

    @Test
    void should_return_1_number_of_bands() throws TimeSlotCantBeFoundException, BudgetOverflowException, DateDisorderException, PriceLevelException, TimeDisorderException {
        Event event = help.getValidNDaysEvent(3);
        Band band = help.getBand();
        event.addBand(band);
        assertEquals(1, event.getNumberOfBands());
    }

    @Test
    void should_return_0_number_of_bands() throws DateDisorderException, PriceLevelException, TimeDisorderException {
        Event event = help.getValidNDaysEvent(3);
        assertEquals(0, event.getNumberOfBands());
    }

    @Test
    void should_throw_exception_budget() throws DateDisorderException, PriceLevelException, TimeDisorderException {
        Event event = help.getValidNDaysEvent(3);
        Band band = help.getBand("The Doors", 5000, 200);

        assertThrows(BudgetOverflowException.class, () -> {
            event.addBand(band);

        });
    }

    @Test
    void should_throw_exception_band() throws BudgetOverflowException, DateDisorderException, PriceLevelException, TimeDisorderException {
        Event event = help.getValidNDaysEvent(3);
        Stage stage = help.getStage();
        event.addStage(stage);
        try {
            for (int i = 0; i < 4; i++) {
                Band band = help.getBand();
                event.addBand(band);
            }
        } catch (TimeSlotCantBeFoundException exception) {
            assertEquals("This band plays already on another stage", exception.getMessage());
        }
    }

    @Test
    void should_return_3_days() throws DateDisorderException, PriceLevelException, TimeDisorderException {
        Event event = help.getValidNDaysEvent(3);
        assertEquals(3, event.getNumberOfDays());
    }

    @Test
    void should_return_1_day() throws DateDisorderException, PriceLevelException, TimeDisorderException {
        Event event = help.getValidNDaysEvent(1);
        assertEquals(1, event.getNumberOfDays());
    }

    @Test
    void should_return_false() throws TimeSlotCantBeFoundException, BudgetOverflowException, DateDisorderException, PriceLevelException, TimeDisorderException {
        //Given
        Event event = help.getValidNDaysEvent(1);
        Band band1 = help.getBand("first band", 30, 300);
        Band band2 = help.getBand("second band", 30, 300);
        Band band3 = help.getBand("third band", 30, 300);
        //if
        event.addBand(band1);
        event.addBand(band2);
        //then
        assertThrows(TimeSlotCantBeFoundException.class, () -> {
            event.addBand(band3);
        });


    }

    @Test
    void should_return_false_because_on_stage_should_play_band() throws TimeSlotCantBeFoundException, BudgetOverflowException, DateDisorderException, PriceLevelException, TimeDisorderException {
        //Given
        Event event = help.getValidNDaysEvent(1);
        Band band1 = help.getBand("first band", 30, 300);
        Band band2 = help.getBand("second band", 30, 300);
        //if
        event.addBand(band1);
        event.addBand(band2);
        //then

        assertFalse(event.removeStage("Stage1"));


    }

    @Test
    void should_return_true() throws TimeSlotCantBeFoundException, BudgetOverflowException, DateDisorderException, PriceLevelException, TimeDisorderException {
        //Given
        Event event = help.getValidNDaysEvent(1);
        Band band1 = help.getBand("first band", 30, 300);
        Band band3 = help.getBand("third band", 30, 300);
        //if
        event.addBand(band1);
        //then
        assertNotNull(event.addBand(band3));

    }


    @Test
    void should_return_date2020_03_01_time10_30() throws TimeSlotCantBeFoundException, DateDisorderException, PriceLevelException, TimeDisorderException {
        //Given 3 days of festival
        LineUp lineUp = help.getLineUp(LocalDate.of(2020, 3, 1),
                LocalDate.of(2020, 3, 3));
        Band band = help.getBand();
        LocalDate resultDate = LocalDate.of(2020, 3, 1);
        LocalTime resultTime = LocalTime.of(10, 30);
        LocalDateTime resultDateTime = LocalDateTime.of(resultDate, resultTime);
        //when
        EventInfo eventInfo = lineUp.addBand(band);
        LocalDateTime actualDateTime = LocalDateTime.of(eventInfo.getDate(), eventInfo.getTime());
        //then
        assertEquals(resultDateTime, actualDateTime);
    }

    @Test
    void should_return_date2020_03_01_time12_05() throws TimeSlotCantBeFoundException, DateDisorderException, PriceLevelException, TimeDisorderException {
        //Given 3 days of festival with 1 stage
        LineUp lineUp = help.getLineUp(LocalDate.of(2020, 3, 1),
                LocalDate.of(2020, 3, 3));
        Band band = help.getBand("Band1", 100, 65);
        Band band2 = help.getBand("Band1", 100, 30);
        LocalDate resultDate = LocalDate.of(2020, 3, 1);
        LocalTime resultTime = LocalTime.of(12, 5);
        LocalDateTime resultDateTime = LocalDateTime.of(resultDate, resultTime);
        //when
        lineUp.addBand(band);
        EventInfo eventInfo = lineUp.addBand(band2);
        LocalDateTime actualDateTime = LocalDateTime.of(eventInfo.getDate(), eventInfo.getTime());
        //then
        assertEquals(resultDateTime, actualDateTime);
    }

    @Test
    void should_return_false_for_added_stage() throws DateDisorderException, PriceLevelException, TimeDisorderException {
        LineUp lineUp = help.getLineUp(LocalDate.of(2020, 12, 12),
                LocalDate.of(2020, 12, 12));
        Stage stage = help.getStage();
        //this function is used in creation of lineUp
        assertFalse(lineUp.addStage(stage));
    }

    @Test
    void should_return_true_for_added_stage() throws DateDisorderException, PriceLevelException, TimeDisorderException {

        LineUp lineUp = help.getLineUp(LocalDate.of(2020, 12, 12),
                LocalDate.of(2020, 12, 12));
        Stage stage=new Stage("name");//stages with different index are not the same
        assertTrue(lineUp.addStage(stage));
    }

    @Test
    void should_return_2_for_number_of_stages() throws DateDisorderException, PriceLevelException, TimeDisorderException {
        //Given
        LineUp lineUp = help.getLineUp(LocalDate.of(2020, 12, 12),
                LocalDate.of(2020, 12, 12));
        Stage stage = new Stage("name");//stages with different index are not the same
        //When
        lineUp.addStage(stage);
        //Than
        assertEquals(2, lineUp.getNumberOfStages());
    }

    @Test
    void should_return_date2020_03_01_time21_30() throws TimeSlotCantBeFoundException, BudgetOverflowException, DateDisorderException, PriceLevelException, TimeDisorderException {
        //Given 3 days of festival with 2 stages and 4 bands
        LineUp lineUp = help.exampleLineUp();

        //When
        Band band = help.getBand(30);
        EventInfo eventInfo = lineUp.addBand(band);

        LocalDate resultDate = LocalDate.of(2020, 3, 1);
        LocalTime resultTime = LocalTime.of(22, 00);
        LocalDateTime resultDateTime = LocalDateTime.of(resultDate, resultTime);
        LocalDateTime actualDateTime = LocalDateTime.of(eventInfo.getDate(), eventInfo.getTime());
        //then
        assertEquals(resultDateTime, actualDateTime);

    }

    @Test
    void should_return_null_because_of_long_playing_time_of_band() throws TimeSlotCantBeFoundException, DateDisorderException, PriceLevelException, TimeDisorderException {
        //Given a new Program
        Program program = new Program(help.getStage(), help.getLineUp(LocalDate.of(2020, 12, 12),
                LocalDate.of(2020, 12, 12)));
        //When
        EventInfo eventInfo = program.addBand(help.getBand(5000));
        //Then
        assertNull(eventInfo);

    }

    @Test
    void should_return_not_null_because_the_playing_time_of_band_is_not_till_end_of_day() throws TimeSlotCantBeFoundException, DateDisorderException, PriceLevelException, TimeDisorderException {
        //Given a new Program
        Program program = new Program(help.getStage(), help.getLineUp(LocalDate.of(2020, 12, 12),
                LocalDate.of(2020, 12, 12)));
        //When
        EventInfo eventInfo = program.addBand(help.getBand());
        //Then
        assertNotNull(eventInfo);

    }

    @Test
    void should_return_false_because_the_band_doesnt_exist_in_event_list() throws DateDisorderException, PriceLevelException, TimeDisorderException {
        //Given
        Event event = help.getValidNDaysEvent(1);
        //When
        boolean check = event.removeBand(help.getBand());
        assertFalse(check);
    }

    @Test
    void should_return_true_because_the_band_exists_in_event_list() throws DateDisorderException, BudgetOverflowException, TimeSlotCantBeFoundException, PriceLevelException, TimeDisorderException {
        //Given
        Event event = help.getValidNDaysEvent(1);
        Band band = help.getBand();
        event.addBand(band);
        //When
        boolean check = event.removeBand(band);
        assertTrue(check);
    }

    @Test
    void should_remove_band_because_time_and_date_are_valid_returns_true() throws DateDisorderException, BudgetOverflowException, TimeSlotCantBeFoundException, PriceLevelException, TimeDisorderException {
        Event event = help.getValidNDaysEvent(1);
        Band band = help.getBand("Band1", 60, 60);
        Band band2 = help.getBand("band2", 60, 60);
        event.addBand(band);
        event.addBand(band2);
        event.addBand(band);
        event.addBand(band2);
        LocalDateTime dateAndTime = LocalDateTime.of(2018, 01, 01, 12, 00);
        assertTrue(event.removeBand(band2, dateAndTime));
    }

    @Test
    void should_not_remove_band_because_time_is_not_valid_returns_false() throws DateDisorderException, BudgetOverflowException, TimeSlotCantBeFoundException, PriceLevelException, TimeDisorderException {
        Event event = help.getValidNDaysEvent(1);
        Band band = help.getBand("Band1", 60, 60);
        Band band2 = help.getBand("band2", 60, 60);
        event.addBand(band);
        event.addBand(band2);
        event.addBand(band);
        event.addBand(band2);
        LocalDateTime dateAndTime = LocalDateTime.of(2018, 01, 01, 12, 30);
        assertFalse(event.removeBand(band2, dateAndTime));
    }
}

package de.bord.festival.eventManagement;

import de.bord.festival.address.Address;
import de.bord.festival.band.Band;
import de.bord.festival.stageManagement.Stage;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {
    @Test
    void should_throw_exception_start_end_date() throws Exception{
        Stage stage=getStage();
        try{
            //invalid date couple(start date> end date)
            Event event=new Event(1, LocalDate.of(2018, 01,01),
                    LocalDate.of(2017, 01,01), "Bord", 2019, 1000,
                    stage);

        }catch(Exception exeption){
            assertEquals("End date can't be before start date", exeption.getMessage());
        }
    }

    @Test
    void should_return_1_number_of_stages() {

        Event event = getValid3DaysEvent();
        assertEquals(1,event.getNumberOfStages());
    }
    @Test
    void should_return_2_number_of_stages(){
        Event event =getValid3DaysEvent();
        Stage stage=getStage();
        event.addStage(stage);
        assertEquals(event.getNumberOfStages(), 2);
    }
    @Test
    void should_return_1_number_of_bands() throws Exception{
        Event event =getValid3DaysEvent();
        Band band=getBand();
        event.addBand(band, 60);
        assertEquals(1, event.getNumberOfBands());
    }
    @Test
    void should_throw_exception_budget()throws Exception{
        Event event =getValid3DaysEvent();
        Band band=getBand(1, "The Doors", 5000);
        try{
            event.addBand(band, 60);
        }catch(Exception exeption){

            assertEquals("The budget is not enough for this band",exeption.getMessage());
        }
    }
    @Test void should_throw_exception_band() throws Exception{
        Event event=getValid3DaysEvent();
        Stage stage=getStage();
        event.addStage(stage);
        try {
            for (int i = 0; i < 4; i++) {
                Band band = getBand();
                event.addBand(band, 300);
            }
        }catch (Exception exeption){
            assertEquals("This band plays already on another stage", exeption.getMessage());
        }
    }




    private Band getBand(){
        Band band= new Band(1,"GOD IS AN ASTRONAUT", "920", 500);
        return band;
    }
    private Band getBand(int id,String name, double priceProEvent){
        Band band= new Band(id,name, "911", priceProEvent);
        return band;
    }
    private Stage getStage(){
        Address address=new Address("Moldawien", "Chisinau", "Heln 12", "7829");
        Stage stage=new Stage(1, "stage1", 300, address );
        return stage;
    }
    private Event getValid3DaysEvent(){

        try {
            Event event = new Event(1, LocalDate.of(2018, 01, 01),
                    LocalDate.of(2018, 01, 03), "Bord", 2019, 1000,
                    getStage());
            return event;
        }catch(Exception exeption){
            System.out.println(exeption.getMessage());
        }
        return null;
    }
}

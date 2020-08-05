package de.bord.festival.band;

import de.bord.festival.helper.HelpClasses;
import de.bord.festival.models.Band;
import org.junit.jupiter.api.Test;


import java.time.LocalDateTime;


import static org.junit.jupiter.api.Assertions.*;

public class BandTest {
    HelpClasses help=new HelpClasses();

    @Test
    void two_bands_with_same_names_should_be_equals(){
        Band band =new Band("Boston", "8493247", 50, 60);
        Band band2= new Band("Boston", "76238", 60, 60);
        assertTrue(band.equals(band2));
    }
    @Test
    void two_bands_with_different_names_should_not_be_equals(){
        Band band =new Band("Boston", "8493247", 50 , 60);
        Band band2= new Band("Boston1", "76238", 60, 60);
        assertFalse(band.equals(band2));
    }
    @Test
    void should_return_2_event_infos_without_removed_one(){
        //Given a band with 3 informations
        Band band=help.getBandWith3EventPlays();

        //When
        LocalDateTime dateAndTime=LocalDateTime.of(2020, 11,12,12,00);
        band.removeEventInfo(dateAndTime);
        int numberOfEventInfo=band.getNumberOfEventInfo();
        //Then
        assertEquals(2, numberOfEventInfo);

    }
    @Test
    void should_return_3_event_info(){
        //Given a band with 3 informations
        Band band=help.getBandWith3EventPlays();

        //When
        int numberOfEventInfo=band.getNumberOfEventInfo();
        //Then
        assertEquals(3, numberOfEventInfo);
    }

}

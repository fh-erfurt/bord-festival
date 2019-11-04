package de.bord.festival;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {
    @Test
    public void should_give_hello_world(){
        //Given
        Main main1=new Main();
        //when
        String hello=main1.giveHello();
        //Then
        assertEquals("Junit Hello WOrld dependency framework-- maven", hello);
    }



}

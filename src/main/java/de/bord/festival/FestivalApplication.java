package de.bord.festival;

import de.bord.festival.exception.DateDisorderException;
import de.bord.festival.exception.PriceLevelException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FestivalApplication {

    public static void main(String[] args) throws DateDisorderException, PriceLevelException {

        SpringApplication.run(FestivalApplication.class, args);
    }

}

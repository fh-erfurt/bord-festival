package de.bord.festival;

import de.bord.festival.repository.ClientRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FestivalApplication {

    private static ClientRepository clientRepository;

    public static void main(String[] args) {
        SpringApplication.run(FestivalApplication.class, args);
    }
}

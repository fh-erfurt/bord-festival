package de.bord.festival;

import de.bord.festival.repository.PriceLevelRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FestivalApplication {

	public static void main(String[] args) {
		SpringApplication.run(FestivalApplication.class, args);
		PriceLevelRepository priceLevelRepository;
	}

}

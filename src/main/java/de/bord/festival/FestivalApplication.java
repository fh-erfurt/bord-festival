package de.bord.festival;

import de.bord.festival.exception.*;
import de.bord.festival.helper.HelpClasses;
import de.bord.festival.models.Client;
import de.bord.festival.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FestivalApplication {

    @Autowired
    private static ClientRepository clientRepository;

    public FestivalApplication(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public static void main(String[] args) throws MailException, ClientNameException {
        SpringApplication.run(FestivalApplication.class, args);

        HelpClasses helper = new HelpClasses();

        Client userClient = helper.exampleClientAsUser();
        Client adminClient = helper.exampleClientAsAdmin();

        clientRepository.save(userClient);
        clientRepository.save(adminClient);
    }


}
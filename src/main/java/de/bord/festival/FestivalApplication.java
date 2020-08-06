package de.bord.festival;

import de.bord.festival.exception.*;
import de.bord.festival.helper.HelpClasses;
import de.bord.festival.models.Client;
import de.bord.festival.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

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

        Optional<Client> userExists = clientRepository.findByMail(userClient.getMail());
        Optional<Client> adminExists = clientRepository.findByMail(adminClient.getMail());

        if(!userExists.isPresent()) {
            clientRepository.save(userClient);
        }
        if(!adminExists.isPresent()) {
            clientRepository.save(adminClient);
        }
    }


}
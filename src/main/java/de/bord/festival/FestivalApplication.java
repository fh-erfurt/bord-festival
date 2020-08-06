package de.bord.festival;

import de.bord.festival.exception.*;
import de.bord.festival.helper.HelpClasses;
import de.bord.festival.models.Client;
import de.bord.festival.models.Event;
import de.bord.festival.repository.ClientRepository;
import de.bord.festival.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class FestivalApplication {

    @Autowired
    private static ClientRepository clientRepository;
    @Autowired
    private static EventRepository eventRepository;

    public FestivalApplication(ClientRepository clientRepository, EventRepository eventRepository) {
        this.clientRepository = clientRepository;
        this.eventRepository = eventRepository;
    }

    public static void main(String[] args) throws PriceLevelException, TimeDisorderException, DateDisorderException, MailException, ClientNameException {
        SpringApplication.run(FestivalApplication.class, args);

        HelpClasses helper = new HelpClasses();
        Event event1 = helper.getValidNDaysEvent(3);
        Event event2 = helper.getValidNDaysEvent1(4);
        Event event3 = helper.getValidNDaysEvent2(5);

        if(eventRepository.findAll().size() == 0) {
            eventRepository.save(event1);
            eventRepository.save(event2);
            eventRepository.save(event3);
        }

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
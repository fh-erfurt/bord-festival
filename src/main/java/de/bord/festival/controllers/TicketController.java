package de.bord.festival.controllers;

import de.bord.festival.controllers.help.HelpClasses;
import de.bord.festival.exception.*;
import de.bord.festival.models.*;
import de.bord.festival.repository.ClientRepository;
import de.bord.festival.repository.EventRepository;
import de.bord.festival.repository.PriceLevelRepository;
import de.bord.festival.ticket.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class TicketController {

    private Client c1;
    private Event event = null;
    private long eventId = -1;

    private final EventRepository eventRepository;
    @Autowired PriceLevelRepository priceLevelRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    public TicketController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }
////////////////// Event auswählen

    @GetMapping("user_menu")
    public String createEventOverview(ModelMap model) throws PriceLevelException, TimeDisorderException, DateDisorderException {


        List<Event> events = eventRepository.findAll();

        model.addAttribute("events", events);


        return "user_menu";
    }


    @GetMapping("/buy_ticket_user")
    public String chooseEvent( @RequestParam (value = "eventId", required = false) long eventId, ModelMap model){
        if(this.eventId == -1){
            this.eventId = eventId;
        }
        Event event1 =  eventRepository.findById(this.eventId);
        event = event1;
        model.addAttribute("theEvent", event1);

        return "buy_ticket_user";
    }

    @PostMapping("/addToBasket")
    public String addTicketToBasket(Type ticketType, ModelMap model) throws MailException, ClientNameException, TicketNotAvailableException {
     //  HelpClasses h1 = new HelpClasses();
   //  Client client1 = h1.exampleClient();
       // clientRepository.save(client1);
        List <Client> clients = clientRepository.findAll();
        Client client1 = clients.get(0);
       client1.addTicket(ticketType, event.getTicketManager());
       // client1.addTicket(ticketType, event.getTicketManager());

     clientRepository.save(client1);

      //  client1.addTicket(ticketType, event.getTicketManager());


        return "redirect:/buy_ticket_user?eventId=" +event.getId();
    }

    @PostMapping("/buy_ticket")
    public String buyTicket(ModelMap model)  {

        List <Client> clients = clientRepository.findAll();
        Client client1 = clients.get(0);
        try {
            event.sellTickets(client1);
            event.addClient(client1);
            eventRepository.save(event);
            return "redirect:/ticket_buy_ok?eventId=" +event.getId();
        }
        catch(TicketNotAvailableException e){
            return "redirect:/buy_ticket_user?eventId=" +event.getId();
        }

    }


}

package de.bord.festival.controllers;

import de.bord.festival.controllers.dataContainers.TicketCounter;
import de.bord.festival.exception.*;
import de.bord.festival.models.*;
import de.bord.festival.repository.ClientRepository;
import de.bord.festival.repository.EventRepository;
import de.bord.festival.repository.PriceLevelRepository;
import de.bord.festival.ticket.Type;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * The class controls the management of the shopping cart
 * and the processing of a ticket order
 */

@Controller
public class TicketController {


    private Client client;  // the client which is logged in

    private Client client2; // used for ticketBuyOk()

    private Event event = null;
    private long eventId = -1;
    private Long clientId = null;
    private Exception exception;
    private TicketCounter ticketCounter = null;

   ClientControllerAdvice clientControllerAdvice = new ClientControllerAdvice();



    private final EventRepository eventRepository;
    @Autowired PriceLevelRepository priceLevelRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    public TicketController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }


    @GetMapping("user_menu")
    public String createEventOverview(ModelMap model)  {
        model.addAttribute("title", "Menu");
        clientId = clientControllerAdvice.getClientId();

        client = clientRepository.findById(clientId.intValue());

        List<Event> events = eventRepository.findAll();

        model.addAttribute("events", events);
        model.addAttribute("title", "Menu");

       eventId = -1;
       ticketCounter = null;
       ticketCounter = new TicketCounter();
      // client.clearExpenditureBasket();
       //client.getExpenditureByPricesFromCart();
       clientRepository.save(client);


        return "user_menu";
    }


    @GetMapping("/buy_ticket_user")
    public String chooseEvent( @RequestParam (value = "eventId", required = false) long eventId, ModelMap model){

        model.addAttribute("title", "Buy ticket");

        if(this.eventId == -1) {
            this.eventId = eventId;
        }
        client = clientRepository.findById(clientId.intValue());
        client2 = null;
        Event event1 =  eventRepository.findById(this.eventId);
        event = event1;
        model.addAttribute("theEvent", event1);

        ticketCounter.setTicketCounter(client);

        model.addAttribute("ticketCounter", ticketCounter);
        model.addAttribute("client", client);


        return "buy_ticket_user";
    }

    @PostMapping("/addToBasket")
    public String addTicketToBasket(Type ticketType, ModelMap model) throws MailException, ClientNameException, TicketNotAvailableException, PriceLevelException {


     //  List <Client> clients = clientRepository.findAll();
    //    Client client1 = clients.get(0);

        if(!ticketCounter.toManyTickets()){
            client.addTicket(ticketType, event.getTicketManager());
            clientRepository.save(client);
        }

        return "redirect:/buy_ticket_user?eventId=" +eventId;
    }

    @PostMapping("/resetBasket")
    public String resetBasket(Type ticketType, ModelMap model) throws MailException, ClientNameException, TicketNotAvailableException {


      //  List <Client> clients = clientRepository.findAll();
  //      Client client1 = clients.get(0);

     client.clearCart();
     client.clearExpenditureBasket();
     clientRepository.save(client);
     ticketCounter = new TicketCounter();



        return "redirect:/buy_ticket_user?eventId=" +eventId;
    }

    @PostMapping("/buy_ticket")
    public String buyTicket(ModelMap model)  {

      //  List <Client> clients = clientRepository.findAll();
      //  Client client1 = clients.get(0);
       // client = client1;

        try {
            if(!ticketCounter.areNoTicketsInCart()){
                client2 = client;
                Hibernate.initialize(client);
                event.sellTickets(client);

                if(isClientNotInEventlist()){
                    event.addClient(client);
                    eventRepository.save(event);
                }
                else{
                    clientRepository.save(client);
                }


              //  client= client1;
                // model.addAttribute("client", client1);
                return "redirect:/ticket_buy_ok?eventId=" +eventId;
            }
            else{

                return "redirect:/buy_ticket_user?eventId=" +eventId;

            }

        }
        catch(TicketNotAvailableException e){
            exception = e;
            return "redirect:/ticket_buy_error?eventId=" +eventId;
        }

    }

    @GetMapping("/ticket_buy_ok")
    public String getTicketBuyOk(ModelMap model){
        model.addAttribute("client", client2);
        model.addAttribute("ticketCounter", ticketCounter);
        return "ticket_buy_ok";
    }



   @GetMapping("/ticket_buy_error")
    public String getTicketBuyError(ModelMap model){

        model.addAttribute("event", event);
        model.addAttribute("exception", exception);
        return "ticket_buy_error";
    }

    @GetMapping("/information_user")
    public String getInformationUser(ModelMap model){

       // List <Client> clients = clientRepository.findAll();
      //  Client client1 = clients.get(0);

      model.addAttribute("client", client);

        return "information_user";
    }

    public boolean isClientNotInEventlist(){
        for(Client client1 : this.event.getClients()){
            if(this.client.getId() == client1.getId()){
                return false;
            }

        }
        return true;
    }



}

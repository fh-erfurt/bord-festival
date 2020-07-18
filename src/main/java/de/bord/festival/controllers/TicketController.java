package de.bord.festival.controllers;

import de.bord.festival.controllers.help.HelpClasses;
import de.bord.festival.exception.*;
import de.bord.festival.models.*;
import de.bord.festival.repository.ClientRepository;
import de.bord.festival.repository.EventRepository;
import de.bord.festival.repository.PriceLevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class TicketController {

    private Event event = null;

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
    /*
    @RequestMapping(value = "buy_ticket", method= RequestMethod.POST)
    public String chooseEvent(@RequestParam(value = "eventId", required = true) Integer eventId, ModelMap model){
        List<Event> event1 = eventRepository.findAll();
        return "/buy_ticket";
    }
    */

    /*
    <tr th:each="Event : ${events}">
        <td th:text="${events.name}"></td>
        <td th:text="${events.address}"></td>
        <td th:text="*{events.id}"></td>

        <form action="#"  class="form" th:action="@{'/publish-post/'+${post.id}}" method="post">

    </tr>
    */
  ////////////////////////////




/*    @RequestMapping(value = "/buy_ticket", method= RequestMethod.POST)
    public String chooseEvent(@RequestParam(value = "eventId", required = true) Integer eventId,ModelMap model){
       Event theEvent = eventRepository.findById(eventId);
       model.addAttribute("theEvent", theEvent);
       PriceLevel thePricelevel = theEvent.getTheActualPricelevel();
       model.addAttribute("thePricelevel", thePricelevel);

*//*
<p th:text="'Campingticket: ' + ${theEvent.getTheActualPricelevel().getCampingTicketPrice()}"></p>
<p th:text="'Dayticket: ' + ${theEvent.getTheActualPricelevel().getDayTicketPrice()}"></p>
<p th:text="'Vipticket: ' +${theEvent.getTheActualPricelevel().getVipTicketPrice()}"></p>
*//*


        return "buy_ticket2";
    }*/


    @GetMapping("/buy_ticket_user")
    public String chooseEvent( @RequestParam long eventId, ModelMap model){

        Event event1 =  eventRepository.findById(eventId);
        event = event1;
        model.addAttribute("theEvent", event1);

        return "buy_ticket_user";
    }
/*
    @GetMapping("buy_ticket_user{eventId}")
    public String addToBasket(@PathVariable("eventId") Integer eventId, ModelMap model){


        Event event1 =  eventRepository.findById(eventId);
        model.addAttribute("theEvent", event1);

        return "buy_ticket_user";
    }
*/
    @PostMapping("addTicketToBasket")
    public void addTicketToBasket(@RequestParam Ticket.TicketType ticketType, ModelMap model) throws MailException, ClientNameException, TicketNotAvailableException {
        HelpClasses h1 = new HelpClasses();
       Client client1 = h1.exampleClient();
        clientRepository.save(client1);

        client1.addTicket(Ticket.TicketType.DAY, event.getTicketManager());
        client1.addTicket(ticketType, event.getTicketManager());

        clientRepository.save(client1);

        client1.addTicket(ticketType, event.getTicketManager());




    }


}

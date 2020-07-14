package de.bord.festival.controllers;

import de.bord.festival.controllers.help.HelpClasses;
import de.bord.festival.exception.DateDisorderException;
import de.bord.festival.exception.PriceLevelException;
import de.bord.festival.exception.TimeDisorderException;
import de.bord.festival.models.Event;
import de.bord.festival.models.PriceLevel;
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


    private final EventRepository eventRepository;
    @Autowired PriceLevelRepository priceLevelRepository;

    @Autowired
    public TicketController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }
////////////////// Event auswählen

    @GetMapping("buy_ticket")
    public String eventOverview(ModelMap model) throws PriceLevelException, TimeDisorderException, DateDisorderException {

        //eventRepository.save(new HelpClasses().getValidNDaysEvent(2));
        List<Event> events = eventRepository.findAll();
        //String[] flowers = new String[] { "Rose", "Lily", "Tulip", "Carnation", "Hyacinth" };
        model.addAttribute("events", events);


        return "buy_ticket";
    }
    @RequestMapping(value = "buy_ticket", method= RequestMethod.POST)
    public String chooseEvent(@RequestParam(value = "eventId", required = true) Integer eventId, ModelMap model){
        List<Event> event1 = eventRepository.findAll();
        return "/buy_ticket";
    }
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

    @GetMapping("/buy_ticket2")
    public String blaaa(ModelMap model){

        return "buy_ticket2";
    }

   // @PostMapping("/")
}

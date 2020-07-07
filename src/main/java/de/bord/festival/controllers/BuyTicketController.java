package de.bord.festival.controllers;

import de.bord.festival.models.Event;
import de.bord.festival.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class BuyTicketController {


    @Autowired EventRepository eventRepository;

////////////////// Event auswählen

    @GetMapping("/eventOverview")
    public String eventOverwiew(ModelMap model){

      //  List<Event> events = eventRepository.findAll();
        model.addAttribute("events", eventRepository.findAll());
        return "/eventOverview";
    }
    /*
    <tr th:each="Event : ${events}">
        <td th:text="${events.name}"></td>
        <td th:text="${events.address}"></td>
    </tr>
    */
  ////////////////////////////



    @GetMapping("/ticketOverview")
    public String ticketOverview(){

        return "/ticketOverview";
    }
}

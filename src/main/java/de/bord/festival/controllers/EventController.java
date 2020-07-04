package de.bord.festival.controllers;

import de.bord.festival.exception.DateDisorderException;
import de.bord.festival.exception.PriceLevelException;
import de.bord.festival.help.HelpClasses;
import de.bord.festival.models.Event;
import de.bord.festival.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
public class EventController {
    private final EventRepository eventRepository;

    @Autowired
    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping("/event")
    @ResponseBody
    public String getEvent(@RequestParam int id) throws DateDisorderException, PriceLevelException {
        HelpClasses helper=new HelpClasses();
        eventRepository.save(helper.getValidNDaysEvent(1));
        Event event=eventRepository.findById(id);
        if (event==null){
            return "No such event";
        }
        else{
            return "This is your event";
        }
    }
    /*
    * TODO: Validation for all members
    * */
    @PostMapping("/event")
    @ResponseBody
    public String createEvent(@Valid Event event, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "formEvent";
        }
        else{
            return "You have saved your event!";
        }
    }

    @GetMapping("/event_create")
    public String greetingForm(Model model) {
        model.addAttribute("event", new Event());
        return "event_create";
    }

    @PostMapping("/event_create")
    public String createEvent(@ModelAttribute Event event) {
        return "redirect:/event_create?success";
    }

    @PutMapping("/event")
    @ResponseBody
    public String updateEvent(@Valid Event event){
        return "You updated your event";
    }
}

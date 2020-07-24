package de.bord.festival.controllers;

import de.bord.festival.exception.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class PagesController {
    @GetMapping("/")
    public String index(Model model) throws BudgetOverflowException, TimeSlotCantBeFoundException, PriceLevelException, TimeDisorderException, DateDisorderException {
        model.addAttribute("title", "Home");

        /*HelpClasses helpClasses = new HelpClasses();
        Event event1 = helpClasses.getValidNDaysEvent2(1);*/
        /*eventRepository.save(event1);*/
        return "index";
    }
    @GetMapping("/error404")
    public String error(Model model) {
        model.addAttribute("title", "Error Page");
        return "error";
    }
}

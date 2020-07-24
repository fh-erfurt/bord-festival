package de.bord.festival.controllers;

import de.bord.festival.exception.*;
import de.bord.festival.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Container for all pages that have no certain mapping for its functionalities
 * Are used to add title or something else
 */
@Controller
public class PagesController {
    @Autowired
    EventRepository eventRepository;
    @GetMapping("/")
    public String index(Model model) throws BudgetOverflowException, TimeSlotCantBeFoundException, PriceLevelException, TimeDisorderException, DateDisorderException {
        model.addAttribute("title", "Home");

        /*HelpClasses helpClasses = new HelpClasses();
        Event event1 = helpClasses.getValidNDaysEvent2(1);
        eventRepository.save(event1);*/
        return "index";
    }
    @GetMapping("/error404")
    public String error(Model model) {
        model.addAttribute("title", "Error Page");
        return "error";
    }
    @GetMapping("/admin_menu")
    public String adminMenu(Model model) {
        model.addAttribute("title", "Menu");
        return "admin_menu";
    }
    @GetMapping("/user_menu")
    public String userMenu(Model model) {
        model.addAttribute("title", "Menu");
        return "user_menu";
    }
}

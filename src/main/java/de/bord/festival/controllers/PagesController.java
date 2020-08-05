package de.bord.festival.controllers;

import de.bord.festival.helper.HelpClasses;
import de.bord.festival.exception.*;
import de.bord.festival.models.Event;
import de.bord.festival.repository.ClientRepository;
import de.bord.festival.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Container for all pages that have no certain mapping for its functionalities
 * Are used to add title or something else
 */
@Controller
public class PagesController {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ClientControllerAdvice clientControllerAdvice = new ClientControllerAdvice();

    @GetMapping("/")
    public String index(Model model) throws BudgetOverflowException, TimeSlotCantBeFoundException, PriceLevelException, TimeDisorderException, DateDisorderException {
        model.addAttribute("title", "Home");

        /*HelpClasses helpClasses = new HelpClasses();
        Event event1 = helpClasses.getValidNDaysEvent2(5);
        event1.addStage(helpClasses.getStage());
        Event newEvent= eventRepository.save(event1);*/

        return "index";
    }

    @GetMapping("/error404")
    public String error404(Model model) {
        model.addAttribute("title", "Error Page");
        return "error404";
    }

    @GetMapping("/error403")
    public String error403(Model model) {
        model.addAttribute("title", "Error Page");

        return "error403";
    }

    @GetMapping("/admin_menu")
    public String adminMenu(Model model) {
        model.addAttribute("title", "Menu");
        return "admin_menu";
    }
}

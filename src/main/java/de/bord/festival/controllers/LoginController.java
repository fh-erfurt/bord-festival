package de.bord.festival.controllers;

import de.bord.festival.controllers.help.HelpClasses;
import de.bord.festival.exception.ClientNameException;
import de.bord.festival.exception.MailException;
import de.bord.festival.models.Address;
import de.bord.festival.models.Client;
import de.bord.festival.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@Controller
public class LoginController {

    @GetMapping("login")
    public String loginForm(){

        return "login";
    }

    @GetMapping(value = "/loginSuccess")
    public String currentClient(Authentication authentication) {

        return "redirect:/";
    }
}

package de.bord.festival.controllers;

import de.bord.festival.exception.ClientNameException;
import de.bord.festival.exception.MailException;
import de.bord.festival.models.Address;
import de.bord.festival.models.Client;
import de.bord.festival.models.Role;
import de.bord.festival.repository.ClientRepository;
import de.bord.festival.security.ClientDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.Collection;

@Controller
public class LoginController {

    ClientControllerAdvice clientControllerAdvice = new ClientControllerAdvice();

    @Autowired
    ClientRepository clientRepository;

    @GetMapping("login")
    public String loginForm(Model model) {

        model.addAttribute("title", "Log in");
        return "login";
    }

    @GetMapping(value = "/loginSuccess")
    public String currentClient(Model model) {
        model.addAttribute("title", "Log in");
        long clientId = clientControllerAdvice.getClientId();

        if (clientId != 0) {
            Client client = clientRepository.findById(clientId);
            if (client != null) {
                if (client.getRole() == Role.ADMIN) {
                    return "admin_menu";
                } else if (client.getRole() == Role.USER) {
                    return "user_menu";
                }
            }
        }

        return "redirect:/";
    }
}

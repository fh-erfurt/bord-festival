package de.bord.festival.controllers;

import de.bord.festival.models.Client;
import de.bord.festival.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class ClientControllerAdvice {

    @Autowired
    ClientRepository clientRepository;

    @ModelAttribute
    public void setCurrentClient(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null) {
            Object principal = authentication.getPrincipal();

            if(principal instanceof Client) {
                Client client = (Client)principal;
                Long clientId = client.getId();
                String clientMail = client.getMail();
                String role = client.getRole();

                model.addAttribute("client", client);
                model.addAttribute("clientId", clientId);
                model.addAttribute("clientMail", clientMail);
                model.addAttribute("role", role);
            }
        }
    }
}


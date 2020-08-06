package de.bord.festival.controllers;

import de.bord.festival.models.Client;
import de.bord.festival.models.Role;
import de.bord.festival.security.ClientDetails;
import de.bord.festival.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Collection;

/**
 * This controller is accessed every time a new View is rendered.
 *
 * It's purpose is to store information about the current user in the Model class and create
 * a convenient way to access the clientId in other controllers.
 */
@ControllerAdvice
public class ClientControllerAdvice {

    @Autowired
    ClientRepository clientRepository;

    /**
     * Casts the authentication principal of the current client into ClientDetails, making the relevant data
     * about the current session available. It the adds clientId, clientMail and authorities to model, making
     * them usable as attributes in every html file.
     * @param model
     */
    @ModelAttribute
    public void setCurrentClient(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof ClientDetails) {
                ClientDetails clientDetails = (ClientDetails) principal;
                Long clientId = clientDetails.getId();
                String clientMail = clientDetails.getUsername();
                Collection<? extends GrantedAuthority> authorities = clientDetails.getAuthorities();

                model.addAttribute("clientId", clientId);
                model.addAttribute("clientMail", clientMail);
                model.addAttribute("authorities", authorities);
            }
        }
    }

    /**
     * Help-function for convenient access of the Id of current client
     *
     * @return clientId
     */
    public long getClientId() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Object principal = auth.getPrincipal();
            if (principal instanceof ClientDetails) {
                ClientDetails clientDetails = (ClientDetails) principal;

                return clientDetails.getId();
            }
        }

        return 0;
    }
}


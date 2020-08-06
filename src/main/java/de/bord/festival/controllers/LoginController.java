package de.bord.festival.controllers;

import de.bord.festival.models.Client;
import de.bord.festival.models.Role;
import de.bord.festival.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * This controller handles the login page.
 *
 * Redirects client to the menu corresponding with his authorities
 */
@Controller
public class LoginController {

    ClientControllerAdvice clientControllerAdvice = new ClientControllerAdvice();

    @Autowired
    ClientRepository clientRepository;

    /**
     * Redirects client to login form.
     *
     * @param model
     * @return login
     */
    @GetMapping("login")
    public String loginForm(Model model) {

        model.addAttribute("title", "Log in");
        return "login";
    }

    /**
     * Redirects client on loginSuccess to either admin_menu or user_menu, depending on his role.
     *
     * @return admin_menu / user_menu
     */
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
                    return "redirect:/user_menu";
                }
            }
        }

        return "redirect:/";
    }
}

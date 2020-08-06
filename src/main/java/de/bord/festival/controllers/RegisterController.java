package de.bord.festival.controllers;

import de.bord.festival.models.Address;
import de.bord.festival.models.Client;
import de.bord.festival.models.Role;
import de.bord.festival.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

/**
 * This controller handles the signing up of new clients
 *
 * Only Users use the register function on our website, admin-accounts are generated manually
 * by developers, so every new account created by the register site is automatically a user.
 */
@Controller
public class RegisterController {

    private final ClientRepository clientRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Autowired
    RegisterController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * Redirects to register form.
     *
     * @param model
     * @return register
     */
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("title", "Register");
        model.addAttribute("client", new Client());
        model.addAttribute("address", new Address());

        return "register";
    }

    /**
     * Checks for errors in the input fields, checks for unique property of the mail-field,
     * saves client on successful signing up in the client repository and redirects user to
     * the index site.
     *
     * @param model
     * @param address
     * @param bindingResultAddress
     * @param client
     * @param bindingResultClient
     * @return index / register
     */
    @PostMapping("/register")
    public String register(Model model, @Valid Address address, BindingResult bindingResultAddress,
                           @Valid Client client, BindingResult bindingResultClient) {

        if (!bindingResultAddress.hasErrors() && !bindingResultClient.hasErrors()) {

            if(!clientRepository.findByMail(client.getMail()).isPresent()) {
                model.addAttribute("mailError", false);

                String passwordHash = passwordEncoder.encode(client.getPassword());
                client.setAddress(address);
                client.setPassword(passwordHash);
                client.setRole(Role.USER);
                clientRepository.save(client);
            }
            else {
                model.addAttribute("mailError", true);
                return "register";
            }
        }
        return "index";
    }
}

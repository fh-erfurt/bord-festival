package de.bord.festival.controllers;

import de.bord.festival.exception.ClientNameException;
import de.bord.festival.exception.MailException;
import de.bord.festival.models.Address;
import de.bord.festival.models.Client;
import de.bord.festival.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@Controller
public class RegisterController {
    private final ClientRepository clientRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Autowired
    RegisterController(ClientRepository clientRepository)
    {
        this.clientRepository = clientRepository;
    }

    @GetMapping("/register")
    public String registerForm(Model model)
    {
        model.addAttribute("client", new Client());
        model.addAttribute("address", new Address());

        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid Address address, BindingResult bindingResultAddress,
                                 @Valid Client client, BindingResult bindingResultClient)
    {
        if (!bindingResultAddress.hasErrors() && !bindingResultClient.hasErrors())
        {

            String passwordHash = passwordEncoder.encode(client.getPassword());

            client.setAddress(address);
            client.setPassword(passwordHash);
            client.setRole("USER");
            clientRepository.save(client);
        }
        // Client signing in himself is automatically a user
        return "user_menu";
    }
}

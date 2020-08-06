package de.bord.festival.security;

import de.bord.festival.exception.ClientNameException;
import de.bord.festival.models.Client;
import de.bord.festival.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * This implements the spring security UserDetailsService.
 *
 * Is used to retrieve client related data.
 */
@Service
public class ClientDetailsService implements UserDetailsService {

    @Autowired
    ClientRepository clientRepository;

    /**
     * Returns Client corresponding to mail.
     *
     * @param mail
     * @return Client
     */
    @Override
    public UserDetails loadUserByUsername(String mail) {
        Optional<Client> client = clientRepository.findByMail(mail);
        if(client == null) new ClientNameException("Client " + mail + " does not exist");

        return client.map(ClientDetails::new).get();
    }
}

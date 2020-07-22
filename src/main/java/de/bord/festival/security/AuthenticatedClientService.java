package de.bord.festival.security;

import de.bord.festival.models.Client;
import de.bord.festival.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticatedClientService implements UserDetailsService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String mail) {
        Client client = clientRepository.findByMail(mail);
        if (client == null) {
            throw new UsernameNotFoundException("The user " + mail + " does not exist");
        }
        return new AuthenticatedClient(client);
    }
}
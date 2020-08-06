package de.bord.festival.security;

import de.bord.festival.models.Client;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class implements the core spring security class UserDetails
 *
 * It stores relevant information about clients (Id, mail, password authorities) in a convenient way,
 * making the authentication principal of the current client castable into this class.
 */
public class ClientDetails implements UserDetails {
    private Long Id;
    private String mail;
    private String password;
    private List<GrantedAuthority> authorities;

    public ClientDetails(Client client) {
        this.Id = client.getId();
        this.mail = client.getMail();
        this.password = client.getPassword();
        this.authorities = Arrays.stream(client.getAuthorities().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public Long getId() {
        return this.Id;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.mail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

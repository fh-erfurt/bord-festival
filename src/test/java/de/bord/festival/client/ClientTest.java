package de.bord.festival.client;

import de.bord.festival.models.Address;
import de.bord.festival.exception.ClientNameException;
import de.bord.festival.exception.MailException;
import de.bord.festival.helper.HelpClasses;
import de.bord.festival.models.Client;
import de.bord.festival.models.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;


public class ClientTest {

    String firstname = "a";
    String lastname = "b";
    String mail = "ab@c.de";
    String password = "test123";
    Role role = Role.USER;
    int id = 999;


    HelpClasses helper = new HelpClasses();
    Address address = helper.getAddress();


    public ClientTest() throws ClientNameException, MailException {
    }
    @ParameterizedTest
    @ValueSource(strings = {" ", "!", "\"", "§", "$", "%", "&", "/", "(", ")", "=", "?", "´", "`", "*", "+", "'", "#", ";", ",", "_", "~", "@", "€", "[", "]", "{", "}"})
    void should_throw_exception_for_firstname_with_specialchars(String input) throws ClientNameException {
        assertThrows(ClientNameException.class, () -> {
            Client client = Client.getNewClient(input, lastname, mail, password, address, role);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "!", "\"", "§", "$", "%", "&", "/", "(", ")", "=", "?", "´", "`", "*", "+", "'", "#", ";", ",", "_", "~", "@", "€", "[", "]", "{", "}"})
    void should_throw_exception_for_lastname_with_specialchars(String input) throws ClientNameException {
        assertThrows(ClientNameException.class, () -> {
            Client client = Client.getNewClient(firstname, input, mail, password, address, role);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" })
    void should_throw_exception_for_firstname_with_numbers(String input) throws ClientNameException {
        assertThrows(ClientNameException.class, () -> {
            Client client = Client.getNewClient(input, lastname, mail, password, address, role);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" })
    void should_throw_exception_for_lastname_with_numbers(String input) throws ClientNameException {
        assertThrows(ClientNameException.class, () -> {
            Client client = Client.getNewClient(firstname, input, mail, password, address, role);
        });
    }

    @Test
    void should_throw_exception_for_firstname_empty() throws ClientNameException {
        String testname = "";

        assertThrows(ClientNameException.class, () -> {
            Client client = Client.getNewClient(testname, lastname, mail, password, address, role);
        });
    }

    @Test
    void should_throw_exception_for_lastname_empty() throws ClientNameException {
        String testname = "";

        assertThrows(ClientNameException.class, () -> {
            Client client = Client.getNewClient(firstname, testname, mail, password, address, role);
        });
    }

    @Test
    void should_throw_exception_for_firstname_51_characters_long() throws ClientNameException {
        String testname = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXY";

        assertThrows(ClientNameException.class, () -> {
            Client client = Client.getNewClient(testname, lastname, mail, password, address, role);
        });
    }

    @Test
    void should_throw_exception_for_lastname_51_characters_long() throws ClientNameException {
        String testname = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXY";

        assertThrows(ClientNameException.class, () -> {
            Client client = Client.getNewClient(firstname, testname, mail, password, address, role);
        });
    }

    @Test
    void should_throw_nothing_for_firstname_50_characters_long() {
        String testname = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWX";

        assertDoesNotThrow(() -> {
            Client client = Client.getNewClient(testname, lastname, mail, password, address, role);
        });
    }

    @Test
    void should_throw_nothing_for_lastname_50_characters_long() {
        String testname = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWX";

        assertDoesNotThrow(() -> {
            Client client = Client.getNewClient(firstname, testname, mail, password, address, role);
        });
    }

    @Test
    void should_throw_exception_for_mail_with_missing_at_symbol() throws MailException {
        String testmail = "testtest.de";
        assertThrows(MailException.class, () -> {
            Client client = Client.getNewClient(firstname, lastname, testmail, password, address, role);
        });
    }

    @Test
    void should_throw_exception_for_mail_with_missing_domain() throws MailException {
        String testmail = "test@test";
        assertThrows(MailException.class, () -> {
            Client client = Client.getNewClient(firstname, lastname, testmail, password, address, role);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = { "John..Doe@example.com", "john.doe@example..com", " johndoe@example.com", "johndoe@example.com ", "a\"b(c)d,e:f;gi[j\\k]l@example.com" })

    void should_throw_exception_for_invalid_mail(String input) throws MailException {
        assertThrows(MailException.class, () -> {
            Client client = Client.getNewClient(firstname, lastname, input, password, address, role);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = { "mail@example.com", "a@b.de", "firstname.lastname@example.com", "first.name+lastname@example.com", "\"very.unusual.@.unusual.com\"@example.com", "\"very.(),:;<>\".VERY.\"very@\"very\".unusual\"@strange.example.com", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789@abcdefghijklmnopqrstuvwxyz-ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.com", "\"\"(),:;<>@\"@example.com", "\"John.Doe\"@example.com", "firstname.lastname@dev.mail.example.com", "support@google.com"})
    void should_throw_nothing_for_valid_mails(String input) {
        assertDoesNotThrow(() -> {
            Client client = Client.getNewClient(firstname, lastname, input, password, address, role);
        });
    }
}

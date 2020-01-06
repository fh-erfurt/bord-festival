package de.bord.festival.client;

import de.bord.festival.address.Address;
import de.bord.festival.exception.ClientNameException;
import de.bord.festival.exception.MailException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;


public class ClientTest {

    String firstname = "a";
    String lastname = "b";
    String mail = "ab@c.de";
    int id = 999;
    Address address = new Address("Deutschland", "Jena", "Anger 1", "07745");

    public ClientTest() throws ClientNameException, MailException {
    }
    // TODO: Specialchars like é. See: "René"

    @ParameterizedTest
    @ValueSource(strings = {" ", "!", "\"", "§", "$", "%", "&", "/", "(", ")", "=", "?", "´", "`", "*", "+", "'", "#", ";", ",", "_", "~", "@", "€", "[", "]", "{", "}"})
    void should_throw_exception_for_firstname_with_specialchars(String input) throws ClientNameException {
        assertThrows(ClientNameException.class, () -> {
            Client client = new Client(input, lastname, mail, id, address);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "!", "\"", "§", "$", "%", "&", "/", "(", ")", "=", "?", "´", "`", "*", "+", "'", "#", ";", ",", "_", "~", "@", "€", "[", "]", "{", "}"})
    void should_throw_exception_for_lastname_with_specialchars(String input) throws ClientNameException {
        assertThrows(ClientNameException.class, () -> {
            Client client = new Client(firstname, input, mail, id, address);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" })
    void should_throw_exception_for_firstname_with_numbers(String input) throws ClientNameException {
        assertThrows(ClientNameException.class, () -> {
            Client client = new Client(input, lastname, mail, id, address);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" })
    void should_throw_exception_for_lastname_with_numbers(String input) throws ClientNameException {
        assertThrows(ClientNameException.class, () -> {
            Client client = new Client(firstname, input, mail, id, address);
        });
    }

    @Test
    void should_throw_exception_for_firstname_empty() throws ClientNameException {
        String testname = "";

        assertThrows(ClientNameException.class, () -> {
            Client client = new Client(testname, lastname, mail, id, address);
        });
    }

    @Test
    void should_throw_exception_for_lastname_empty() throws ClientNameException {
        String testname = "";

        assertThrows(ClientNameException.class, () -> {
            Client client = new Client(firstname, testname, mail, id, address);
        });
    }

    @Test
    void should_throw_exception_for_firstname_51_characters_long() throws ClientNameException {
        String testname = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXY";

        assertThrows(ClientNameException.class, () -> {
            Client client = new Client(testname, lastname, mail, id, address);
        });
    }

    @Test
    void should_throw_exception_for_lastname_51_characters_long() throws ClientNameException {
        String testname = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXY";

        assertThrows(ClientNameException.class, () -> {
            Client client = new Client(firstname, testname, mail, id, address);
        });
    }

    @Test
    void should_throw_nothing_for_firstname_50_characters_long() {
        String testname = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWX";

        assertDoesNotThrow(() -> {
            Client client = new Client(testname, lastname, mail, id, address);
        });
    }

    @Test
    void should_throw_nothing_for_lastname_50_characters_long() {
        String testname = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWX";

        assertDoesNotThrow(() -> {
            Client client = new Client(firstname, testname, mail, id, address);
        });
    }

    @Test
    void should_throw_exception_for_mail_with_missing_at_symbol() throws MailException {
        String testmail = "testtest.de";
        assertThrows(MailException.class, () -> {
            Client client = new Client(firstname, lastname, testmail, id, address);
        });
    }

    @Test
    void should_throw_exception_for_mail_with_missing_domain() throws MailException {
        String testmail = "test@test";
        assertThrows(MailException.class, () -> {
            Client client = new Client(firstname, lastname, testmail, id, address);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = { "John..Doe@example.com", "john.doe@example..com", " johndoe@example.com", "johndoe@example.com ", "john@doe@example.com", "a\"b(c)d,e:f;gi[j\\k]l@example.com" })
    void should_throw_exception_for_invalid_mail() throws MailException {
        String testmail = "test@test";
        assertThrows(MailException.class, () -> {
            Client client = new Client(firstname, lastname, testmail, id, address);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = { "mail@example.com", "a@b.de", "!#$%&'*+-/=?^_`{|}~@example.com", "firstname.lastname@example.com", "first.name+lastname@example.com", "\"very.unusual.@.unusual.com\"@example.com", "\"very.(),:;<>[]\\\".VERY.\\\"very@\\ \\\"very\\\".unusual\"@strange.example.com", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789@abcdefghijklmnopqrstuvwxyz-ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.com", "\"\"(),:;<>@[\\]\"@example.com", "\"John..Doe\"@example.com", "firstname.lastname@dev.mail.example.com", "support@google.com"})
    void should_throw_nothing_for_valid_mails(String input) {
        assertDoesNotThrow(() -> {
            Client client = new Client(firstname, lastname, input, id, address);
        });
    }
}

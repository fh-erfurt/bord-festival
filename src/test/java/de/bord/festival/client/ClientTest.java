package de.bord.festival.client;

import de.bord.festival.address.Address;
import de.bord.festival.exception.ClientNameException;
import de.bord.festival.exception.MailException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class ClientTest {

    String phoneNumber = "123";
    int id = 999;
    String firstname = "a";
    String lastname = "b";

    Address address = new Address("Deutschland", "Jena", "Anger 1", "07745");
    //Client client = new Client(firstname, lastname, phoneNumber, id, address);

    public ClientTest() throws ClientNameException, MailException {
    }

    @Test
    void should_throw_exception_for_name_with_specialchars() throws ClientNameException {
        String testname = "!";
        /*
        assertThrows(ClientNameException.class, () -> {
            client.nameCheck("serfsdfsdf");
        });

         */
    }

    @Test
    void should_return_true_for_valid_name() {
        String testname = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZäöüÄÖÜß";

        //assertEquals(ClientNameException.class, client.nameCheck(testname));
    }
}

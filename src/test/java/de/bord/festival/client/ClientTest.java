package de.bord.festival.client;

import de.bord.festival.address.Address;
import de.bord.festival.exception.BudgetException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class ClientTest {

    String phoneNumber = "123";
    int id = 999;

    Address address = new Address("Deutschland", "Jena", "Anger 1", "07745");

    @Test
    void should_throw_exception_for_name_with_specialchars() {
        /*
        String testname = "!";
        Client client = new Client(testname, phoneNumber, id, address);
        client.validateName();
        assertThrows(BudgetException, client.validateName());
         */
    }
}

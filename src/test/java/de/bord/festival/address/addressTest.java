package de.bord.festival.address;
import de.bord.festival.helper.HelpClasses;
import de.bord.festival.models.Address;
import de.bord.festival.models.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class addressTest {
    HelpClasses helper = new HelpClasses();
    Address address = helper.getAddress();

    @Test
    void should_return_true_for_address_object() {
        assertTrue(address.equals(this.address));
    }

    @Test
    void should_return_false_for_nonaddress_object() {
        Stage stage = helper.getStage();
        assertFalse(address.equals(stage));
    }

}

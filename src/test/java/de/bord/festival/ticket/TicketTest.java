package de.bord.festival.ticket;

import de.bord.festival.exception.PriceLevelException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TicketTest {
    @Test
    void should_return_50_for_level1_validPercentageOfSoldTicketsForPricelevel() throws PriceLevelException {

        PriceLevel level1 = new PriceLevel(20.00, 39.99, 54.99,
                70.00);
        PriceLevel level2 = new PriceLevel(30.00, 49.99, 64.99,
                50.00);
        PriceLevel level3 = new PriceLevel(40.00, 59.99, 74.99,
                60.00);
        ArrayList<PriceLevel> priceLevels = new ArrayList<>();
        priceLevels.add(level1);
        priceLevels.add(level2);
        priceLevels.add(level3);
        TicketManager ticketManager1 = new TicketManager(priceLevels, 3, 1000,20000,300);
        assertEquals(50, ticketManager1.getPriceLevel(0).getPercentageForPricelevel());
    }
    @Test
    void should_return_70_for_level1_validPercentageOfSoldTicketsForPricelevel() throws PriceLevelException {

        PriceLevel level1 = new PriceLevel(20.00, 39.99, 54.99,
                70.00);
        PriceLevel level2 = new PriceLevel(30.00, 49.99, 64.99,
                50.00);
        PriceLevel level3 = new PriceLevel(40.00, 59.99, 74.99,
                60.00);
        ArrayList<PriceLevel> priceLevels = new ArrayList<>();
        priceLevels.add(level1);
        priceLevels.add(level2);
        priceLevels.add(level3);
        TicketManager ticketManager1 = new TicketManager(priceLevels, 3, 1000,20000,300);
        assertEquals(70, ticketManager1.getPriceLevel(2).getPercentageForPricelevel());
    }

}

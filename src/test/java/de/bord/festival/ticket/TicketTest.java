package de.bord.festival.ticket;

import de.bord.festival.models.Client;
import de.bord.festival.exception.*;
import de.bord.festival.models.PriceLevel;
import de.bord.festival.models.Ticket;
import de.bord.festival.models.TicketManager;
import de.bord.festival.repository.PriceLevelRepository;
import de.bord.festival.repository.TicketManagerRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.bord.festival.help.HelpClasses;
import org.springframework.beans.factory.annotation.Autowired;

public class TicketTest {
    @Autowired
    PriceLevelRepository priceLevelRepository;

    @Autowired
    TicketManagerRepository ticketManagerRepository;

    @Test
    void should_return_50_for_level1_getPercentageForPriceLevel() throws PriceLevelException, PriceLevelNotAvailableException {

        // given 3 price levels for TicketManager1

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

        DayTicket dayTicket = new DayTicket("day test" , 50.00);
        CampingTicket campingTicket = new CampingTicket("camping test", 80.00 );
        VIPTicket vipTicket = new VIPTicket("vip test", 100.00);

        // when ( Collections.sort(priceLevels) in Constructor )
        TicketManager ticketManager1 = new TicketManager(priceLevels, 1000,20000,300,dayTicket,campingTicket,vipTicket);

        //then
        assertEquals(50, ticketManager1.getPercentageForPriceLevel(0));
    }
    @Test
    void should_return_70_for_level1_getPercentageForPriceLevel() throws PriceLevelException, PriceLevelNotAvailableException {

        // given 3 price levels for TicketManager1

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

        DayTicket dayTicket = new DayTicket( "day test", 40);
        CampingTicket campingTicket = new CampingTicket("camping test", 70.00);
        VIPTicket vipTicket = new VIPTicket("vip test", 99.99);

        // when ( Collections.sort(priceLevels) in Constructor )
        TicketManager ticketManager1 = new TicketManager(priceLevels, 1000,20000,300, dayTicket, campingTicket ,vipTicket);
        //then
        assertEquals(70, ticketManager1.getPercentageForPriceLevel(2));
    }

    @Test
    void should_return_Festival_2020_DayTicket_for_ticketManager1_setDescription() throws PriceLevelException {

        // given TicketManager with DayTicket dayTicket.description = "day test"
        HelpClasses helpClasses = new HelpClasses();
        TicketManager ticketManager1 = helpClasses.exampleTicketManager();

        // when
        ticketManager1.getTicket(Ticket.TicketType.DAY).setDescription("Festival 2020 DayTicket");

        // then
        assertEquals("Festival 2020 DayTicket", ticketManager1.getTicket(Ticket.TicketType.DAY).getDescription());

    }

    @Test
    void should_return_59_99_for_ticketManager1_campingTicket_getStdPrice_after_setPriceLevel_() throws PriceLevelException, PriceLevelNotAvailableException {

        // given
        HelpClasses helpClasses = new HelpClasses();
        TicketManager ticketManager1 = helpClasses.exampleTicketManager();

        // when ( pricelevel set from 0 to 1 )
        ticketManager1.setAutomaticPriceLevelChange(false);
        ticketManager1.setPriceLevel(1);

        // then
        assertEquals(59.99, ticketManager1.getTicket(Ticket.TicketType.CAMPING).getStdPrice());

    }
    @Test
    void should_return_52_49_for_ticketManager1_vipTicket_getStdPrice_after_setTicketPrices_in_Constructor() throws PriceLevelException{

        // given ( exampleTicketManager.vipTicket.stdPrice = 100.00 )
        HelpClasses helpClasses = new HelpClasses();
        //when
        TicketManager ticketManager1 = helpClasses.exampleTicketManager();

        // then
        assertEquals(52.49, ticketManager1.getTicket(Ticket.TicketType.VIP).getStdPrice());

    }

    @Test
    void should_return_182_47000000000003_for_ticketManager1_getIncomeTicketSales() throws PriceLevelException, MailException, ClientNameException, TicketNotAvailableException {
        // given ( exampleTicketManager.vipTicket.stdPrice = 100.00 )
        HelpClasses helpClasses = new HelpClasses();
        TicketManager ticketManager1 = helpClasses.exampleTicketManager();
        Client client1 = helpClasses.exampleClientWith4Tickets();

        // when
        ticketManager1.sellTickets(client1);

        // then
        assertEquals(182.47000000000003, ticketManager1.getIncomeTicketSales());

    }

    @Test
    void should_return_10_for_ticketManager1_getNumberOfDayTickets() throws PriceLevelException, MailException, ClientNameException, TicketNotAvailableException {

        HelpClasses helpClasses = new HelpClasses();
        TicketManager ticketManager1 = helpClasses.exampleTicketManager();
        Client client1 = helpClasses.exampleClientWith4Tickets();


        ticketManager1.sellTickets(client1);

        assertEquals(10, ticketManager1.getNumberOfDayTickets());

    }


    @Test
    void should_return_9_for_ticketManager1_getNumberOfDayTicketsLeft() throws PriceLevelException, MailException, ClientNameException, TicketNotAvailableException {
        // given ( exampleTicketManager.getNumberOfDayTickets = 10 )
        HelpClasses helpClasses = new HelpClasses();
        TicketManager ticketManager1 = helpClasses.exampleTicketManager();
        Client client1 = helpClasses.exampleClientWith4Tickets(); //1 * DayTicket in Cart

        // when
        ticketManager1.sellTickets(client1);

        // then
        assertEquals(9, ticketManager1.getNumberOfDayTicketsLeft());

    }

    @Test
    void should_return_true_for_ticketManager1_isAvailable() throws  MailException, PriceLevelException, ClientNameException, TicketNotAvailableException {
        // given ( exampleTicketManager.getNumberOfDayTickets = 2 )
        HelpClasses helpClasses = new HelpClasses();
        TicketManager ticketManager1 = helpClasses.exampleTicketManager();
        Client client1 = helpClasses.exampleClientWith4Tickets(); //1 * DayTicket in Cart

        // when
        ticketManager1.sellTickets(client1);

        // then
        assertEquals(true, ticketManager1.isAvailable(Ticket.TicketType.DAY, 1));

    }

    @Test
    void should_return_false_for_ticketManager1_isAvailable() throws MailException, PriceLevelException, ClientNameException, TicketNotAvailableException {
        // given ( example2TicketManager.getNumberOfDayTickets = 2 )
        HelpClasses helpClasses = new HelpClasses();
        TicketManager ticketManager1 = helpClasses.example2TicketManager();
        Client client1 = helpClasses.exampleClientWith4Tickets(); //1 * DayTicket in Cart
        client1.addTicket(Ticket.TicketType.DAY, ticketManager1);

        // when
        ticketManager1.sellTickets(client1);

        // then
        assertEquals(false, ticketManager1.isAvailable(Ticket.TicketType.DAY, 2));

    }

    @Test
    void should_return_1_for_ticketManager1_getNumberOfSoldDayTickets() throws PriceLevelException, MailException, ClientNameException, TicketNotAvailableException {
        // given ( exampleTicketManager.getNumberOfDayTickets = 10 )
        HelpClasses helpClasses = new HelpClasses();
        TicketManager ticketManager1 = helpClasses.exampleTicketManager();
        Client client1 = helpClasses.exampleClientWith4Tickets(); // 1 * DayTicket in Cart

        // when
        ticketManager1.sellTickets(client1);

        // then
        assertEquals(1, ticketManager1.getNumberOfSoldDayTickets());

    }

    @Test
    void should_return_2_for_ticketManager1_getNumberOfSoldCampingTickets() throws PriceLevelException, MailException, ClientNameException, TicketNotAvailableException {
        // given ( exampleTicketManager.getNumberOfCampingTickets = 20 )
        HelpClasses helpClasses = new HelpClasses();
        TicketManager ticketManager1 = helpClasses.exampleTicketManager();
        Client client1 = helpClasses.exampleClientWith4Tickets(); // 2 * CampingTicket in Cart

        // when
        ticketManager1.sellTickets(client1);

        // then
        assertEquals(2, ticketManager1.getNumberOfSoldCampingTickets());

    }

    @Test
    void should_return_18_for_ticketManager1_getNumberOfCampingTicketsLeft() throws PriceLevelException, MailException, ClientNameException, TicketNotAvailableException {
        // given ( exampleTicketManager.getNumberOfCampingTickets = 20 )
        HelpClasses helpClasses = new HelpClasses();
        TicketManager ticketManager1 = helpClasses.exampleTicketManager();
        Client client1 = helpClasses.exampleClientWith4Tickets(); // 2 * CampingTicket in Cart

        // when
        ticketManager1.sellTickets(client1);

        // then
        assertEquals(18, ticketManager1.getNumberOfCampingTicketsLeft());

    }

    @Test
    void should_return_4_for_client1_getCartSize() throws PriceLevelException, MailException, ClientNameException, TicketNotAvailableException {

        HelpClasses helpClasses = new HelpClasses();

        Client client1 = helpClasses.exampleClientWith4Tickets();

        assertEquals(4, client1.getCartSize());

    }

    @Test
    void should_return_0_for_client1_getCartSize() throws PriceLevelException, MailException, ClientNameException, TicketNotAvailableException {
        // given
        HelpClasses helpClasses = new HelpClasses();
        TicketManager ticketManager1 = helpClasses.exampleTicketManager();
        Client client1 = helpClasses.exampleClientWith4Tickets();

        // when
        ticketManager1.sellTickets(client1);

        // then
        assertEquals(0, client1.getCartSize());

    }

    @Test
    void should_return_182_47000000000003_for_client1_getExpenditure() throws PriceLevelException, MailException, ClientNameException, TicketNotAvailableException {
        // given
        HelpClasses helpClasses = new HelpClasses();
        TicketManager ticketManager1 = helpClasses.exampleTicketManager();
        Client client1 = helpClasses.exampleClientWith4Tickets();

        // when
        ticketManager1.sellTickets(client1);

        // then
        assertEquals(182.47000000000003, client1.getExpenditure());

    }

    @Test
    void should_return_4_for_client1_get_ticketsSize() throws PriceLevelException, MailException, ClientNameException, TicketNotAvailableException {
        // given
        HelpClasses helpClasses = new HelpClasses();
        TicketManager ticketManager1 = helpClasses.exampleTicketManager();
        Client client1 = helpClasses.exampleClientWith4Tickets();

        // when
        ticketManager1.sellTickets(client1);

        // then
        assertEquals(4, client1.getInventorySize());

    }

    @Test
    void should_return_0_for_ticketManager1_getActualPriceLevelIndex() throws PriceLevelException, MailException, ClientNameException, TicketNotAvailableException {
        // given
        HelpClasses helpClasses = new HelpClasses();
        TicketManager ticketManager1 = helpClasses.exampleTicketManager();
        Client client1 = helpClasses.exampleClient();
        client1.addTicket(Ticket.TicketType.DAY,ticketManager1);

        ticketManager1.sellTickets(client1);

        assertEquals(0, ticketManager1.getActualPriceLevelIndex());

    }


    @Test
    void should_return_40_for_ticketManager1_totalNumberOfSoldTicketsInPercent() throws PriceLevelException, MailException, ClientNameException, TicketNotAvailableException {
        // given
        HelpClasses helpClasses = new HelpClasses();
        TicketManager ticketManager1 = helpClasses.example2TicketManager();
        Client client1 = helpClasses.exampleClient();


        client1.addTicket(Ticket.TicketType.CAMPING,ticketManager1);
        client1.addTicket(Ticket.TicketType.CAMPING,ticketManager1);
        client1.addTicket(Ticket.TicketType.VIP,ticketManager1);
        client1.addTicket(Ticket.TicketType.DAY,ticketManager1);

        //when
        ticketManager1.sellTickets(client1);

        //then
        assertEquals(40, ticketManager1.totalNumberOfSoldTicketsInPercent());

    }


    @Test
    void should_return_2_for_ticketManager1_getActualPriceLevelIndex() throws PriceLevelException, MailException, ClientNameException, TicketNotAvailableException {
        // given
        HelpClasses helpClasses = new HelpClasses();
        TicketManager ticketManager1 = helpClasses.example2TicketManager();
        Client client1 = helpClasses.exampleClient();

        client1.addTicket(Ticket.TicketType.CAMPING,ticketManager1);
        client1.addTicket(Ticket.TicketType.CAMPING,ticketManager1);
        client1.addTicket(Ticket.TicketType.CAMPING,ticketManager1);
        client1.addTicket(Ticket.TicketType.CAMPING,ticketManager1);
        client1.addTicket(Ticket.TicketType.CAMPING,ticketManager1);
        client1.addTicket(Ticket.TicketType.CAMPING,ticketManager1);

        //when
        ticketManager1.sellTickets(client1);

        client1.addTicket(Ticket.TicketType.VIP,ticketManager1);
        client1.addTicket(Ticket.TicketType.DAY,ticketManager1);

        ticketManager1.sellTickets(client1);

        double test = ticketManager1.totalNumberOfSoldTicketsInPercent();

        //then
        assertEquals(2, ticketManager1.getActualPriceLevelIndex());

    }


    @Test
    void should_return_1_for_ticketManager1_getActualPriceLevelIndex() throws PriceLevelException, MailException, ClientNameException, TicketNotAvailableException {
        // given
        HelpClasses helpClasses = new HelpClasses();
        TicketManager ticketManager1 = helpClasses.example2TicketManager();
        Client client1 = helpClasses.exampleClient();

        client1.addTicket(Ticket.TicketType.CAMPING,ticketManager1);
        client1.addTicket(Ticket.TicketType.CAMPING,ticketManager1);
        client1.addTicket(Ticket.TicketType.CAMPING,ticketManager1);
        client1.addTicket(Ticket.TicketType.CAMPING,ticketManager1);
        client1.addTicket(Ticket.TicketType.CAMPING,ticketManager1);
        client1.addTicket(Ticket.TicketType.CAMPING,ticketManager1);


        //when
        ticketManager1.sellTickets(client1);

        //then
        assertEquals(1, ticketManager1.getActualPriceLevelIndex());

    }

    @Test
    void should_return_0_for_ticketManager1_getActualPriceLevelIndexAfter_4_Sells() throws PriceLevelException, MailException, ClientNameException, TicketNotAvailableException {
        // given
        HelpClasses helpClasses = new HelpClasses();
        TicketManager ticketManager1 = helpClasses.example2TicketManager();
        Client client1 = helpClasses.exampleClient();

        client1.addTicket(Ticket.TicketType.CAMPING,ticketManager1);
        client1.addTicket(Ticket.TicketType.CAMPING,ticketManager1);


        //when
        ticketManager1.sellTickets(client1);
        client1.addTicket(Ticket.TicketType.DAY,ticketManager1);
        ticketManager1.sellTickets(client1);
        client1.addTicket(Ticket.TicketType.DAY,ticketManager1);
        ticketManager1.sellTickets(client1);
        client1.addTicket(Ticket.TicketType.VIP,ticketManager1);
        ticketManager1.sellTickets(client1);

        //then
        assertEquals(0, ticketManager1.getActualPriceLevelIndex());

    }

    @Test
    void should_return_0_for_ticketManager1_two_same_pricelevels_getActualPriceLevelIndex() throws PriceLevelException, MailException, ClientNameException, TicketNotAvailableException {
        // given
        HelpClasses helpClasses = new HelpClasses();
        TicketManager ticketManager1 = helpClasses.example3TicketManager();
        Client client1 = helpClasses.exampleClient();

        client1.addTicket(Ticket.TicketType.CAMPING,ticketManager1);

        //when
        ticketManager1.sellTickets(client1);

        //then
        assertEquals(0, ticketManager1.getActualPriceLevelIndex());

    }

    @Test
    void should_return_1_for_ticketManager1_two_same_pricelevels_getActualPriceLevelIndex() throws PriceLevelException, MailException, ClientNameException, TicketNotAvailableException {
        // given
        HelpClasses helpClasses = new HelpClasses();
        TicketManager ticketManager1 = helpClasses.example3TicketManager();
        Client client1 = helpClasses.exampleClient();

        client1.addTicket(Ticket.TicketType.CAMPING,ticketManager1);
        client1.addTicket(Ticket.TicketType.CAMPING,ticketManager1);

        //when
        ticketManager1.sellTickets(client1);


        //then
        assertEquals(1, ticketManager1.getActualPriceLevelIndex());

    }


}

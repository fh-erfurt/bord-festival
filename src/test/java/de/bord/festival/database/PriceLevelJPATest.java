package de.bord.festival.database;

import de.bord.festival.helper.HelpClasses;
import de.bord.festival.exception.*;
import de.bord.festival.models.*;
import de.bord.festival.repository.ClientRepository;
import de.bord.festival.repository.PriceLevelRepository;
import de.bord.festival.repository.TicketManagerRepository;
import de.bord.festival.models.CampingTicket;
import de.bord.festival.models.DayTicket;
import de.bord.festival.ticket.Type;
import de.bord.festival.models.VIPTicket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;



@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PriceLevelJPATest {

    @Autowired
    PriceLevelRepository priceLevelRepository;

    @Autowired
    TicketManagerRepository ticketManagerRepository;

    @Autowired
    ClientRepository clientRepository;

    public TicketManager ticketManager;

    public HelpClasses helpClasses;

    @BeforeEach
    void initialize() throws PriceLevelException {
        helpClasses = new HelpClasses();
        ticketManager = helpClasses.exampleTicketManager();
    }

    @Test
    void should_return_20_for_databasePriceLevel_getDayTicketPrice() throws PriceLevelException {
        //given
        priceLevelRepository.save(new PriceLevel(20.00, 39.99, 54.99,
                70.00));
        //when
        PriceLevel databasePriceLevel = priceLevelRepository.findById(1);
        //then
        assertEquals(20.00, databasePriceLevel.getDayTicketPrice());

    }

    @Transactional
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
        CampingTicket campingTicket = new CampingTicket( "camping test", 80.00 );
        VIPTicket vipTicket = new VIPTicket("vip test", 100.00);

        // when ( Collections.sort(priceLevels) in Constructor )
        TicketManager ticketManager1 = new TicketManager(priceLevels, 1000,20000,300,dayTicket,campingTicket,vipTicket);

        ticketManagerRepository.save(ticketManager1);
        TicketManager databaseTicketManager = ticketManagerRepository.findById(1);

        //then
        assertEquals(50, databaseTicketManager.getPercentageForPriceLevel(0));
    }


    @Test
    void should_return_10_for_ticketManager_getNumberOfDayTicketsLeft(){
        //given
        ticketManagerRepository.save(ticketManager);
        //when
        TicketManager databaseTicketManager = ticketManagerRepository.findById(1);
        //then
        assertEquals(10, databaseTicketManager.getNumberOfDayTicketsLeft());
    }

    @Transactional
    @Test
    void should_return_Festival_2020_DayTicket_for_ticketManager1_getNewTicket() throws PriceLevelException {
        // given TicketManager with DayTicket dayTicket.description = "day test"
        HelpClasses helpClasses = new HelpClasses();
        TicketManager ticketManager1 = helpClasses.exampleTicketManager();

        // when
        ticketManager1.getTicket(Type.DAY).setDescription("Festival 2020 DayTicket");
        ticketManagerRepository.save(ticketManager1);

        TicketManager databaseTicketManager = ticketManagerRepository.findById(1);

        // then
        assertEquals("Festival 2020 DayTicket", databaseTicketManager.getNewTicket(Type.DAY).getDescription());
    }

    @Test
    void should_return_30_for_order1_getDayTicket_getStdprice() throws TicketNotAvailableException, MailException, ClientNameException, PriceLevelException, TimeDisorderException, DateDisorderException {
        // given
        HelpClasses h1 = new HelpClasses();
        Client client1 = h1.exampleClient();

        Event event = h1.getValidNDaysEvent(2);

        //when
        client1.addTicket(Type.DAY, event.getTicketManager());
        event.sellTickets(client1);

        // then
        assertEquals(30.00, client1.getOrders_().get(0).getDayTickets().get(0).getStdPrice());
    }

    @Test
    void should_return_52_49_for_order2_getVIPTicket_getStdprice() throws TicketNotAvailableException, MailException, ClientNameException, PriceLevelException, TimeDisorderException, DateDisorderException {
        // given
        HelpClasses h1 = new HelpClasses();
        Client client1 = h1.exampleClient();

        Event event = h1.getValidNDaysEvent(2);

        //when
        client1.addTicket(Type.DAY, event.getTicketManager());
        event.sellTickets(client1);
        client1.addTicket(Type.VIP, event.getTicketManager());
        event.sellTickets(client1);

        // then
        assertEquals(52.49, client1.getOrders_().get(1).getVipTickets().get(0).getStdPrice());
    }

    @Test
    void should_return_3_for_client1_getOrders_size() throws TicketNotAvailableException, MailException, ClientNameException, PriceLevelException, TimeDisorderException, DateDisorderException {
        // given
        HelpClasses h1 = new HelpClasses();
        Client client1 = h1.exampleClient();

        Event event = h1.getValidNDaysEvent(2);

        //when
        client1.addTicket(Type.DAY, event.getTicketManager());
        event.sellTickets(client1);
        client1.addTicket(Type.CAMPING, event.getTicketManager());
        client1.addTicket(Type.DAY, event.getTicketManager());
        event.sellTickets(client1);
        client1.addTicket(Type.VIP, event.getTicketManager());
        event.sellTickets(client1);

        // then
        assertEquals(3, client1.getOrders_().size());
    }
}

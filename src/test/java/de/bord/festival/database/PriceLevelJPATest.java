package de.bord.festival.database;

import de.bord.festival.exception.*;
import de.bord.festival.helper.HelpClasses;
import de.bord.festival.models.*;
import de.bord.festival.repository.ClientRepository;
import de.bord.festival.repository.PriceLevelRepository;
import de.bord.festival.repository.TicketManagerRepository;
import de.bord.festival.ticket.CampingTicket;
import de.bord.festival.ticket.DayTicket;
import de.bord.festival.ticket.Type;
import de.bord.festival.ticket.VIPTicket;
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

    // public PriceLevel priceLevel;

    @Autowired
    PriceLevelRepository priceLevelRepository;

    @Autowired
    TicketManagerRepository ticketManagerRepository;

    @Autowired
    ClientRepository clientRepository;


    public TicketManager ticketManager;

    public HelpClasses helpClasses;



    public Ticket TicketRepository;
    public CampingTicket campingTicket;

/*
    public Client exampleClient;


    AddressRepository addressRepository;
    Address address;
*/


    @BeforeEach
    void initialize() throws PriceLevelException, MailException, ClientNameException, TicketNotAvailableException {


        helpClasses = new HelpClasses();
        ticketManager = helpClasses.exampleTicketManager();

    }

    @Test
    void priceLevelTest() throws PriceLevelException {


        priceLevelRepository.save(new PriceLevel(20.00, 39.99, 54.99,
                70.00));

        PriceLevel databasePriceLevel = priceLevelRepository.findById(1);
        assertEquals(20.00, databasePriceLevel.getDayTicketPrice());
        assertEquals(54.99, databasePriceLevel.getVipTicketPrice());

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
    void ticketManagerTest(){

        ticketManagerRepository.save(ticketManager);
        TicketManager databaseTicketManager = ticketManagerRepository.findById(1);
        assertEquals(10, databaseTicketManager.getNumberOfDayTicketsLeft());
        assertEquals(10, databaseTicketManager.getNumberOfDayTicketsLeft());
    }

    @Transactional
    @Test
    void should_return_Festival_2020_DayTicket_for_ticketManager1_setDescription() throws PriceLevelException {

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
    void clientTestAndSellTest() throws TicketNotAvailableException, MailException, ClientNameException, PriceLevelException, TimeDisorderException, DateDisorderException {
      HelpClasses h1 = new HelpClasses();
       Client client1 = h1.exampleClient();
        clientRepository.save(client1);
        Event event = h1.getValidNDaysEvent(2);

        client1.addTicket(Type.DAY, event.getTicketManager());
      //client1.addTicket(ticketType, event.getTicketManager());
        clientRepository.save(client1);
        clientRepository.save(client1);
/*
        client1.addTicket(ticketType, event.getTicketManager());
        assertEquals(6, databaseTicketManager.getNumberOfDayTicketsLeft());
        assertEquals(0, exampleClient.getCartSize());
        assertEquals(182.47000000000003, databaseClient.getExpenditure());
        assertEquals(182.47000000000003, databaseClient.getExpenditure());
 */
    }

}

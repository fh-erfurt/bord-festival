package de.bord.festival.database;

import de.bord.festival.exception.*;
import de.bord.festival.help.HelpClasses;
import de.bord.festival.models.PriceLevel;
import de.bord.festival.models.Ticket;
import de.bord.festival.models.TicketManager;
import de.bord.festival.repository.PriceLevelRepository;
import de.bord.festival.repository.TicketManagerRepository;
import de.bord.festival.ticket.CampingTicket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


//@DataJpaTest
@SpringBootTest
public class PriceLevelJPATest {

    // public PriceLevel priceLevel;

    @Autowired
    PriceLevelRepository priceLevelRepository;

    @Autowired
    TicketManagerRepository ticketManagerRepository;
/*
    @Autowired
    ClientRepository clientRepository;

*/
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

        priceLevelRepository.save(level1);
        priceLevelRepository.save(level2);
        priceLevelRepository.save(level3);


        DayTicket dayTicket = new DayTicket(Ticket.TicketType.DAY,"day test" , 50.00);
        CampingTicket campingTicket = new CampingTicket(Ticket.TicketType.CAMPING, "camping test", 80.00 );
        VIPTicket vipTicket = new VIPTicket(Ticket.TicketType.VIP, "vip test", 100.00);


        // when ( Collections.sort(priceLevels) in Constructor )
        TicketManager ticketManager1 = new TicketManager(priceLevels, 1000,20000,300,dayTicket,campingTicket,vipTicket);

        ticketManagerRepository.save(ticketManager1);
        TicketManager databaseTicketManager = ticketManagerRepository.findById(4);

        //then
        assertEquals(50, databaseTicketManager.getPercentageForPriceLevel(0));
    }


    @Test
    void ticketManagerTest(){
        //  priceLevelRepository.create(priceLevel);
        //  PriceLevel databasePriceLevel = priceLevelRepository.findOne(this.priceLevel);
        ticketManagerRepository.save(ticketManager);
        TicketManager databaseTicketManager = ticketManagerRepository.findById(1);
        assertEquals(10, databaseTicketManager.getNumberOfDayTicketsLeft());
        assertEquals(10, databaseTicketManager.getNumberOfDayTicketsLeft());
    }
/*
    @Test
    void clientTestAndSellTest() throws TicketNotAvailableException {
        //addressRepository.create(address);
        ticketManagerRepository.save(ticketManager);
        TicketManager databaseTicketManager = ticketManagerRepository.findById(1);
        clientRepository.save(exampleClient);
        Client databaseClient= clientRepository.findById(1);
        assertEquals(10, databaseTicketManager.getNumberOfDayTicketsLeft());
        assertEquals(4, exampleClient.getCartSize());
        ticketManager.sellTickets(exampleClient);
        assertEquals(6, databaseTicketManager.getNumberOfDayTicketsLeft());
        assertEquals(0, exampleClient.getCartSize());
        assertEquals(182.47000000000003, databaseClient.getExpenditure());
        assertEquals(182.47000000000003, databaseClient.getExpenditure());
    }
*/
}

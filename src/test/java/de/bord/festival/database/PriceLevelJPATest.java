//      package de.bord.festival.database;
//
//      import de.bord.festival.exception.ClientNameException;
//      import de.bord.festival.exception.MailException;
//      import de.bord.festival.exception.PriceLevelException;
//      import de.bord.festival.exception.TicketNotAvailableException;
//      import de.bord.festival.help.HelpClasses;
//      import de.bord.festival.eventManagement.Address;
//      import de.bord.festival.client.Client;
//      import de.bord.festival.repository.PriceLevelRepository;
//      import de.bord.festival.ticket.*;
//      import de.bord.festival.repository.AddressRepository;
//      import de.bord.festival.repository.ClientRepository;
//      import de.bord.festival.repository.TicketManagerRepository;
//      import org.junit.jupiter.api.BeforeEach;
//      import org.junit.jupiter.api.Test;
//      import org.springframework.beans.factory.annotation.Autowired;
//      import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//      import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//      import org.springframework.boot.test.context.SpringBootTest;
//      import org.springframework.context.annotation.ComponentScan;
//      import org.springframework.context.annotation.Configuration;
//
//      import static org.junit.jupiter.api.Assertions.assertEquals;
//
//
//
//      //@DataJpaTest
//      @SpringBootTest
//      public class PriceLevelJPATest {
//        // public PriceLevel priceLevel;
//
//         @Autowired
//          PriceLevelRepository priceLevelRepository;
//
//         @Autowired
//         TicketManagerRepository ticketManagerRepository;
//
//
//         public TicketManager ticketManager;
//        // public TicketManagerRepository ticketManagerRepository;
//         public HelpClasses helpClasses;
//
//
//
//         public Ticket TicketRepository;
//         public CampingTicket campingTicket;
//
//
//         public Client exampleClient;
//         public ClientRepository clientRepository;
//
//          AddressRepository addressRepository;
//          Address address;
//
//          @BeforeEach
//          void initialize() throws PriceLevelException, MailException, ClientNameException, TicketNotAvailableException {
//
//
//              helpClasses = new HelpClasses();
//              ticketManager = helpClasses.exampleTicketManager();
//
//              /*
//              ticketManager = helpClasses.exampleTicketManager();
//              ticketManagerRepository = new TicketManagerRepository();
//
//
//              clientRepository = new ClientRepository();
//
//              address = new Address("Germany", "Berlin", "Plumb", "2211");
//              this.addressRepository = new AddressRepository();
//
//             CampingTicket campingTicket = new CampingTicket(TicketType.CAMPING, "test", 0.0);
//             VIPTicket vIPTicket = new VIPTicket(TicketType.VIP, "test", 0.0);
//             DayTicket dayTicket = new DayTicket(TicketType.DAY, "test", 0.0);
//      */
//          }
//
//          @Test
//          void priceLevelTest() throws PriceLevelException {
//
//
//              priceLevelRepository.save(new PriceLevel(20.00, 39.99, 54.99,
//                      70.00));
//
//              PriceLevel databasePriceLevel = priceLevelRepository.findById(1);
//              assertEquals(20.00, databasePriceLevel.getDayTicketPrice());
//              assertEquals(54.99, databasePriceLevel.getVipTicketPrice());
//
//          }
//
//
//
//          @Test
//          void ticketManagerTest(){
//            //  priceLevelRepository.create(priceLevel);
//            //  PriceLevel databasePriceLevel = priceLevelRepository.findOne(this.priceLevel);
//              ticketManagerRepository.save(ticketManager);
//              TicketManager databaseTicketManager = ticketManagerRepository.findById(1);
//              assertEquals(10, databaseTicketManager.getNumberOfDayTicketsLeft());
//              assertEquals(10, databaseTicketManager.getNumberOfDayTicketsLeft());
//          }
//      /*
//          @Test
//          void clientTestAndSellTest() throws TicketNotAvailableException {
//              addressRepository.create(address);
//              ticketManagerRepository.create(ticketManager);
//              TicketManager databaseTicketManager = ticketManagerRepository.findOne(this.ticketManager);
//              clientRepository.create(exampleClient);
//              Client databaseClient= clientRepository.findOne(this.exampleClient);
//              assertEquals(10, databaseTicketManager.getNumberOfDayTicketsLeft());
//              assertEquals(4, exampleClient.getCartSize());
//              ticketManager.sellTickets(exampleClient);
//              assertEquals(6, databaseTicketManager.getNumberOfDayTicketsLeft());
//              assertEquals(0, exampleClient.getCartSize());
//              assertEquals(182.47000000000003, databaseClient.getExpenditure());
//              assertEquals(182.47000000000003, databaseClient.getExpenditure());
//          }
//
//      */
//      }
//
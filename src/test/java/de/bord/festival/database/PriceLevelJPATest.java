package de.bord.festival.database;

import de.bord.festival.exception.ClientNameException;
import de.bord.festival.exception.MailException;
import de.bord.festival.exception.PriceLevelException;
import de.bord.festival.exception.TicketNotAvailableException;
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

import static org.junit.jupiter.api.Assertions.assertEquals;


//@DataJpaTest
@SpringBootTest
public class PriceLevelJPATest {

    @Autowired
    PriceLevelRepository priceLevelRepository;

    @Autowired
    TicketManagerRepository ticketManagerRepository;


    public TicketManager ticketManager;
    // public TicketManagerRepository ticketManagerRepository;
    public HelpClasses helpClasses;


    public Ticket TicketRepository;
    public CampingTicket campingTicket;



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


}

package de.bord.festival.help;

import de.bord.festival.address.Address;
import de.bord.festival.band.Band;
import de.bord.festival.band.EventInfo;
import de.bord.festival.client.Client;
import de.bord.festival.eventManagement.Event;
import de.bord.festival.eventManagement.LineUp;
import de.bord.festival.exception.*;
import de.bord.festival.stageManagement.Stage;
import de.bord.festival.ticket.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class HelpClasses {
    public Band getBand() {
        return new Band(1, "GOD IS AN ASTRONAUT", "920", 500);

    }

    public LineUp getLineUp(LocalDate startDate, LocalDate endDate) throws DateException, PriceLevelException{
        Stage stage = getStage();
        return new LineUp(startDate, endDate, stage, getValidNDaysEvent(1));

    }

    public Band getBand(String name, double priceProEvent) {
        return new Band(5, name, "911", priceProEvent);

    }

    public Stage getStage() {

        return new Stage(1, "stage1", 300);

    }

    public Event getValidNDaysEvent(int numberOfDays) throws DateException, PriceLevelException {

        return new Event(1, LocalDate.of(2018, 01, 01),
                LocalDate.of(2018, 01, numberOfDays), "Bord", 2019, 1000,
                getStage(),exampleTicketManager(), getAddress());


    }
    public Address getAddress(){
        return new Address("Germany", "Berlin", "Nordwez 1", "8803");
    }

    public LineUp exampleLineUp() throws TimeException, DateException, PriceLevelException {
        //lineUp for 3 days with 2 stages and 4 bands. Each band plays 5 hours
        LineUp lineUp = getLineUp(LocalDate.of(2020, 3, 1),
                LocalDate.of(2020, 3, 3));
        lineUp.addStage(getStage());


        Band band1 = getBand("band1", 40);
        Band band2 = getBand("band2", 40);
        Band band3 = getBand("band3", 40);
        Band band4 = getBand("band4", 40);
        lineUp.addBand(band1, 300);
        lineUp.addBand(band2, 300);
        lineUp.addBand(band3, 300);
        lineUp.addBand(band4, 300);
        return lineUp;
    }

    public Band getBandWith3EventPlays(){
        Band band =getBand();
        EventInfo eventInfo = new EventInfo(LocalTime.of(12,00), getStage());
        eventInfo.setDate(LocalDate.of(2020, 12,12));
        EventInfo eventInfo1 = new EventInfo(LocalTime.of(12,00), getStage());
        eventInfo1.setDate(LocalDate.of(2020, 11,12));
        EventInfo eventInfo2 = new EventInfo(LocalTime.of(12,00), getStage());
        eventInfo2.setDate(LocalDate.of(2020, 11,12));

        band.addEventInfo(eventInfo);
        band.addEventInfo(eventInfo1);
        band.addEventInfo(eventInfo2);
        return band;
    }

    public TicketManager exampleTicketManager() throws PriceLevelException {
        PriceLevel p1 = new PriceLevel(20.00, 39.99, 54.99,
                70.00);
        PriceLevel p2 = new PriceLevel(30.00, 49.99, 52.49,
                50.00);
        PriceLevel p3 = new PriceLevel(40.00, 59.99, 74.99,
                60.00);
        ArrayList<PriceLevel> priceLevels = new ArrayList<>();
        priceLevels.add(p1);
        priceLevels.add(p2);
        priceLevels.add(p3);

        DayTicket dayTicket = new DayTicket(Ticket.TicketType.DAY, 0, "day test", true, 01,"01.01.2020");
        CampingTicket campingTicket = new CampingTicket(Ticket.TicketType.CAMPING, 1000, "camping test", true, 02 );
        VIPTicket vipTicket = new VIPTicket(Ticket.TicketType.VIP, 5000, "vip test", true, 100.00);

        return new TicketManager(priceLevels, 3, 10,20,30, dayTicket, campingTicket, vipTicket);
    }

    public TicketManager example2TicketManager() throws PriceLevelException {
        PriceLevel p1 = new PriceLevel(20.00, 39.99, 54.99,
                70.00);
        PriceLevel p2 = new PriceLevel(30.00, 49.99, 52.49,
                50.00);
        PriceLevel p3 = new PriceLevel(40.00, 59.99, 74.99,
                60.00);
        ArrayList<PriceLevel> priceLevels = new ArrayList<>();
        priceLevels.add(p1);
        priceLevels.add(p2);
        priceLevels.add(p3);

        DayTicket dayTicket = new DayTicket(Ticket.TicketType.DAY, 0, "day test", true, 01,"01.01.2020");
        CampingTicket campingTicket = new CampingTicket(Ticket.TicketType.CAMPING, 1000, "camping test", true, 02 );
        VIPTicket vipTicket = new VIPTicket(Ticket.TicketType.VIP, 5000, "vip test", true, 100.00);

        return new TicketManager(priceLevels, 3, 2,6,2, dayTicket, campingTicket, vipTicket);
    }

    public Client exampleClientWith4Tickets() throws MailException, ClientNameException, PriceLevelException, TicketException {
        Client client = exampleClient();
        TicketManager t1 =  exampleTicketManager();

        client.addTicket(Ticket.TicketType.DAY, t1);
        client.addTicket(Ticket.TicketType.CAMPING, t1);
        client.addTicket(Ticket.TicketType.VIP, t1);
        client.addTicket(Ticket.TicketType.CAMPING, t1);

        return client;
    }

    public Client exampleClient() throws MailException, ClientNameException {
       return new Client("Max", "Muster","max@test.de",1,getAddress());
    }

}

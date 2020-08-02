package de.bord.festival.controllers.help;

import de.bord.festival.exception.*;
import de.bord.festival.models.*;
import de.bord.festival.ticket.CampingTicket;
import de.bord.festival.ticket.DayTicket;
import de.bord.festival.ticket.Type;
import de.bord.festival.ticket.VIPTicket;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class HelpClasses {
    public Band getBand() {
        return new Band("GOD IS AN ASTRONAUT", "920", 500,200);

    }

    public LineUp getLineUp(LocalDate startDate, LocalDate endDate) throws DateDisorderException, PriceLevelException, TimeDisorderException {
        Stage stage = getStage();
        return new LineUp(LocalTime.of(10, 30), LocalTime.of(23, 59), 30, startDate, endDate, stage, getValidNDaysEvent(1));

    }

    public Band getBand(String name, double priceProEvent) {
        return new Band(name, "911", priceProEvent, 200);

    }

    public Stage getStage() {

        return new Stage(1,"stage1");

    }
    public Stage getStage(int id) {

        return new Stage(id,"stage1");

    }
    public Stage getStage(int id, String name) {

        return new Stage(id,name);

    }

    public Event getValidNDaysEvent(int numberOfDays) throws DateDisorderException, PriceLevelException, TimeDisorderException {

        return Event.getNewEvent(LocalTime.of(10, 30), LocalTime.of(23, 59), 30, LocalDate.of(2018, 01, 01),
                LocalDate.of(2018, 01, numberOfDays), "Bord", BigDecimal.valueOf(2019),
                getStage(),exampleTicketManager(), getAddress());


    }
    public Event getValidNDaysEvent1(int numberOfDays) throws DateDisorderException, PriceLevelException, TimeDisorderException {

        return Event.getNewEvent(LocalTime.of(10, 30), LocalTime.of(23, 59), 30, LocalDate.of(2020, 01, 01),
                LocalDate.of(2020, 01, numberOfDays), "Bord", BigDecimal.valueOf(2019),
                getStage(),exampleTicketManager(), getAddress());


    }
    public Event getValidNDaysEvent2(int numberOfDays) throws DateDisorderException, PriceLevelException, TimeDisorderException {

        return Event.getNewEvent(LocalTime.of(10, 30), LocalTime.of(23, 59), 30, LocalDate.of(2025, 01, 01),
                LocalDate.of(2025, 01, numberOfDays), "Bord", BigDecimal.valueOf(2019),
                getStage(),exampleTicketManager(), getAddress());


    }
    public Address getAddress(){
        return new Address("Germany", "Berlin", "Nordwez 1", "8803");
    }

    public LineUp exampleLineUp() throws TimeSlotCantBeFoundException, DateDisorderException, PriceLevelException, TimeDisorderException {
        //lineUp for 3 days with 2 stages and 4 bands. Each band plays 5 hours
        LineUp lineUp = getLineUp(LocalDate.of(2020, 3, 1),
                LocalDate.of(2020, 3, 3));
        lineUp.addStage(getStage());


        Band band1 = getBand("band1", 40);
        Band band2 = getBand("band2", 40);
        Band band3 = getBand("band3", 40);
        Band band4 = getBand("band4", 40);
        lineUp.addBand(band1);
        lineUp.addBand(band2);
        lineUp.addBand(band3);
        lineUp.addBand(band4);
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

        DayTicket dayTicket = new DayTicket("Sommerfest:Erfurt:2020-08-20:day:Tag", 30.99);
        CampingTicket campingTicket = new CampingTicket("Sommerfest:Erfurt:2020-08-20:day:Camp", 80.00);
        VIPTicket vipTicket = new VIPTicket("Sommerfest:Erfurt:2020-08-20:day:VIP", 101.99);

        return new TicketManager(priceLevels, 10,20,30, dayTicket, campingTicket, vipTicket);
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

        DayTicket dayTicket = new DayTicket("day test", 30.99);
        CampingTicket campingTicket = new CampingTicket("camping test", 80.00);
        VIPTicket vipTicket = new VIPTicket("vip test", 101.99);

        return new TicketManager(priceLevels, 2,6,2, dayTicket, campingTicket, vipTicket);
    }

    public TicketManager example3TicketManager() throws PriceLevelException {
        PriceLevel p1 = new PriceLevel(20.00, 39.99, 54.99,
                10.00);
        PriceLevel p2 = new PriceLevel(30.00, 49.99, 52.49,
                10.00);
        PriceLevel p3 = new PriceLevel(40.00, 59.99, 74.99,
                60.00);
        ArrayList<PriceLevel> priceLevels = new ArrayList<>();
        priceLevels.add(p1);
        priceLevels.add(p2);
        priceLevels.add(p3);

        DayTicket dayTicket = new DayTicket("day test", 30.99);
        CampingTicket campingTicket = new CampingTicket("camping test", 80.00);
        VIPTicket vipTicket = new VIPTicket("vip test", 101.99);

        return new TicketManager(priceLevels, 2,6,2, dayTicket, campingTicket, vipTicket);
    }

    public Client exampleClientWith4Tickets() throws MailException, ClientNameException, PriceLevelException, TicketNotAvailableException {
        Client client = exampleClient();
        TicketManager ticketManager1 =  exampleTicketManager();

        client.addTicket(Type.DAY, ticketManager1);
        client.addTicket(Type.CAMPING, ticketManager1);
        client.addTicket(Type.VIP, ticketManager1);
        client.addTicket(Type.CAMPING, ticketManager1);

        return client;
    }

    public Client exampleClient() throws MailException, ClientNameException {
        return Client.getNewClient("Max", "Muster","max@test.de", "pass123", getAddress(), "USER");
    }

}

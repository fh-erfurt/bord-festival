package de.bord.festival.eventManagement;

import de.bord.festival.band.Band;
import de.bord.festival.band.EventInfo;
import de.bord.festival.client.Client;
import de.bord.festival.exception.*;
import de.bord.festival.stageManagement.Stage;

import java.time.LocalDateTime;

public interface IEvent {

    boolean addStage(Stage stage);
    boolean removeStage(int id);
    EventInfo addBand(Band band, long minutesOnStage) throws BudgetOverflowException, TimeSlotCantBeFoundException;
    boolean removeBand(Band band);
    boolean removeBand(Band band, LocalDateTime dateAndTime);
    void addToTheActualCosts(double amount);
    boolean sellTickets(Client client) throws TicketManagerException, TicketNotAvailableException;//ticketmanager
    boolean setPriceLevel(int index) throws PriceLevelNotAvailableException;
}

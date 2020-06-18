package de.bord.festival.eventManagement;

import de.bord.festival.models.Band;
import de.bord.festival.models.EventInfo;
import de.bord.festival.models.Client;
import de.bord.festival.exception.BudgetOverflowException;
import de.bord.festival.exception.TimeSlotCantBeFoundException;
import de.bord.festival.exception.*;
import de.bord.festival.models.Stage;

import java.time.LocalDateTime;

public interface IEvent {

    boolean addStage(Stage stage);
    boolean removeStage(int id);
    EventInfo addBand(Band band, long minutesOnStage) throws BudgetOverflowException, TimeSlotCantBeFoundException;
    boolean removeBand(Band band);
    boolean removeBand(Band band, LocalDateTime dateAndTime);
    void addToTheActualCosts(double amount);
    void sellTickets(Client client) throws  TicketNotAvailableException;//ticketmanager
    boolean setPriceLevel(int index) throws PriceLevelNotAvailableException;
}

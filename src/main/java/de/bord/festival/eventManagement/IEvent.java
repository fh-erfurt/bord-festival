package de.bord.festival.eventManagement;

import de.bord.festival.band.Band;
import de.bord.festival.band.EventInfo;
import de.bord.festival.client.Client;
import de.bord.festival.exception.BudgetOverflowException;
import de.bord.festival.exception.TicketManagerException;
import de.bord.festival.exception.TimeSlotCantBeFoundException;
import de.bord.festival.stageManagement.Stage;

import java.time.LocalDateTime;

public interface IEvent {

    public boolean addStage(Stage stage);

    public boolean removeStage(int id);

    public EventInfo addBand(Band band, long minutesOnStage) throws BudgetOverflowException, TimeSlotCantBeFoundException;

    public boolean removeBand(Band band);

    public boolean removeBand(Band band, LocalDateTime dateAndTime);

    public void addToTheActualCosts(double amount);

    public boolean sellTickets(Client client) throws TicketManagerException;

    public boolean setPriceLevel(int index);
}

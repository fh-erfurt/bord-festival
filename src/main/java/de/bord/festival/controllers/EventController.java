package de.bord.festival.controllers;

import de.bord.festival.controllers.dataContainers.BandTimeSlotContainer;
import de.bord.festival.controllers.dataContainers.DateTimeContainer;
import de.bord.festival.controllers.dataContainers.StageIdContainer;
import de.bord.festival.controllers.dataContainers.TicketManagerContainer;
import de.bord.festival.controllers.help.HelpClasses;
import de.bord.festival.exception.*;
import de.bord.festival.models.*;
import de.bord.festival.repository.BandRepository;
import de.bord.festival.repository.ClientRepository;
import de.bord.festival.repository.EventRepository;
import de.bord.festival.repository.StageRepository;
import de.bord.festival.ticket.CampingTicket;
import de.bord.festival.ticket.DayTicket;
import de.bord.festival.ticket.Type;
import de.bord.festival.ticket.VIPTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Controller class to manage admin sector
 * Is responsible for:
 * Create event- with ticket manager, lineup and programs
 * Update event- only fields that do not break event complex structure: dates, times, price
 * levels are can not be changed
 * Show events-  list of events with link to information and update possebility
 * Update event program:
 * add band from all timeslots of program
 * remove band only from one timeslot
 * add stage
 * remove stage
 */
@Controller
public class EventController {
    private final EventRepository eventRepository;
    private final BandRepository bandRepository;
    private final StageRepository stageRepository;

    @Autowired
    public EventController(StageRepository stageRepository, EventRepository eventRepository, BandRepository bandRepository) {
        this.eventRepository = eventRepository;
        this.bandRepository = bandRepository;
        this.stageRepository = stageRepository;
    }


    /**
     * Mapping for showing create event or update event form
     *
     * @param eventId required only if goal is to update event
     * @return event_form contains same form for update and create event
     */
    @GetMapping("event")
    public String eventForm(@RequestParam(required = false) String eventId, Model model) {
        if (eventId == null) {
            model.addAttribute("tmk", new TicketManagerContainer());
            model.addAttribute("event", new Event());
            model.addAttribute("dateTimeContainer", new DateTimeContainer());
            model.addAttribute("address", new Address());
            model.addAttribute("stage", new Stage());
            model.addAttribute("title", "Create event");
            model.addAttribute("newEvent", true);
            model.addAttribute("eventInFuture", true);
        } else {
            if (!isLong(eventId)) {
                return "error404";
            }

            long eventIdLong = Long.parseLong(eventId);
            Event event = eventRepository.findById(eventIdLong);

            if (event == null) {
                return "error404";
            } else {
                boolean result = setExistingEvent(model, event);
                if (!result) {
                    return "error404";
                }

                if (event.getStartDate().isAfter(LocalDate.now())) {
                    model.addAttribute("eventInFuture", true);
                    model.addAttribute("title", "Update event \"" + event.getName() + "\"");
                } else {
                    model.addAttribute("eventInFuture", false);
                    model.addAttribute("title", "View event \"" + event.getName() + "\"");
                }

                model.addAttribute("newEvent", false);
            }
        }
        return "event_form";

    }

    /**
     * Mapping for sent form to create or update the event
     * <p>
     * All input parameter are used to fill event constructor with necessary objects and fields
     * all binding results are used to validate these fields
     *
     * @param eventId to update existing event
     * @return program file, if new event was created
     * event_form in the case event was updates
     */
    @PostMapping("/event")
    public String createEvent(@Valid Event event, BindingResult bindingResultEvent,
                              @Valid DateTimeContainer dateTimeContainer, BindingResult bindingResultDateTimeContainer,
                              @Valid Address address, BindingResult bindingResultAddress,
                              @Valid @ModelAttribute("tmk") TicketManagerContainer tmk, BindingResult bindingResultTmk,
                              @Valid Stage stage, BindingResult bindingResultStage, Model model, @RequestParam(required = false) String eventId) {
        model.addAttribute("eventInFuture", true);

        if (noErrors(bindingResultEvent, bindingResultDateTimeContainer,
                bindingResultAddress, bindingResultTmk, bindingResultStage)) {

            //get all values for Event constructor
            LocalTime startTime = dateTimeContainer.getStartTime();
            LocalTime endTime = dateTimeContainer.getEndTime();
            LocalDate startDate = dateTimeContainer.getStartDate();
            LocalDate endDate = dateTimeContainer.getEndDate();
            long breakBetweenTwoBands = dateTimeContainer.getBreakBetweenTwoBands();
            String name = event.getName();
            BigDecimal budget = event.getBudget();

            //get all values for ticket manager constructor
            List<PriceLevel> priceLevels = tmk.getPriceLevels();
            int numberOfDayTickets = tmk.getNumberOfDayTickets();
            int numberOfCampingTickets = tmk.getNumberOfCampingTickets();
            int numberOfVipTickets = tmk.getNumberOfVipTickets();

            String ticketDescriptionPattern = name + ':' + address.getCity() + ':' + startDate.toString() + ':';
            DayTicket dayTicket = new DayTicket(ticketDescriptionPattern + "day:" + tmk.getDayTicketDescription(), 0);


            CampingTicket campingTicket = new CampingTicket(ticketDescriptionPattern + "camping:" + tmk.getCampingTicketDescription(), 0);
            VIPTicket vipTicket = new VIPTicket(ticketDescriptionPattern + "vip:" + tmk.getVipTicketDescription(), 0);
            TicketManager ticketManager = new TicketManager(priceLevels, numberOfDayTickets,
                    numberOfCampingTickets, numberOfVipTickets, dayTicket, campingTicket, vipTicket);
            try {
                //create event normally
                Event newEvent = Event.getNewEvent(startTime, endTime, breakBetweenTwoBands, startDate,
                        endDate, name, budget, stage, ticketManager, address);
                //update event case
                if (isEventIdValid(eventId)) {
                    return updateEvent(model, newEvent, eventId, bindingResultDateTimeContainer);
                } else {
                    eventRepository.save(newEvent);
                    model.addAttribute("title", "Create event");
                    model.addAttribute("newEvent", true);
                    return "redirect:/program?successCreate&eventId=" + newEvent.getId();
                }

            } catch (DateDisorderException e) {
                bindingResultDateTimeContainer.rejectValue("startDate", "error.dateTimeContainer", e.getMessage());
            } catch (TimeDisorderException e) {
                bindingResultDateTimeContainer.rejectValue("startTime", "error.dateTimeContainer", e.getMessage());
            }

        }
        //update case
        if (isEventIdValid(eventId)) {
            event.setId(Long.parseLong(eventId));
            model.addAttribute("event", event);
        }
        //create case
        return "event_form";

    }

    /**
     * Mapping shows list of all existing events
     */
    @GetMapping("events")
    public String getEvents(Model model) {


        List<Event> events = eventRepository.findAll();
        Collections.sort(events, Comparator.comparing(Event::getStartDate));
        Collections.reverse(events);
        model.addAttribute("events", events);
        model.addAttribute("title", "Event Overview");

        return "events";
    }

    /**
     * Mapping shows events program with bands, stages, times and dates
     *
     * @param eventId to show program of certain event
     * @return program, if eventId is valid, otherwise error404
     */
    @GetMapping("program")
    public String showProgram(@RequestParam String eventId, Model model) {
        model.addAttribute("title", "Program");


        if (!isEventIdValid(eventId)) {
            return "error404";
        }
        long eventIdLong = Long.parseLong(eventId);
        Event event1 = eventRepository.findById(eventIdLong);
        fillModelWithAttributesForProgram(new Band(), event1, model, new BandTimeSlotContainer(), new Stage(), new StageIdContainer());

        return "program";
    }

    /**
     * Mapping adds band to the program of certain event
     *
     * @return program with added timeslot otherwise error404
     */
    @PostMapping("band_add")
    public String addBand(@Valid Band band, BindingResult bindingResult,
                          @RequestParam String eventId,
                          Model model) {

        if (!isEventIdValid(eventId)) {
            return "error404";
        }
        long eventIdLong = Long.parseLong(eventId);
        Event event = eventRepository.findById(eventIdLong);
        //check if band values are right
        if (bindingResult.hasErrors()) {
            fillModelWithAttributesForProgram(band, event, model, new BandTimeSlotContainer(), new Stage(), new StageIdContainer());
            return "program";
        }
        return addBand(event, band, model, bindingResult);
    }


    /**
     * Model adds stage to the event program
     *
     * @return program with added new stage, otherwise error404
     */
    @PostMapping("stage_add")
    public String addStage(@Valid Stage stage,
                           BindingResult bindingResult,
                           String eventId,
                           Model model) {

        if (!isEventIdValid(eventId)) {
            return "error404";
        }
        long eventIdLong = Long.parseLong(eventId);
        Event event = eventRepository.findById(eventIdLong);
        event.sortPrograms();

        //check if stage values are right
        if (bindingResult.hasErrors()) {
            fillModelWithAttributesForProgram(new Band(), event, model, new BandTimeSlotContainer(), stage, new StageIdContainer());
            return "program";
        }

        boolean stageAdded = event.addStage(stage);
        if (stageAdded) {
            eventRepository.save(event);
            return "redirect:/program?successAddStage&eventId=" + event.getId();
        } else {
            bindingResult.rejectValue("identifier", "error.stage", "Stage with this identifier already exists");
            fillModelWithAttributesForProgram(new Band(), event, model, new BandTimeSlotContainer(), stage, new StageIdContainer());
            return "program";
        }


    }

    /**
     * Removes band from event program, if it exists
     * Case 1: band without timeslot: band will be removed from all timeslots
     * Case 2: band with a timeslot: band will be removed only from this timeslot
     *
     * @return program with removed band
     */
    @PostMapping("band_remove")
    public String removeBand(@Valid BandTimeSlotContainer bandTimeSlotContainer, BindingResult bindingResult,
                             Model model, String eventId) {
        if (!isEventIdValid(eventId)) {
            return "error404";
        }

        long eventIdLong = Long.parseLong(eventId);
        Event event = eventRepository.findById(eventIdLong);

        String bandId = bandTimeSlotContainer.getBandId();
        //check if band id was not manipulated
        if (!isLong(bandId)) {
            return "error404";
        }
        //check if date time is set
        if (bindingResult.hasErrors()) {
            fillModelWithAttributesForProgram(new Band(), event, model, bandTimeSlotContainer, new Stage(), new StageIdContainer());
            return "program";
        }
        Band band = bandRepository.findById(Long.parseLong(bandId));
        //check if band exists in database
        if (band == null) {
            bindingResult.rejectValue("bandId", "error.bandTimeSlotContainer", "there is no band with this name");
            fillModelWithAttributesForProgram(new Band(), event, model, bandTimeSlotContainer, new Stage(), new StageIdContainer());
            return "program";
        }

        return removeBand(event, model, bandTimeSlotContainer, band, bindingResult);
    }

    /**
     * Mapping removes stage from event program, if on this stage are no timeslots
     *
     * @return program with removed stage
     */
    @PostMapping("stage_remove")
    public String removeStage(@Valid StageIdContainer stageIdContainer, BindingResult bindingResult,
                              Model model, String eventId) {

        if (!isEventIdValid(eventId)) {
            return "error404";
        }

        long eventIdLong = Long.parseLong(eventId);
        Event event = eventRepository.findById(eventIdLong);

        String stageId = stageIdContainer.getStageId();

        boolean removed;
        if (!isLong(stageId)) {
            return "error404";
        }

        Stage stage = stageRepository.findById(Long.parseLong(stageId));
        removed = event.removeStage(stage.getIdentifier());

        if (removed) {
            eventRepository.save(event);
            model.addAttribute("programs", event.getPrograms());
            return "redirect:/program?successRemoveStage&eventId=" + event.getId();
        } else {
            bindingResult.rejectValue("stageId", "error.stageIdContainer", "This stage can not be deleted");
            fillModelWithAttributesForProgram(new Band(), event, model, new BandTimeSlotContainer(), new Stage(), stageIdContainer);
            return "program";
        }
    }

    void fillModelWithAttributesForProgram(Band band, Event event, Model model, BandTimeSlotContainer bandTimeSlotContainer, Stage stage, StageIdContainer stageIdContainer) {
        model.addAttribute("dayPrograms", event.getPrograms());
        model.addAttribute("bands", event.getBands());
        model.addAttribute("stages", event.getStages());
        model.addAttribute("band", band);
        model.addAttribute("eventId", String.valueOf(event.getId()));
        model.addAttribute("bandTimeSlotContainer", bandTimeSlotContainer);
        model.addAttribute("stage", stage);
        model.addAttribute("stageIdContainer", stageIdContainer);


    }

    private String updateEvent(Model model, Event newEvent, String eventId, BindingResult bindingResultDateTimeContainer) {
        //only future events can be updated
        if (newEvent.getStartDate().isBefore(LocalDate.now())) {
            bindingResultDateTimeContainer.rejectValue("mainErrorContainer", "error.dateTimeContainer", "Event of the past can not be updated");
            return "event_form";
        }
        Event event = eventRepository.findById(Long.parseLong(eventId));

        event.setName(newEvent.getName());
        event.getAddress().update(newEvent.getAddress());

        event.getTicketManager().update(newEvent.getTicketManager());
        eventRepository.save(event);

        setExistingEvent(model, event);
        model.addAttribute("newEvent", false);
        model.addAttribute("title", "Update event \"" + event.getName() + "\"");
        return "redirect:/event?successUpdate&eventId=" + event.getId();
    }

    /**
     * adds band and save event if there are no other exceptions
     * returns success query parameter if there are no exceptions
     * otherwise returns exception massages
     */
    String addBand(Event event, Band band, Model model, BindingResult bindingResult) {

        try {
            event.addBand(band);
            eventRepository.save(event);
            model.addAttribute("programs", event.getPrograms());
            return "redirect:/program?successAddBand&eventId=" + event.getId();

        } catch (BudgetOverflowException e) {
            bindingResult.rejectValue("pricePerEvent", "error.band", e.getMessage());
        } catch (TimeSlotCantBeFoundException e) {
            bindingResult.rejectValue("minutesOnStage", "error.band", e.getMessage());
        } finally {
            fillModelWithAttributesForProgram(band, event, model, new BandTimeSlotContainer(), new Stage(), new StageIdContainer());
        }
        return "program";
    }

    boolean setExistingEvent(Model model, Event event) {
        try {
            DateTimeContainer dtc = new DateTimeContainer();

            dtc.setStartDate(event.getStartDate());
            dtc.setEndDate(event.getEndDate());
            dtc.setStartTime(event.getStartTime());
            dtc.setEndTime(event.getEndTime());
            dtc.setBreakBetweenTwoBands(event.getBreakBetweenTwoBandsInMinutes());

            TicketManagerContainer tmc = new TicketManagerContainer();

            tmc.setNumberOfDayTickets(event.getNumberOfDayTickets());
            tmc.setNumberOfCampingTickets(event.getNumberOfCampingTickets());
            tmc.setNumberOfVipTickets(event.getNumberOfVipTickets());
            tmc.setDayTicketDescription(event.getTicket(Type.DAY).getDescription().split(":")[4]);
            tmc.setCampingTicketDescription(event.getTicket(Type.CAMPING).getDescription().split(":")[4]);
            tmc.setVipTicketDescription(event.getTicket(Type.VIP).getDescription().split(":")[4]);
            tmc.setPriceLevels(event.getPriceLevelsForEvent());

            Address address = event.getAddress();
            Stage stage = event.getFirstStage();

            model.addAttribute("event", event);
            model.addAttribute("dateTimeContainer", dtc);
            model.addAttribute("address", address);
            model.addAttribute("tmk", tmc);
            model.addAttribute("stage", stage);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    String removeBand(Event event, Model model, BandTimeSlotContainer bandTimeSlotContainer, Band band, BindingResult bindingResult) {

        boolean removed = true;

        if (isTimeSlotSet(bandTimeSlotContainer)) {
            //check if user didn't choose standard date as yyy-dd-mm
            if (bandTimeSlotContainer.getDateTimeToDeleteBand().equals("")) {
                bindingResult.rejectValue("dateTimeToDeleteBand", "error.bandTimeSlotContainer", "Please choose a certain date");
                fillModelWithAttributesForProgram(new Band(), event, model, bandTimeSlotContainer, new Stage(), new StageIdContainer());
                return "program";
            }
            LocalDateTime localDateTime = LocalDateTime.parse(bandTimeSlotContainer.getDateTimeToDeleteBand());

            removed = event.removeBand(band, localDateTime);
        } else {
            event.removeBand(band);
        }
        if (removed) {
            eventRepository.save(event);
            model.addAttribute("programs", event.getPrograms());
            return "redirect:/program?successRemoveBand&eventId=" + event.getId();
        } else {
            bindingResult.rejectValue("dateTimeToDeleteBand", "error.bandTimeSlotContainer", "There is no time slot with this band combination found");
            fillModelWithAttributesForProgram(new Band(), event, model, bandTimeSlotContainer, new Stage(), new StageIdContainer());
            return "program";
        }
    }

    boolean isTimeSlotSet(BandTimeSlotContainer bandTimeSlotContainer) {
        return bandTimeSlotContainer.getDateTimeToDeleteBand() != null;
    }

    public static boolean isLong(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Long.parseLong(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * check if user does not manipulate the event id
     */
    public boolean isEventIdValid(String eventId) {

        //check if user does not manipulate the event is
        if (!isLong(eventId)) {
            return false;
        }
        long eventIdLong = Long.parseLong(eventId);
        //check if event exists
        Event event = eventRepository.findById(eventIdLong);
        if (event == null) {
            return false;
        }
        return true;

    }

    private boolean noErrors(BindingResult bindingResultEvent,
                             BindingResult bindingResultDateTimeContainer,
                             BindingResult bindingResultAddress,
                             BindingResult bindingResultTmk,
                             BindingResult bindingResultStage) {

        return !bindingResultEvent.hasErrors() && !bindingResultDateTimeContainer.hasErrors() &&
                !bindingResultAddress.hasErrors() && !bindingResultStage.hasErrors() &&
                !bindingResultTmk.hasErrors();
    }

}

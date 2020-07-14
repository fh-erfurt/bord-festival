package de.bord.festival.controllers;

import de.bord.festival.controllers.dataContainers.DateTimeContainer;
import de.bord.festival.controllers.dataContainers.TicketManagerContainer;
import de.bord.festival.controllers.help.HelpClasses;
import de.bord.festival.exception.*;
import de.bord.festival.models.*;
import de.bord.festival.repository.EventRepository;
import de.bord.festival.ticket.CampingTicket;
import de.bord.festival.ticket.DayTicket;
import de.bord.festival.ticket.VIPTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;


@Controller
public class EventController {
    private final EventRepository eventRepository;

    @Autowired
    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping("/event_create")
    public String greetingForm(Model model) {


        model.addAttribute("tmk", new TicketManagerContainer());
        model.addAttribute("event", new Event());
        model.addAttribute("dateTimeContainer", new DateTimeContainer());
        model.addAttribute("address", new Address());
        model.addAttribute("stage", new Stage());


        return "event_create";
    }

    @PostMapping("/event_create")
    public String createEvent(@Valid Event event, BindingResult bindingResultEvent,
                              @Valid DateTimeContainer dateTimeContainer, BindingResult bindingResultDateTimeContainer,
                              @Valid Address address, BindingResult bindingResultAddress,
                              @Valid @ModelAttribute("tmk") TicketManagerContainer tmk, BindingResult bindingResultTmk,
                              @Valid Stage stage, BindingResult bindingResultStage){


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
                eventRepository.save(newEvent);
                return "redirect:/event_create?success&id=" + event.getId();
            } catch (DateDisorderException e) {
                bindingResultDateTimeContainer.rejectValue("startDate", "error.dateTimeContainer", e.getMessage());
            } catch (TimeDisorderException e) {
                bindingResultDateTimeContainer.rejectValue("startTime", "error.dateTimeContainer", e.getMessage());
            }

        }
        return "event_create";

    }

    @GetMapping("events")
    public String getEvents(Model model) throws PriceLevelException, TimeDisorderException, DateDisorderException, BudgetOverflowException, TimeSlotCantBeFoundException {


        List<Event> events = eventRepository.findAll();
        Collections.sort(events, (x,y)->x.getStartDate().compareTo(y.getEndDate()));
        Collections.reverse(events);
        model.addAttribute("events", events);

        return "events";
    }
    @PostMapping("update_event")
    public String updateEvent(Model model) {

        return "events";
    }

    @GetMapping("program")
    public String showProgram(@RequestParam String eventId, Model model) throws PriceLevelException, TimeDisorderException, DateDisorderException, BudgetOverflowException, TimeSlotCantBeFoundException {

        if (!isLong(eventId)){
            return "error404";
        }
        long eventIdLong= Long.parseLong(eventId);

        ///
        HelpClasses helpClasses = new HelpClasses();

        Event event=helpClasses.getValidNDaysEvent(2);
        event.addBand(helpClasses.getBand("My lovely band", 10.00));
        event.addBand(helpClasses.getBand("My lovely band2", 10.00  ));
        event.addBand(helpClasses.getBand("My lovely band3", 10.00  ));
        event.addBand(helpClasses.getBand("My lovely band4", 10.00  ));
        event.addBand(helpClasses.getBand("My lovely band5", 10.00  ));
        event.addStage(helpClasses.getStage(2, "Stage2"));
        eventRepository.save(event);
        ///
        Event event1 = eventRepository.findById(eventIdLong);
        if (event1==null){
            return "eventAnsicht";
        }
        fillModelWithAttributes(new Band(), event1, model);

        return "program";
    }

    @PostMapping("band_add")
    public String addBand(@Valid Band band, BindingResult bindingResult,
                          @RequestParam String eventId,
                          Model model) {
        //check if user does not manipulate the event is
        if (!isLong(eventId)){
            return "error404";
        }
        long eventIdLong= Long.parseLong(eventId);
        //check if event exists
        Event event = eventRepository.findById(eventIdLong);
        if (event==null){
            return "error404";
        }
        //check if band values are right
        if (bindingResult.hasErrors()){
            fillModelWithAttributes(band, event, model);
            return "program";
        }
        //add band and save event if there are no other exceptions
        try {
            event.addBand(band);
            eventRepository.save(event);
            model.addAttribute("programs", event.getPrograms());
            return "redirect:/program?success&eventId="+eventId;

        } catch (BudgetOverflowException e) {
            bindingResult.rejectValue("pricePerEvent", "error.band", e.getMessage());
        }catch (TimeSlotCantBeFoundException e){
            bindingResult.rejectValue("minutesOnStage", "error.band", e.getMessage());
        }
        finally {
            fillModelWithAttributes(band, event, model);
        }
        return "program";

    }

    void fillModelWithAttributes(Band band, Event event, Model model){
        model.addAttribute("dayPrograms", event.getPrograms());
        model.addAttribute("band", band);
        model.addAttribute("eventId", String.valueOf(event.getId()));
    }



    @PostMapping("stage_add")
    public String addStage(@Valid Stage stage,
                           BindingResult bindingResult,
                           @RequestParam long eventId) {

        if (bindingResult.hasErrors()){
            return "program";
        }
        Event event=eventRepository.findById(eventId);

        if (event==null){
            bindingResult.addError(new ObjectError("MainObject", "This event does not exist"));
        }


        return "events";
    }
    @PostMapping("band_remove")
    public String removeBand(Model model, long eventId,
                             @Valid Band band, BindingResult bindingResultBand,
                             LocalDateTime localDateTime) {

        if (bindingResultBand.hasErrors()){
            return "eventUpdate";
        }
        Event event=eventRepository.findById(eventId);
        if(event==null){
            bindingResultBand.rejectValue("mainElement", "error.mainElement",
                    "This event does not exist");
            return "eventUpdate";
        }
        if (localDateTime!= null){
            event.removeBand(band, localDateTime);
        }
        else{
            event.removeBand(band);
        }
        eventRepository.save(event);

        return "redirect:/removeBand?success";
    }



    @PostMapping("stage_remove")
    public String removeStage(Model model) {

        return "events";
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

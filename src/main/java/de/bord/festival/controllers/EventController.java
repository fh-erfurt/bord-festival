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
                              @Valid Stage stage, BindingResult bindingResultStage) {
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
    public String getEvents(Model model) throws PriceLevelException, TimeDisorderException, DateDisorderException {

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


    @PostMapping("band_add")
    public String addBand(@Valid Band band, BindingResult bindingResult,
                          long minutesOnStage, BindingResult bindingResultMinutesOnStage,
                          @RequestParam long eventId,
                          Model model) {

        if (bindingResult.hasErrors()){
            return "eventUpdate";
        }

        Event event=eventRepository.findById(eventId);

        if (event==null){
            bindingResult.addError(new ObjectError("mainElement", "This event does not exist"));
            return "eventUpdate";
        }

        try {
            event.addBand(band, minutesOnStage);
            eventRepository.save(event);
            model.addAttribute("programs", event.getPrograms());
            //here will be printed all bands with their information
            return "redirect:/addBand?success";

        } catch (BudgetOverflowException | TimeSlotCantBeFoundException e) {
            bindingResult.addError(new ObjectError("mainElement", e.getMessage()));
            return "eventUpdate";
        }

    }



    @PostMapping("stage_add")
    public String addStage(Model model) {

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

package de.bord.festival.controllers;

import de.bord.festival.exception.DateDisorderException;
import de.bord.festival.exception.PriceLevelException;
import de.bord.festival.exception.TimeDisorderException;
import de.bord.festival.models.*;
import de.bord.festival.repository.EventRepository;
import de.bord.festival.ticket.CampingTicket;
import de.bord.festival.ticket.DayTicket;
import de.bord.festival.ticket.VIPTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;


@Controller
public class EventController {
    private final EventRepository eventRepository;

    @Autowired
    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    /*
    * TODO: Validation for all members
    * */
    @PostMapping("/event")
    @ResponseBody
    public String createEvent(@Valid Event event, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "formEvent";
        }
        else{
            return "You have saved your event!";
        }
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
                              @Valid Address address,  BindingResult bindingResultAddress,
                              @Valid @ModelAttribute("tmk")TicketManagerContainer tmk, BindingResult bindingResultTmk,
                              @Valid Stage stage, BindingResult bindingResultStage) {
        if (noErrors(bindingResultEvent, bindingResultDateTimeContainer,
                bindingResultAddress, bindingResultTmk,bindingResultStage )){

            //get all values for Event constructor
            LocalTime startTime=dateTimeContainer.getStartTime();
            LocalTime endTime=dateTimeContainer.getEndTime();
            LocalDate startDate=dateTimeContainer.getStartDate();
            LocalDate endDate=dateTimeContainer.getEndDate();
            long breakBetweenTwoBands=dateTimeContainer.getBreakBetweenTwoBands();
            String name=event.getName();

            BigDecimal budget=event.getBudget();


            List<PriceLevel> priceLevels = tmk.getPriceLevels();
            int numberOfDayTickets=tmk.getNumberOfDayTickets();
            int numberOfCampingTickets=tmk.getNumberOfCampingTickets();
            int numberOfVipTickets= tmk.getNumberOfVipTickets();

            String ticketDescriptionPattern=name+':'+address.getCity()+':'+startDate.toString()+':';
            DayTicket dayTicket= new DayTicket(ticketDescriptionPattern+"day:"+tmk.getDayTicketDescription(), 0);


            CampingTicket campingTicket= new CampingTicket(ticketDescriptionPattern+"camping:"+tmk.getCampingTicketDescription(), 0);
            VIPTicket vipTicket= new VIPTicket(ticketDescriptionPattern+"vip:"+tmk.getVipTicketDescription(), 0);
            TicketManager ticketManager=new TicketManager(priceLevels, numberOfDayTickets,
                    numberOfCampingTickets, numberOfVipTickets, dayTicket, campingTicket, vipTicket);
            try{

                Event newEvent = Event.getNewEvent(startTime, endTime, breakBetweenTwoBands,startDate,
                        endDate, name, budget, stage, ticketManager, address);
                Event databaseEvent =eventRepository.save(newEvent);
                return "redirect:/event_create?success&id="+event.getId();
            }catch (DateDisorderException e ) {
                bindingResultDateTimeContainer.rejectValue("startDate","error.dateTimeContainer", e.getMessage());
            }
            catch (TimeDisorderException e){
                bindingResultDateTimeContainer.rejectValue("startTime","error.dateTimeContainer", e.getMessage());
            }

        }
        return "event_create";



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

    @PutMapping("/event")
    @ResponseBody
    public String updateEvent(@Valid Event event){
        return "You updated your event";
    }
}

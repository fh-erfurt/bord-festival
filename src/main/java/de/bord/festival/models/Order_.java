package de.bord.festival.models;

import de.bord.festival.ticket.CampingTicket;
import de.bord.festival.ticket.DayTicket;
import de.bord.festival.ticket.Type;
import de.bord.festival.ticket.VIPTicket;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name="orders")
public class Order_ extends AbstractModel {



    @OneToMany (cascade = CascadeType.ALL)
    private List<Ticket> dayTickets;

    @OneToMany (cascade = CascadeType.ALL)
    private List<Ticket> campingTickets;

    @OneToMany (cascade = CascadeType.ALL)
    private List<Ticket> vipTickets;

    public Order_(){}

    public Order_(List<Ticket> tickets){
        this.dayTickets = new LinkedList<>();
        this.campingTickets = new LinkedList<>();
        this.vipTickets = new LinkedList<>();
        setTicketList(tickets);
    }

    public List<Ticket> getDayTickets() {
        return dayTickets;
    }

    public List<Ticket> getCampingTickets() {
        return campingTickets;
    }

    public List<Ticket> getVipTickets() {
        return vipTickets;
    }

    private void setTicketList(List<Ticket> tickets){
        for (Ticket ticket:tickets) {
            if(ticket.getTicketType()== Type.DAY){
                dayTickets.add((DayTicket)ticket);
            }
            else if((ticket.getTicketType()== Type.CAMPING)){
                campingTickets.add((CampingTicket)ticket);
            }
            else if((ticket.getTicketType()== Type.VIP)){
                vipTickets.add((VIPTicket)ticket);
            }
        }
    }

}

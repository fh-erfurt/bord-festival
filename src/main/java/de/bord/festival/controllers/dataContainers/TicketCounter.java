package de.bord.festival.controllers.dataContainers;

import de.bord.festival.models.Client;
import de.bord.festival.ticket.Type;

/**
 * the class provides information about the data (number of tickets) in the shopping cart
 */

public class TicketCounter {

    private int dayTicketCounter;
    private int campingTicketCounter;
    private int vipTicketCounter;
    private boolean isCartFull = false;

    public TicketCounter(){
        dayTicketCounter = 0;
        campingTicketCounter = 0;
        vipTicketCounter = 0;
    }

    public int getDayTicketCounter() {
        return dayTicketCounter;
    }

    public int getCampingTicketCounter() {
        return campingTicketCounter;
    }

    public int getVipTicketCounter() {
        return vipTicketCounter;
    }

    public void setTicketCounter(Client client){
        campingTicketCounter=0;
        vipTicketCounter=0;
        dayTicketCounter=0;
        for (int i=0; i<client.getCartSize(); i++){
            if(client.getCartItem(i).getTicketType() == Type.DAY){
                dayTicketCounter++;
            }
            else if (client.getCartItem(i).getTicketType() == Type.VIP){
                vipTicketCounter++;
            }
            else if(client.getCartItem(i).getTicketType() == Type.CAMPING){
                campingTicketCounter++;
            }
        }

    }

    public boolean toManyTickets(){
        if(campingTicketCounter + vipTicketCounter + dayTicketCounter == 10){
            isCartFull = true;
            return true;

        }
        return false;
    }

    public boolean isCartFull() {
        return isCartFull;
    }

    public boolean areNoTicketsInCart(){
        if(dayTicketCounter+vipTicketCounter+campingTicketCounter==0){
            return true;
        }
        else {
            return false;
        }
    }
}

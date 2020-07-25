package de.bord.festival.controllers.dataContainers;

import de.bord.festival.models.Client;
import de.bord.festival.ticket.Type;

public class TicketCounter {


    private int dayTicketCounter;
    private int campingTicketCounter;
    private int vipTicketCounter;

    public TicketCounter(){
        dayTicketCounter = 0;
        campingTicketCounter = 0;
        vipTicketCounter = 0;
    }

    public int getDayTicketCounter() {
        return dayTicketCounter;
    }

    public void setDayTicketCounter(int dayTicketCounter) {
        this.dayTicketCounter = dayTicketCounter;
    }

    public int getCampingTicketCounter() {
        return campingTicketCounter;
    }

    public void setCampingTicketCounter(int campingTicketCounter) {
        this.campingTicketCounter = campingTicketCounter;
    }

    public int getVipTicketCounter() {
        return vipTicketCounter;
    }

    public void setVipTicketCounter(int vipTicketCounter) {
        this.vipTicketCounter = vipTicketCounter;
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

    public boolean checkTicketCounter(){
        if(campingTicketCounter==0 && vipTicketCounter==0 && dayTicketCounter == 0){
            return true;
        }
        return false;
    }
}

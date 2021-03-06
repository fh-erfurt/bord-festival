package de.bord.festival.models;

import de.bord.festival.client.IClient;
import de.bord.festival.exception.ClientNameException;
import de.bord.festival.exception.MailException;
import de.bord.festival.exception.PriceLevelException;
import de.bord.festival.exception.TicketNotAvailableException;
import de.bord.festival.ticket.Type;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

@Entity
public class Client extends AbstractModel implements IClient {

    private String firstname;
    private String lastname;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Address address;
    private String mail;
    private String password;
    private Role role;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Ticket> inventory;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ticket> cart;
    private double expenditure = 0.0;
    private double expenditureBasket = 0.0;


    @OneToMany(cascade = CascadeType.ALL)
     private List<Order_> orders_;

    public Client(){}

    public Client(String firstname, String lastname, String mail, String password, Address address, Role role)
    {
        inventory = new LinkedList<Ticket>();
        cart = new LinkedList<Ticket>();
       orders_ = new LinkedList<Order_>();
        this.firstname = firstname;
        this.lastname = lastname;
        this.mail = mail;
        this.password = password;
        this.address = address;
        this.role = role;
    }

    public static Client getNewClient(String firstname, String lastname, String mail, String password, Address address, Role role)
            throws ClientNameException, MailException {
        nameCheck(firstname);
        nameCheck(lastname);
        mailCheck(mail);

        return new Client(firstname, lastname, mail, password, address, role);
    }

    /**
     * Checks if the name is within given restrictions
     *
     * @param name
     * @throws ClientNameException if wrong characters / no characters / more than 50 characters
     */
    public static void nameCheck(String name) throws ClientNameException {
        Pattern p = Pattern.compile("^[a-zA-ZäÄöÖüÜß]*$");
        Matcher m = p.matcher(name);

        if(!m.find()) {
            throw new ClientNameException("Name can only consist of letters");
        }
        else if(name.trim().isEmpty()) {
            throw new ClientNameException("Name can't be empty");
        }
        else if(name.length() > 50) {
            throw new ClientNameException("Name can't be longer than 50 characters");
        }
    }

    /**
     * Checks if the e-mail-address is within given restrictions
     *
     * @param mail
     * @throws MailException
     */
    public static void mailCheck(String mail) throws MailException {
        Pattern p = Pattern.compile("^[\\w!#$%&’*+/=?_`(){|}~\"@<>,:;^-]+(?:\\.[\\w!#$%&’*+/=?_`(){|}~\"@<>,:;^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
        Matcher m = p.matcher(mail);

        if(!m.find()) {
            throw new MailException("Falsche E-Mail-Adresse");
        }
    }

    /**
     * Overrides old firstname and lastname with new ones
     *
     * @param newFirstName
     * @param newLastName
     * @throws ClientNameException
     */
    public void changeName(String newFirstName, String newLastName) throws ClientNameException {
        nameCheck(newFirstName);
        nameCheck(newLastName);

        this.firstname = newFirstName;
        this.lastname = newLastName;
    }

    /**
     * Called when a Client adds a ticket to his shoppingcart
     *
     * @param type type
     * @param ticketmanager ticketmanager
     * @throws TicketNotAvailableException
     */

    public void addTicket(Type type, TicketManager ticketmanager) throws TicketNotAvailableException, PriceLevelException {
        if(!ticketmanager.isAvailable(type, 1)) {
            throw new TicketNotAvailableException("No more tickets available");
        }
                Ticket ticket = ticketmanager.getNewTicket(type);

                this.cart.add(ticket);
                this.expenditureBasket += ticket.getStdPrice();
    }

    /**
     * Called when a Client buys his whole shopping-cart
     * Adds the bought tickets to his inventory
     */
    public void addCartToInventory(){
        inventory.addAll(cart);
    }

    /**
     * @param index number of ticket in cart
     * @return ticket with specified index
     */
    public Ticket getCartItem(int index) { return cart.get(index); }

    public int getCartSize(){ return cart.size(); }

    public int getInventorySize(){ return inventory.size(); }

    public void clearCart(){
        this.cart.clear();
    }

    public void clearExpenditureBasket(){
        expenditureBasket = 0;
    }

    /**
     * Adds to the expenditure attribute of Client
     * @param expenditure
     */
    public void setExpenditure(double expenditure) { this.expenditure += expenditure; }

    public double getExpenditure(){return expenditure;}

    public int getNumberOfAllTicketsInBasket(){
        int ticketcounter = 0;
        ticketcounter += getNumberOfTicketsInBasket(Type.DAY);
        ticketcounter += getNumberOfTicketsInBasket(Type.VIP);
        ticketcounter += getNumberOfTicketsInBasket(Type.CAMPING);

        return ticketcounter;
    }

    public int getNumberOfTicketsInBasket(Type type){
        int ticketcounter = 0;
        for(int i = 0; i<cart.size();i++){
            if(this.cart.get(i).getTicketType() == type){
                ticketcounter++;
            }
        }
        return ticketcounter;
    }


    @Override
    public boolean equals(Object object) {
        if(object instanceof Client) {
            Client client = (Client)object;
            return this.firstname.equals(client.getFirstname())
                    && this.lastname.equals(client.getLastname())
                    && this.mail.equals(client.getMail());
        }
        return false;
    }

    public String getFirstname() {
        return this.firstname;
    }
    public String getLastname() {
        return this.lastname;
    }
    public String getMail() { return this.mail; }
    public Address getAddress() {return this.address; }
    public String getPassword() { return this.password; }
    public Role getRole() { return this.role; }
    public String getAuthorities() { return "ROLE_" + role; }


    public void setFirstname(String firstname){ this.firstname=firstname; }
    public void setLastname(String lastname){ this.lastname=lastname; }
    public void setMail(String mail){ this.mail=mail; }
    public void setAddress(Address address) { this.address=address; }
    public void setPassword(String password) { this.password=password; }
   // public void setRole(String role) { this.role=role; }

    public double getExpenditureBasket() {
        return expenditureBasket;
    }

   public List<Order_> getOrders_() {
        return orders_;
    }

    public void addOrder(){
        Order_ order = new Order_(cart);
        this.orders_.add(order);
    }


    public double getExpenditureByPricesFromCart(){
        double  expenditureBasket_ = 0.0;
        for(Ticket ticket : this.cart){

            expenditureBasket_ += ticket.getStdPrice();
        }
        return expenditureBasket_;
    }

    public void setRole(Role role) { this.role=role; }
}


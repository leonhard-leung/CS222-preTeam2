package shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**This class represents a customer object
 * @author Stephen Coloma*/
public class Customer implements Serializable {
    private String name;
    private String username;
    private String address;
    private String email;
    private String password;
    private List<Order> orderHistory;

    /**Constructor for customer object*/
    public Customer(String name, String username, String address, String email, String password) {
        this.name = name;
        this.username = username;
        this.address = address;
        this.email = email;
        this.password = password;
    }

    /**This constructor will be used only for customer login. The customer object has already been made and saved in the system*/
    public Customer(Customer customer){
        this.name = customer.getName();
        this.username = customer.getUsername();
        this.address = customer.getAddress();
        this.email = customer.getEmail();
        this.password = customer.getPassword();

        this.orderHistory = new ArrayList<>(); //initializes the arraylist
    }

    /**This constructor will only be used for reading from the xml file*/
    public Customer(Customer customer, List<Order> orderHistory){
        this.name = customer.getName();
        this.username = customer.getUsername();
        this.address = customer.getAddress();
        this.email = customer.getEmail();
        this.password = customer.getPassword();

        this.orderHistory = orderHistory;
    }

    //getters setters

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(List<Order> orderHistory) {
        this.orderHistory = orderHistory;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", orderHistory=" + orderHistory +
                '}';
    }
}

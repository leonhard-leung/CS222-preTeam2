package shared;

import java.io.Serializable;
import java.util.List;

/**This method represents an order where it contains the customer, his orders and the time he pushed the order*/
public class Order implements Serializable {
    private Customer customer;
    private List<Product> orders;
    private String timeStamp;
    private double totalPrice; //for successful order
    private boolean status; //for successful order
    private int ID; //for successful order
    private static int count = 0; //for successful order

    /**This constructor will be mainly used in client side*/
    public Order(Customer customer, List<Product> orders, String timeStamp) {
        this.customer = customer;
        this.orders = orders;
        this.timeStamp = timeStamp;
    }

    /**This constructor will be used only for reading from the database*/
    public Order( List<Product> productList, String timeStamp, double totalPrice, boolean status, int id){
        this.orders = productList;
        this.timeStamp = timeStamp;
        this.totalPrice = totalPrice;
        this.status = status;
        this.ID = id;
    }

    /**This constructor should be use only for an order that is pushed successfully in the system */
    public Order(Order successfullOrder){
        //copy details
        this.customer = successfullOrder.getCustomer();
        this.orders = successfullOrder.getOrders();
        this.timeStamp = successfullOrder.getTimeStamp();

        //set other fields
        status = false;

        //get total price of the order
        totalPrice = 0;
        for (Product product: successfullOrder.getOrders()) {
            if (product instanceof Food food){

                totalPrice+=food.getPrice()*food.getQuantity(); //price for food
            }else if (product instanceof Beverage beverage){

                for (String variation: beverage.getSizeQuantity().keySet()) {
                    totalPrice += beverage.getVariationPrice(variation)*beverage.getVariationQuantity(variation);
                }
            }
        }

        //setting the order ID through incrementation
        synchronized (this){
            ++count;
            this.ID = count;
        }
    }

    /**This constructor will be used only for reading order_list.xml */
    public Order(Customer customer, List<Product> orders, int id, String timeStamp, double totalPrice, boolean status){
        this.customer = customer;
        this.orders = orders;
        this.ID = id;
        this.timeStamp = timeStamp;
        this.totalPrice = totalPrice;
        this.status = status;
    }


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Product> getOrders() {
        return orders;
    }

    public void setOrders(List<Product> orders) {
        this.orders = orders;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public int getID() {
        return ID;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "customer=" + customer +
                ", orders=" + orders +
                ", timeStamp='" + timeStamp + '\'' +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                ", ID=" + ID +
                '}';
    }
}

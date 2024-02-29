package client.model;

import shared.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClientModel {
    private HashMap<String, Food> foodMenu; // return from server
    private HashMap<String, Beverage> beverageMenu; //return from server
    private Customer customer; //return from server
    private final List<Product> cart;
    private Order order;

    /**This initially sets all fields to null.*/
    public ClientModel() {
        foodMenu = null;
        beverageMenu = null;
        customer = null;
        cart = null;
    }

    /**This constructor will be used solely for logging in.*/
    public ClientModel( Customer customer, HashMap<String, Food> foodMenu, HashMap<String, Beverage> beverageMenu) {
        this.customer = customer;
        this.foodMenu = foodMenu;
        this.beverageMenu = beverageMenu;

        this.cart = new ArrayList<>();
    }

    /**This method creates an order if the user wants to check out an order*/
    public Order placeOrder(){
        if (customer != null && cart != null && !cart.isEmpty()) {
            /*Logic for getting time stamp: The format is 01/05/2024*/
            String timestamp = java.time.LocalDate.now().toString();
            return new Order(customer, cart, timestamp); // Return order if there's an item in cart
        } else {
            return null; // Can not check out if there's no customer or cart is empty
        }
    }

    /**This method adds the order to customer's order history*/
    public void orderProcessSuccessful(Order successfulOrder){
        order = null;
        cart.clear(); // Clears the cart after creating the order
        customer.getOrderHistory().add(successfulOrder);
    }

    //getter and setter
    public HashMap<String, Food> getFoodMenu() {
        return foodMenu;
    }

    public void setFoodMenu(HashMap<String, Food> foodMenu) {
        this.foodMenu = foodMenu;
    }

    public HashMap<String, Beverage> getBeverageMenu() {
        return beverageMenu;
    }

    public void setBeverageMenu(HashMap<String, Beverage> beverageMenu) {
        this.beverageMenu = beverageMenu;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Product> getCart() {
        return cart;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}

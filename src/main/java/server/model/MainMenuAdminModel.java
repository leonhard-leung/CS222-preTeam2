package server.model;

import server.model.listeners.MainMenuAdminObserver;
import shared.Beverage;
import shared.Customer;
import shared.Food;
import shared.Order;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainMenuAdminModel {
    private final AtomicBoolean menuChanges = new AtomicBoolean(false);
    private final List<MainMenuAdminObserver> observers = new ArrayList<>();
    private List<Customer> customerAccountList;
    private List<Order> orderList;
    private HashMap<String, Food> foodMenu;
    private HashMap<String, Beverage> beverageMenu;

    public void setCustomerAccountList(List<Customer> customerAccountList) {
        this.customerAccountList = customerAccountList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public void setFoodMenu(HashMap<String, Food> foodMenu) {
        this.foodMenu = foodMenu;
    }

    public void setBeverageMenu(HashMap<String, Beverage> beverageMenu) {
        this.beverageMenu = beverageMenu;
    }

    public void setMenuChanges(boolean value) {
        menuChanges.set(value);
    }

    public void registerObserver(MainMenuAdminObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers(String code) {
        for (MainMenuAdminObserver observer : observers) {
            observer.notifyMenuChanges(code, menuChanges.get());
        }
    }

    public List<Customer> getCustomerAccountList() {
        return customerAccountList;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public HashMap<String, Food> getFoodMenu() {
        return foodMenu;
    }

    public HashMap<String, Beverage> getBeverageMenu() {
        return beverageMenu;
    }
} // end of MainMenuAdminModel class

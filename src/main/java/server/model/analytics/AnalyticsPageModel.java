package server.model.analytics;

import shared.Beverage;
import shared.Food;
import shared.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AnalyticsPageModel {
    private List<Order> orderList = new ArrayList<>();
    private HashMap<String, Food> foodList = new HashMap<>();
    private HashMap<String, Beverage> beverageList = new HashMap<>();
    private double sales;
    private int totalOrders;

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public void setFoodList(HashMap<String, Food> foodList) {
        this.foodList = foodList;
    }

    public void setBeverageList(HashMap<String, Beverage> beverageList) {
        this.beverageList = beverageList;
    }

    public HashMap<String, Food> getFoodList() {
        return foodList;
    }

    public HashMap<String, Beverage> getBeverageList() {
        return beverageList;
    }

    public double getSales() {
        return sales;
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public void computeTotalOrders() {
        totalOrders = orderList.size();
    }

    public void computeSales() {
        for (Order order : orderList) {
            sales += order.getTotalPrice();
        }
    } // end of computeSales
} // end of AnalyticsPageModel

package server.model.inventory;

import javafx.collections.ObservableList;
import server.model.listeners.InventoryPageObserver;
import shared.Beverage;
import shared.Food;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class InventoryPageModel {
    private final AtomicBoolean inventoryChanges = new AtomicBoolean(false);
    private final List<InventoryPageObserver> observers = new ArrayList<>();
    private HashMap<String, Food> foodList = new HashMap<>();
    private HashMap<String, Beverage> beverageList = new HashMap<>();

    public void setFoodList(HashMap<String, Food> foodList) {
        this.foodList = foodList;
    }

    public void setBeverageList(HashMap<String, Beverage> beverageList) {
        this.beverageList = beverageList;
    }

    public void setInventoryChanges(boolean value) {
        inventoryChanges.set(value);
    }

    public void registerObserver(InventoryPageObserver observer) {
        observers.add(observer);
    } // end of registerObserver

    public void notifyObservers() {
        for (InventoryPageObserver observer : observers) {
            observer.notifyInventoryChanges(inventoryChanges.get());
        }
    } // end of notifyObservers

    public HashMap<String, Food> getFoodList() {
        return foodList;
    }

    public HashMap<String, Beverage> getBeverageList() {
        return beverageList;
    }

    public void updateInventory(ObservableList<Object> list) {
        HashMap<String, Food> newFoodList = new HashMap<>();
        HashMap<String, Beverage> newBeverageList = new HashMap<>();

        for (Object entry : list) {
            if (entry instanceof Food food) {
                newFoodList.put(food.getName(), food);
            } else if (entry instanceof Beverage beverage) {
                newBeverageList.put(beverage.getName(), beverage);
            }
        }

        setFoodList(newFoodList);
        setBeverageList(newBeverageList);
    } // end of updateInventory
} // end of InventoryPageModel

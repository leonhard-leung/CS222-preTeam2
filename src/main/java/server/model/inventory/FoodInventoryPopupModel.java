package server.model.inventory;

import shared.Food;

import java.util.concurrent.atomic.AtomicInteger;

public class FoodInventoryPopupModel {
    private final AtomicInteger totalQuantity = new AtomicInteger(0);
    private int value;
    private Food food;

    public void setFood(Food food) {
        this.food = food;
        totalQuantity.set(food.getQuantity());
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Food getFood() {
        return food;
    }

    public int getValue() {
        return value;
    }

    public AtomicInteger getTotalQuantity() {
        return totalQuantity;
    }

    public void updateQuantity(int counter) {
        do {
            if (counter > 0) {
                food.incrementQuantity();
                counter--;
            } else {
                food.decrementQuantity();
                counter++;
            }
        } while (counter != 0);
    } // end of updateQuantity
} // end of FoodInventoryPopupModel class

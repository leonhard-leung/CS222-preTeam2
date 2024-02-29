package server.model.inventory;

import shared.Beverage;

import java.util.concurrent.atomic.AtomicInteger;

public class BeverageInventoryPopupModel {
    private final AtomicInteger smallTotalQuantity = new AtomicInteger(0);
    private final AtomicInteger mediumTotalQuantity = new AtomicInteger(0);
    private final AtomicInteger largeTotalQuantity = new AtomicInteger(0);
    private int smallValue;
    private int mediumValue;
    private int largeValue;
    private Beverage beverage;

    public void setBeverage(Beverage beverage) {
        this.beverage = beverage;
        smallTotalQuantity.set(beverage.getSizeQuantity().get("small"));
        mediumTotalQuantity.set(beverage.getSizeQuantity().get("medium"));
        largeTotalQuantity.set(beverage.getSizeQuantity().get("large"));
    }

    public void setSmallValue(int value) {
        smallValue = value;
    }

    public void setMediumValue(int value) {
        mediumValue = value;
    }

    public void setLargeValue(int value) {
        largeValue = value;
    }

    public Beverage getBeverage() {
        return beverage;
    }

    public int getSmallValue() {
        return smallValue;
    }

    public int getMediumValue() {
        return mediumValue;
    }

    public int getLargeValue() {
        return largeValue;
    }

    public AtomicInteger getSmallTotalQuantity() {
        return smallTotalQuantity;
    }

    public AtomicInteger getMediumTotalQuantity() {
        return mediumTotalQuantity;
    }

    public AtomicInteger getLargeTotalQuantity() {
        return largeTotalQuantity;
    }

    public void updateQuantity(int smallCounter, int mediumCounter, int largeCounter) {
        do {
            if (smallCounter > 0) {
                beverage.incrementQuantity("small");
                smallCounter--;
            } else {
                beverage.decrementQuantity("small");
                smallCounter++;
            }
        } while (smallCounter != 0);

        do {
            if (mediumCounter > 0) {
                beverage.incrementQuantity("medium");
                mediumCounter--;
            } else {
                beverage.decrementQuantity("medium");
                mediumCounter++;
            }
        } while (mediumCounter != 0);

        do {
            if (largeCounter > 0) {
                beverage.incrementQuantity("large");
                largeCounter--;
            } else {
                beverage.decrementQuantity("large");
                largeCounter++;
            }
        } while (largeCounter != 0);
    } // end of updateQuantity
} // end of BeverageInventoryPopupModel

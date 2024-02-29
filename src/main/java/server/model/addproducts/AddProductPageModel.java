package server.model.addproducts;

import server.model.listeners.AddProductPageObserver;
import shared.Beverage;
import shared.Food;
import util.ImageUtility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class AddProductPageModel {
    private final AtomicBoolean newProduct = new AtomicBoolean(false);
    private final List<AddProductPageObserver> observers = new ArrayList<>();
    private HashMap<String, Food> foodMenu = new HashMap<>();
    private HashMap<String, Beverage> beverageMenu = new HashMap<>();

    private Food food;
    private Beverage beverage;
    private String name;
    private char type;
    private String description;
    private int sQuantity;
    private int mQuantity;
    private int lQuantity;
    private double sPrice;
    private double mPrice;
    private double lPrice;
    private Object[] image;

    public void setFoodMenu(HashMap<String, Food> foodMenu) {
        this.foodMenu = foodMenu;
    }

    public void setBeverageMenu(HashMap<String, Beverage> beverageMenu) {
        this.beverageMenu = beverageMenu;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(char type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSQuantity(int sQuantity) {
        this.sQuantity = sQuantity;
    }

    public void setMQuantity(int mQuantity) {
        this.mQuantity = mQuantity;
    }

    public void setLQuantity(int lQuantity) {
        this.lQuantity = lQuantity;
    }

    public void setSPrice(double sPrice) {
        this.sPrice = sPrice;
    }

    public void setMPrice(double mPrice) {
        this.mPrice = mPrice;
    }

    public void setLPrice(double lPrice) {
        this.lPrice = lPrice;
    }

    public void setNewProduct(boolean value) {
        newProduct.set(value);
    }

    public void registerObserver(AddProductPageObserver observer) {
        observers.add(observer);
    } // end of registerObserver

    public void notifyObservers() {
        for (AddProductPageObserver observer : observers) {
            observer.notifyNewProduct(newProduct.get(), type);
        }
    } // end of notifyObservers

    public void createProduct(boolean isFood) {
        if (isFood) {
            food = new Food(name, type, 0.0, 0, image, description, sQuantity, sPrice);
            foodMenu.put(name, food);
        } else {
            beverage = new Beverage(name, type, 0.0, 0, image, description, sQuantity, mQuantity, lQuantity, sPrice, mPrice, lPrice);
            beverageMenu.put(name, beverage);
        }
    } // end of createProduct

    public void createCopy(String absolutePath) {
        String extension = absolutePath.substring(absolutePath.lastIndexOf('.'));
        String copiedImagePath = ImageUtility.copyImage(absolutePath, name + extension);

        String imageFilename = copiedImagePath.substring(copiedImagePath.lastIndexOf('\\') + 1);
        try {
            image = new Object[]{imageFilename, ImageUtility.getImageBytes(imageFilename)};
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    } // end of createCopy

    public Food getFood() {
        return food;
    }

    public Beverage getBeverage() {
        return beverage;
    }
} // end of AddProductPageModel

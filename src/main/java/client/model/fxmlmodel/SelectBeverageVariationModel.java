package client.model.fxmlmodel;

import javafx.scene.image.Image;
import shared.Beverage;
import shared.Product;

import java.text.DecimalFormat;

public class SelectBeverageVariationModel {
    private final String productName;
    private final String productDetails;
    private final Image productImage;
    private Beverage beverage;
    private int smallAvailability;
    private int mediumAvailability;
    private int largeAvailability;

    private double smallTotal;
    private double mediumTotal;
    private double largeTotal;

    private final double smallPrice;
    private final double mediumPrice;
    private final double largePrice;

    private int smallOrderedQuantity;
    private int mediumOrderedQuantity;
    private int largeOrderedQuantity;

    public SelectBeverageVariationModel(Product product){
        beverage = (Beverage) product;

        this.productName = beverage.getName();
        this.productDetails = beverage.getDescription();

        this.smallAvailability = beverage.getSizeQuantity().get("small"); //need to subtract 1 for display
        this.mediumAvailability = beverage.getSizeQuantity().get("medium"); //need to subtract 1 for display
        this.largeAvailability = beverage.getSizeQuantity().get("large"); //need to subtract 1 for display

        this.smallPrice = beverage.getSizePrice().get("small");
        this.mediumPrice = beverage.getSizePrice().get("medium");
        this.largePrice = beverage.getSizePrice().get("large");

        this.productImage = beverage.getImage();

        //totals
        smallTotal = 0;
        mediumTotal = 0;
        largeTotal = 0;

        //order quantity
        smallOrderedQuantity = 0;
        mediumOrderedQuantity = 0;
        largeOrderedQuantity = 0;
    }

    public String incrementSmallQuantity(){
        if (smallAvailability > 0){

            ++smallOrderedQuantity;
            --smallAvailability; //decrement the available
            smallTotal+=smallPrice;
            return "";
        }else {
            return null;
        }

    }

    public String decrementSmallQuantity(){
        if (smallOrderedQuantity > 0){
            --smallOrderedQuantity;
            ++smallAvailability;  //increment the available stocks
            smallTotal-=smallPrice;
            return "";
        }else {
            return null;
        }
    }

    public String incrementMediumQuantity(){
        if (mediumAvailability > 0){

            ++mediumOrderedQuantity;
            --mediumAvailability; //decrement the available
            mediumTotal+=mediumPrice;
            return "";
        }else {
            return null;
        }

    }

    public String decrementMediumQuantity(){
        if (mediumOrderedQuantity > 0){
            --mediumOrderedQuantity;
            ++mediumAvailability;  //increment the available stocks
            mediumTotal-=mediumPrice;
            return "";
        }else {
            return null;
        }
    }

    public String incrementLargeQuantity(){
        if (largeAvailability > 0){

            ++largeOrderedQuantity;
            --largeAvailability; //decrement the available
            largeTotal+=largePrice;
            return "";
        }else {
            return null;
        }

    }

    public String decrementLargeQuantity(){
        if (largeOrderedQuantity > 0){
            --largeOrderedQuantity;
            ++largeAvailability;  //increment the available stocks
            largeTotal-=largePrice;
            return "";
        }else {
            return null;
        }
    }

    public String getProductName() {
        return productName;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public Image getProductImage() {
        return productImage;
    }

    public Beverage getBeverage() {
        return beverage;
    }

    public void setBeverage(Beverage beverage) {
        this.beverage = beverage;
    }

    public int getSmallAvailability() {
        return smallAvailability;
    }

    public int getMediumAvailability() {
        return mediumAvailability;
    }

    public int getLargeAvailability() {
        return largeAvailability;
    }

    public String getSmallTotal() {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(smallTotal);
    }

    public String getMediumTotal() {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(mediumTotal);
    }

    public String  getLargeTotal() {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(largeTotal);
    }

    public String getSmallPrice() {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(smallPrice);
    }

    public String getLargePrice() {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(largePrice);
    }

    public int getSmallOrderedQuantity() {
        return smallOrderedQuantity;
    }

    public int getMediumOrderedQuantity() {
        return mediumOrderedQuantity;
    }

    public int getLargeOrderedQuantity() {
        return largeOrderedQuantity;
    }

}

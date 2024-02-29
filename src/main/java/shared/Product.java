package shared;

import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

/**This abstract class represent a product. A product is classified into f - food, and b - beverage.
 * Food does not have any variations, meaning same price and quantity.
 * Whereas for beverage, it may be small, medium, large and has different price and quantity for each
 * @author  Stephen Coloma*/
public abstract class Product implements Serializable{
    private String name;
    private char type;
    private double review; //average of 1-5
    private int reviewCount;
    private Object[] image;
    private String description;
    private int amountSold;

    /**A constructor initializes the details of the classes whose direct descendant of this class.*/
    public Product(String name, char type, double review, int reviewCount, Object[] image, String description) {
        this.name = name;
        this.type = type;
        this.review = review;
        this.image = image;
        this.reviewCount = reviewCount;
        this.description = description;
    }

    //getters setters
    public String getName(){
        return name;
    }

    public char getType(){
        return type;
    }

    public double getReview(){
        return review;
    }

    public int getReviewCount() {
        return reviewCount;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setType(char type) {
        this.type = type;
    }

    public void setReview(double review) {
        this.review = review;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmountSold() {
        return amountSold;
    }

    public void setAmountSold(int amountSold) {
        this.amountSold = amountSold;
    }

    public String getImageName() {
        return (String) image[0];
    }

    public Image getImage() {
        return new Image(new ByteArrayInputStream((byte[]) image[1]));
    }

    public void setImage(Object[] image) {
        this.image = image;
    }

    /**This method updates the review and reviewCount in a synchronized manner*/
    public synchronized void updateReview(double review){
        this.review = (this.review * this.reviewCount + review) / (this.reviewCount + 1);
        reviewCount++;
    }

    public int getQuantity() {
        return 0;
    }

    /**Will only be used for reviews*/
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Product product = (Product) obj;
        return name.equals(product.name);
    }

    /**Will only be used for reviews*/
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", type=" + type +
                ", review=" + review +
                ", reviewCount=" + reviewCount +
                ", image=" + image[0] +
                ", description='" + description + '\'' +
                ", amountSold=" + amountSold ;
    }
}
package shared;

import util.exception.OutOfStockException;

/**This class represents a food object where there is no variation for it.
 * @author  Stephen Coloma*/
public class Food extends Product {
    private int quantity;
    private double price;

    //constructors
    public Food(String name, char type, double review, int reviewCount, Object[] image, String description, int quantity, double price) {
        super(name, type, review, reviewCount, image, description);
        this.quantity = quantity;
        this.price = price;
    }

    /**This constructor will only be used for client side rating function*/
    public Food(String name, char type, double review){
        super(name, type, review, 0, null, null);
    }


    //getters setters
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public synchronized void incrementQuantity() {
        quantity++;
    }

    public synchronized void decrementQuantity() {
        quantity--;
    }

    /**This method updates the quantity of the product for every successful order
     * @throws Exception when the quantity cannot accommodate the order*/
    public synchronized void updateQuantity(int count) throws Exception{
        int temp = quantity;

        quantity = quantity - count;

        if (quantity<0){
            quantity = temp;
            throw new OutOfStockException("Out of stock");
        }

        //reaches here means no error updating the value
        int sold = super.getAmountSold() + count;
        super.setAmountSold(sold);
    }

    @Override
    public String toString() {
        String concat = super.toString();
        return "Food{" + concat +
                "quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
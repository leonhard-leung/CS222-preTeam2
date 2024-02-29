package client.model.fxmlmodel;

import shared.Beverage;
import shared.Food;
import shared.Product;

public class CheckoutItemCardModel {
    private Product product;

    public CheckoutItemCardModel(Food food){
        this.product = food;
    }
    public CheckoutItemCardModel(Beverage beverage){
        this.product = beverage;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

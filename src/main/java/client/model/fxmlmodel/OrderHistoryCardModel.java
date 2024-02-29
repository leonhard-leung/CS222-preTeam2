package client.model.fxmlmodel;

import shared.Beverage;
import shared.Food;
import shared.Product;

public class OrderHistoryCardModel {
    private Product product;

    public OrderHistoryCardModel(Food food){
        this.product = food;
    }
    public OrderHistoryCardModel(Beverage beverage){
        this.product = beverage;
    }
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

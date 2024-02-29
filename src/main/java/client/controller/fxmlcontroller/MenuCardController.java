package client.controller.fxmlcontroller;

import client.model.fxmlmodel.MenuCardModel;
import client.view.fxmlview.MenuCardView;
import shared.Beverage;
import shared.Food;
import shared.Product;

public class MenuCardController {
    private final MenuCardModel model;
    private final MenuCardView view;

    public MenuCardController(MenuCardModel model, MenuCardView view){
        this.model = model;
        this.view = view;
    }

    public void setProductData(Product product) {
        double review = Math.round(product.getReview() * 10.0) / 10.0;
        if (product instanceof Food food) {
            view.setProductName(food.getName());
            view.setProductDescription(food.getDescription());
            view.setProductRating("Rating: " + review);
            view.setProductPrice("₱ " + food.getPrice());
            view.setProductImage(food.getImage());
            view.setSizeLabel("");
            model.setProduct(food);
        } else if (product instanceof Beverage beverage) {
            view.setProductName(beverage.getName());
            view.setProductDescription(beverage.getDescription());
            view.setProductRating("Rating: " + review);
            view.setProductPrice("₱ " + beverage.getSizePrice().get("small") + "0 - " + "₱ " + beverage.getSizePrice().get("large") + "0");
            view.setProductImage(beverage.getImage());
            view.setSizeLabel("size: S,M.L");
            model.setProduct(beverage);
        }
    } // end of setProductData
} // end of MenuCardController
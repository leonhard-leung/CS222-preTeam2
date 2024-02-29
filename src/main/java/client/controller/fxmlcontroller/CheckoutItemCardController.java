package client.controller.fxmlcontroller;

import client.model.fxmlmodel.CheckoutItemCardModel;
import client.view.fxmlview.CheckoutItemCardView;
import shared.Beverage;
import shared.Food;
import shared.Product;

import java.util.HashMap;

public class CheckoutItemCardController {
    private CheckoutItemCardModel model;
    private CheckoutItemCardView view;

    public CheckoutItemCardController(CheckoutItemCardModel model, CheckoutItemCardView view){
        this.view = view;
        this.model = model;
    }

    public void setData(){
        Product product = model.getProduct();
        if (product.getType() == 'f'){
            Food food = (Food) product;
            setFoodDataOnCard(food);
        }else if (product.getType() == 'b'){
            Beverage beverage = (Beverage) product;
            setBeverageDataOnCard(beverage);
        }
    }

    /**This sets up Beverage data of in the card*/
    private void setBeverageDataOnCard(Beverage beverage) {
        String size ="";
        for (String variation: beverage.getSizeQuantity().keySet()) {
            if (beverage.getSizeQuantity().get(variation) != 0){
                size = variation;
            }
        }
        HashMap<String, Double> sizePrice = beverage.getSizePrice();

        view.getItemName().setText(this.model.getProduct().getName());
        view.getItemImage().setImage(this.model.getProduct().getImage());

        int beverageQuantity = ((Beverage) this.model.getProduct()).getSizeQuantity().get(size);
        view.getItemQuantity().setText("qty: " + beverageQuantity);

        double beveragePrice = ((Beverage) this.model.getProduct()).getSizePrice().get(size);
        view.getItemPriceLabel().setText("₱  " + beveragePrice + "0");
        
        view.getItemSizeLabel().setText(size);
        view.getItemSizeLabel().setVisible(true);
    }

    /**This sets up Food data of in the card*/
    private void setFoodDataOnCard(Food food) {
        view.getItemSizeLabel().setVisible(false); //no sizes for food

        view.getItemName().setText(this.model.getProduct().getName());
        view.getItemImage().setImage(this.getModel().getProduct().getImage());
        view.getItemQuantity().setText("qty: " + this.getModel().getProduct().getQuantity());
        double foodTotal = ((Food) this.getModel().getProduct()).getPrice();
        view.getItemPriceLabel().setText("₱ " +  foodTotal + "0");
    }

    public CheckoutItemCardModel getModel() {
        return model;
    }

    public void setModel(CheckoutItemCardModel model) {
        this.model = model;
    }

    public CheckoutItemCardView getView() {
        return view;
    }

    public void setView(CheckoutItemCardView view) {
        this.view = view;
    }
}

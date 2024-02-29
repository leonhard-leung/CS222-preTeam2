package client.controller.fxmlcontroller;

import client.model.fxmlmodel.SelectFoodModel;
import client.view.fxmlview.SelectFoodView;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SelectFoodController {
    private SelectFoodModel model;
    private SelectFoodView view;
    private int finalOrderedQuantity; //used for add to cart button not the close button
    private double finalOrderedPrice;


    public SelectFoodController(SelectFoodModel model, SelectFoodView view) {
        this.model = model;
        this.view = view;

        //setting up the view
        this.view.getProductNameLabel().setText(model.getProductName());
        this.view.getProductDescriptionLabel().setText(model.getProductDetails());
        this.view.getProductPriceLabel().setText("₱ " + model.getProductPrice());
        this.view.getProductImage().setImage(model.getProductImage());
        this.view.getAddToCartButton().setDisable(true);

        //if stocks is 0
        if (this.model.getProductAvailability() == 0){
            this.view.getProductAvailabilityLabel().setText("out of stock");
            this.view.getProductAvailabilityLabel().setTextFill(Color.RED);
            this.view.getDecrementButton().setDisable(true);
            this.view.getIncrementButton().setDisable(true);
            this.view.getAddToCartButton().setDisable(true);
            this.view.getTotalPriceLabel().setText("out of stock");
            this.view.getTotalPriceLabel().setTextFill(Color.RED);
            this.view.getNoticeLabel().setText("out of stock");
            this.view.getNoticeLabel().setVisible(true);
        }else {
            this.view.getTotalPriceLabel().setText("₱ 0" + model.getTotal());
            this.view.getProductAvailabilityLabel().setText("Item Availability: " + model.getProductAvailability());
        }

        //set up the data of the view
        this.view.getQuantityLabel().setText(String.valueOf(model.getOrderedQuantity()));
        this.view.getTotalPriceLabel().setText("₱ 0" + model.getTotal());

        //set up action for add to cart button
        this.view.setActionAddToCartButton((ActionEvent event) ->{
            finalOrderedQuantity = model.getOrderedQuantity();
            finalOrderedPrice = Double.parseDouble(model.getTotal());
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.close();
        });

        //set up action for increment button
        this.view.setActionIncrementButton((ActionEvent event) ->{
            if (model.incrementQuantity() != null){
                this.view.getAddToCartButton().setDisable(false);
                String qty = String.valueOf(model.getOrderedQuantity());
                String totalPrice = ("₱ " + model.getTotal());

                view.getQuantityLabel().setText(qty);
                view.getTotalPriceLabel().setText(totalPrice);

                //check the updated the stock availability
                this.view.getProductAvailabilityLabel().setText("Item Availability: " + model.getProductAvailability());
            }else {
                this.view.getNoticeLabel().setText("amount exceeded");
                this.view.getNoticeLabel().setVisible(true);
            }
        });

        //set up action for decrement button
        this.view.setActionDecrementButton((ActionEvent event) ->{
            this.view.getNoticeLabel().setVisible(false);
            if (model.decrementQuantity() != null){
                String qty = String.valueOf(model.getOrderedQuantity());
                String totalPrice = ("₱ " + model.getTotal());

                view.getQuantityLabel().setText(qty);
                view.getTotalPriceLabel().setText(totalPrice);

                //check the updated stock availability
                this.view.getProductAvailabilityLabel().setText("Item Availability: " + model.getProductAvailability());
                if (model.getOrderedQuantity() == 0){
                    this.view.getAddToCartButton().setDisable(true);
                    this.view.getTotalPriceLabel().setText("₱ 0" + model.getTotal());
                }
            }else {
                this.view.getNoticeLabel().setText("no amount below 0");
                this.view.getAddToCartButton().setDisable(true);
                this.view.getNoticeLabel().setVisible(true);
            }
        });
    }

    public SelectFoodModel getModel() {
        return model;
    }

    public void setModel(SelectFoodModel model) {
        this.model = model;
    }

    public SelectFoodView getView() {
        return view;
    }

    public void setView(SelectFoodView view) {
        this.view = view;
    }

    public int getFinalOrderedQuantity() {
        return finalOrderedQuantity;
    }

    public double getFinalOrderedPrice() {
        return finalOrderedPrice;
    }

}



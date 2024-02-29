package client.controller.fxmlcontroller;

import client.model.fxmlmodel.SelectBeverageVariationModel;
import client.view.fxmlview.SelectBeverageVariationView;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SelectBeverageVariationController {
    private SelectBeverageVariationModel model;
    private SelectBeverageVariationView view;
    private int finalSmallOrderedQuantity; //used for add to cart button not the close button
    private int finalMediumOrderedQuantity; //used for add to cart button not the close button
    private int finalLargeOrderedQuantity; //used for add to cart button not the close button
    private double finalSmallOrderedPrice; //used for add to cart button not the close button
    private double finalMediumOrderedPrice; //used for add to cart button not the close button
    private double finalLargeOrderedPrice; //used for add to cart button not the close button

    public SelectBeverageVariationController(SelectBeverageVariationModel model, SelectBeverageVariationView view){
        this.view = view;
        this.model = model;

        //setting up the view
        this.view.getProductNameLabel().setText(model.getProductName());
        this.view.getProductDescriptionLabel().setText(model.getProductDetails());
        this.view.getProductPriceLabel().setText("₱ " + model.getSmallPrice() + " - " + "₱ " + model.getLargePrice());
        this.view.getProductAvailabilityLabel().setText("Item Availability: " + (model.getSmallAvailability() + model.getMediumAvailability() + model.getLargeAvailability()));
        this.view.getProductImage().setImage(model.getProductImage());
        this.view.getAddToCartButton().setDisable(true);

        //setting up data below
        this.view.getSmallStockLabel().setText("Availability: " + model.getSmallAvailability());
        this.view.getMediumStockLabel().setText("Availability: " + model.getMediumAvailability());
        this.view.getLargeStockLabel().setText("Availability: " + model.getLargeAvailability());

        //setting the prices below
        this.view.getSmallTotalPriceLabel().setText("₱ 0" + model.getSmallTotal());
        this.view.getMediumTotalPriceLabel().setText("₱ 0" + model.getMediumTotal());
        this.view.getLargeTotalPriceLabel().setText("₱ 0" + model.getLargeTotal());

        //setting the quantity below
        this.view.getSmallQuantityLabel().setText(String.valueOf(model.getSmallOrderedQuantity()));
        this.view.getMediumQuantityLabel().setText(String.valueOf(model.getMediumOrderedQuantity()));
        this.view.getLargeQuantityLabel().setText(String.valueOf(model.getLargeOrderedQuantity()));

        //if a variation is 0
        if (this.model.getSmallAvailability() == 0){
            this.view.getSmallStockLabel().setText("out of stock");
            this.view.getSmallStockLabel().setTextFill(Color.RED);
            this.view.getSmallDecrementButton().setDisable(true);
            this.view.getSmallIncrementButton().setDisable(true);
            this.view.getSmallTotalPriceLabel().setText("out of stock");
            this.view.getSmallTotalPriceLabel().setTextFill(Color.RED);
        }

        if (this.model.getMediumAvailability() == 0){
            this.view.getMediumStockLabel().setText("out of stock");
            this.view.getMediumStockLabel().setTextFill(Color.RED);
            this.view.getMediumDecrementButton().setDisable(true);
            this.view.getMediumIncrementButton().setDisable(true);
            this.view.getMediumTotalPriceLabel().setText("out of stock");
            this.view.getMediumTotalPriceLabel().setTextFill(Color.RED);
        }

        if (this.model.getLargeAvailability() == 0) {
            this.view.getLargeStockLabel().setText("out of stock");
            this.view.getLargeStockLabel().setTextFill(Color.RED);
            this.view.getLargeDecrementButton().setDisable(true);
            this.view.getLargeIncrementButton().setDisable(true);
            this.view.getLargeTotalPriceLabel().setText("out of stock");
            this.view.getLargeTotalPriceLabel().setTextFill(Color.RED);
        }

        //all sizes dont have stocks
        if (this.model.getSmallAvailability() == 0 && this.model.getMediumAvailability() == 0 && this.model.getLargeAvailability() == 0){
            this.view.getProductAvailabilityLabel().setText("out of stock");
            this.view.getProductAvailabilityLabel().setTextFill(Color.RED);
            this.view.getAddToCartButton().setDisable(true);
            this.view.getNoticeLabel().setText("out of stock on all sizes");
            this.view.getNoticeLabel().setVisible(true);
        }

        //set up action for add to cart button
        this.view.setActionAddToCartButton((ActionEvent event) ->{
            if (!view.getSmallCheckBox().isSelected() && !view.getMediumCheckBox().isSelected() && !view.getLargeCheckBox().isSelected()) { //there are no selected checkboxes
                this.view.getNoticeLabel().setText("click the checkboxes");
                this.view.getNoticeLabel().setVisible(true);
            }else {
                if (view.getSmallCheckBox().isSelected()){
                    finalSmallOrderedQuantity = model.getSmallOrderedQuantity();
                    finalSmallOrderedPrice = Double.parseDouble(model.getSmallTotal());
                }

                if (view.getMediumCheckBox().isSelected()){
                    finalMediumOrderedQuantity = model.getMediumOrderedQuantity();
                    finalMediumOrderedPrice = Double.parseDouble(model.getMediumTotal());
                }

                if (view.getLargeCheckBox().isSelected()){
                    finalLargeOrderedQuantity = model.getLargeOrderedQuantity();
                    finalLargeOrderedPrice = Double.parseDouble(model.getLargeTotal());
                }
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.close();
            }
        });

        //set up action for increment small button
        this.view.setActionSmallIncrementButton((ActionEvent event) ->{
            if (model.incrementSmallQuantity() != null){
                this.view.getAddToCartButton().setDisable(false);
                String qty = String.valueOf(model.getSmallOrderedQuantity());
                String totalPrice = ("₱ " + model.getSmallTotal());

                view.getSmallQuantityLabel().setText(qty);
                view.getSmallTotalPriceLabel().setText(totalPrice);

                //check the updated the stock availability
                this.view.getSmallStockLabel().setText("Availability: " + model.getSmallAvailability());
                this.view.getProductAvailabilityLabel().setText("Item Availability: " + (model.getSmallAvailability() + model.getMediumAvailability() + model.getLargeAvailability()));
            }else {
                this.view.getNoticeLabel().setText("amount exceeded");
                this.view.getNoticeLabel().setVisible(true);
            }
        });

        //set up action for small decrement button
        this.view.setActionSmallDecrementButton((ActionEvent event) ->{
            this.view.getNoticeLabel().setVisible(false);
            if (model.decrementSmallQuantity() != null){
                String qty = String.valueOf(model.getSmallOrderedQuantity());
                String totalPrice = ("₱ " + model.getSmallTotal());

                view.getSmallQuantityLabel().setText(qty);
                view.getSmallTotalPriceLabel().setText(totalPrice);

                //check the updated stock availability
                this.view.getSmallStockLabel().setText("Availability: " + model.getSmallAvailability());


                if (model.getSmallOrderedQuantity() == 0){
                    this.view.getSmallTotalPriceLabel().setText("₱ 0" + model.getSmallTotal());
                    if (model.getSmallOrderedQuantity() == 0 && model.getMediumOrderedQuantity() == 0){
                        this.view.getAddToCartButton().setDisable(true);
                    }
                }
                this.view.getProductAvailabilityLabel().setText("Item Availability: " + (model.getSmallAvailability() + model.getMediumAvailability() + model.getLargeAvailability()));
            }else {
                this.view.getNoticeLabel().setText("amount not below 0");
                this.view.getNoticeLabel().setVisible(true);
            }
        });

        //set up action for increment medium button
        this.view.setActionMediumIncrementButton((ActionEvent event) ->{
            if (model.incrementMediumQuantity() != null){
                this.view.getAddToCartButton().setDisable(false);
                String qty = String.valueOf(model.getMediumOrderedQuantity());
                String totalPrice = ("₱ " + model.getMediumTotal());

                view.getMediumQuantityLabel().setText(qty);
                view.getMediumTotalPriceLabel().setText(totalPrice);

                //check the updated the stock availability
                this.view.getMediumStockLabel().setText("Availability: " + model.getMediumAvailability());
                this.view.getProductAvailabilityLabel().setText("Item Availability: " + (model.getSmallAvailability() + model.getMediumAvailability() + model.getLargeAvailability()));
            }else {
                this.view.getNoticeLabel().setText("amount exceeded");
                this.view.getNoticeLabel().setVisible(true);
            }
        });

        //set up action for medium decrement button
        this.view.setActionMediumDecrementButton((ActionEvent event) ->{
            this.view.getNoticeLabel().setVisible(false);
            if (model.decrementMediumQuantity() != null){
                String qty = String.valueOf(model.getMediumOrderedQuantity());
                String totalPrice = ("₱ " + model.getMediumTotal());

                view.getMediumQuantityLabel().setText(qty);
                view.getMediumTotalPriceLabel().setText(totalPrice);

                //check the updated stock availability
                this.view.getMediumStockLabel().setText("Availability: " + model.getMediumAvailability());

                if (model.getMediumOrderedQuantity() == 0){
                    this.view.getMediumTotalPriceLabel().setText("₱ 0" + model.getMediumTotal());
                    if (model.getSmallOrderedQuantity() == 0 && model.getMediumOrderedQuantity() == 0 && model.getLargeOrderedQuantity() == 0){
                        this.view.getAddToCartButton().setDisable(true);
                    }
                }
                this.view.getProductAvailabilityLabel().setText("Item Availability: " + (model.getSmallAvailability() + model.getMediumAvailability() + model.getLargeAvailability()));
            }else {
                this.view.getNoticeLabel().setText("amount not below 0");
                this.view.getNoticeLabel().setVisible(true);
            }
        });

        //set up action for increment large button
        this.view.setActionLargeIncrementButton((ActionEvent event) ->{
            if (model.incrementLargeQuantity() != null){
                this.view.getAddToCartButton().setDisable(false);
                String qty = String.valueOf(model.getLargeOrderedQuantity());
                String totalPrice = ("₱ " + model.getLargeTotal());

                view.getLargeQuantityLabel().setText(qty);
                view.getLargeTotalPriceLabel().setText(totalPrice);

                //check the updated the stock availability
                this.view.getLargeStockLabel().setText("Availability: " + model.getLargeAvailability());
                this.view.getProductAvailabilityLabel().setText("Item Availability: " + (model.getSmallAvailability() + model.getMediumAvailability() + model.getLargeAvailability()));
            }else {
                this.view.getNoticeLabel().setText("amount exceeded");
                this.view.getNoticeLabel().setVisible(true);
            }
        });

        //set up action for large decrement button
        this.view.setActionLargeDecrementButton((ActionEvent event) ->{
            this.view.getNoticeLabel().setVisible(false);
            if (model.decrementLargeQuantity() != null){
                String qty = String.valueOf(model.getLargeOrderedQuantity());
                String totalPrice = ("₱ " + model.getLargeTotal());

                view.getLargeQuantityLabel().setText(qty);
                view.getLargeTotalPriceLabel().setText(totalPrice);

                //check the updated stock availability
                this.view.getLargeStockLabel().setText("Availability: " + model.getLargeAvailability());


                if (model.getLargeOrderedQuantity() == 0){
                    this.view.getLargeTotalPriceLabel().setText("₱ 0" + model.getLargeTotal());
                    if (model.getSmallOrderedQuantity() == 0 && model.getMediumOrderedQuantity() == 0 && model.getLargeOrderedQuantity() == 0){
                        this.view.getAddToCartButton().setDisable(true);
                    }
                }
                this.view.getProductAvailabilityLabel().setText("Item Availability: " + (model.getSmallAvailability() + model.getMediumAvailability() + model.getLargeAvailability()));
            }else {
                this.view.getNoticeLabel().setText("amount not below 0");
                this.view.getNoticeLabel().setVisible(true);
            }
        });
    }

    public SelectBeverageVariationModel getModel() {
        return model;
    }

    public void setModel(SelectBeverageVariationModel model) {
        this.model = model;
    }

    public SelectBeverageVariationView getView() {
        return view;
    }

    public void setView(SelectBeverageVariationView view) {
        this.view = view;
    }

    public int getFinalSmallOrderedQuantity() {
        return finalSmallOrderedQuantity;
    }

    public int getFinalMediumOrderedQuantity() {
        return finalMediumOrderedQuantity;
    }

    public int getFinalLargeOrderedQuantity() {
        return finalLargeOrderedQuantity;
    }

    public double getFinalSmallOrderedPrice() {
        return finalSmallOrderedPrice;
    }

    public double getFinalMediumOrderedPrice() {
        return finalMediumOrderedPrice;
    }

    public double getFinalLargeOrderedPrice() {
        return finalLargeOrderedPrice;
    }

}

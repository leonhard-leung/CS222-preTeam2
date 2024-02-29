package client.controller.fxmlcontroller;

import client.model.fxmlmodel.CheckoutItemCardModel;
import client.model.fxmlmodel.CheckoutPageModel;
import client.view.fxmlview.CheckoutPageView;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import shared.Beverage;
import shared.Food;
import shared.Product;

import java.util.List;
import java.util.Random;

public class CheckoutPageController {
    private CheckoutPageModel model;
    private CheckoutPageView view;

    public CheckoutPageController(CheckoutPageModel model, CheckoutPageView view){
        this.model = model;
        this.view = view;

        //set up the grid pane from the client's cart
        populateGridPane();

        //setting up the view
        setUpView();
    }

    /**This method populates the grid pane in the checkout page*/
    private void populateGridPane() {
        List<Product> cart = model.getCart();
        int column = 0;
        int row = 1;
        try {
            for (Product product: cart) {
                //load first
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/client/checkout_item_card.fxml"));

                //putting the card on the anchorPane
                Pane card = loader.load();

                if (product instanceof Food food){
                    CheckoutItemCardController cardOnCheckout = new CheckoutItemCardController(new CheckoutItemCardModel(food), loader.getController());
                    cardOnCheckout.setData();
                }else if (product instanceof Beverage beverage){
                    CheckoutItemCardController cardOnCheckout = new CheckoutItemCardController(new CheckoutItemCardModel(beverage), loader.getController());
                    cardOnCheckout.setData();
                }

                if (column == 1){
                    column = 0;
                    row++;
                }
                view.getGridPaneCartOnCheckOut().add(card, column++, row);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setUpView() {
        this.view.getClientName().setText(model.getCustomer().getName());
        this.view.getClientAddress().setText(model.getCustomer().getAddress());

        //randomize the delivery payment
        // Initialize a Random object
        Random random = new Random();

        // Generate a random integer between 0 and 14 (15 intervals)
        int interval = random.nextInt(4);

        // Calculate the deliveryPayment within the specified range and intervals
        double deliveryPayment = 8 + interval * 10;

        this.view.getSubtotalPriceLabel().setText("₱ " + model.getSubTotal() + "0");
        this.view.getDeliveryFeeLabel().setText("₱ " + deliveryPayment + "0");
        double totalAmount = model.getSubTotal() + deliveryPayment;
        this.view.getTotalAmountLabel().setText("₱ " + totalAmount + "0");
    }

    public CheckoutPageModel getModel() {
        return model;
    }

    public void setModel(CheckoutPageModel model) {
        this.model = model;
    }

    public CheckoutPageView getView() {
        return view;
    }

    public void setView(CheckoutPageView view) {
        this.view = view;
    }
}

package client.controller.fxmlcontroller;

import client.model.fxmlmodel.OrderHistoryCardModel;
import client.model.fxmlmodel.OrderHistoryPageModel;
import client.view.fxmlview.OrderHistoryPageView;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import shared.Beverage;
import shared.Food;
import shared.Product;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryPageController {
    private OrderHistoryPageModel model;
    private OrderHistoryPageView view;
    private List<Product> ratedProducts;

    public OrderHistoryPageController(OrderHistoryPageModel model, OrderHistoryPageView view){
        this.model =model;
        this.view = view;

        //set up the grid pane
        populateGridPane();

        //set up action for cancel button
        setUpActionCancelButton();
    }

    private void setUpActionCancelButton() {
        this.view.getCancelButton().setOnAction(event -> {
            ratedProducts = null;
            view.closeCheckoutView();
        });
    }

    /**This method gets all the rating from the elements in the grid pane*/
    //todo:
    public void submitRatedProducts(){
        ObservableList<Node> orderHistoryProducts = this.view.getGridPaneOrderHistory().getChildren();
        List<Product> ratedProducts = new ArrayList<>();

        //for each card
        for (Node product: orderHistoryProducts) {
            String name = null;
            char type = ' ';
            double review = 0;

            Pane productPane = (Pane) product;
            for (Node productPart: productPane.getChildren()) {
                if (productPart instanceof Label){
                    switch (productPart.getId()){
                        case "productNameLabel":
                            name = ((Label) productPart).getText();
                            break;
                        case "typeLabel":
                            String data = ((Label) productPart).getText();
                            String cleanedData = data.replaceAll("[Type:\\s]", "");
                            type = cleanedData.toLowerCase().charAt(0);
                            break;
                    }
                }

                if (productPart instanceof HBox){
                    int counter = 0;
                    for (Node star: ((HBox) productPart).getChildren()) {
                        if (star instanceof ToggleButton toggleButton) {
                            boolean isSelected = toggleButton.isSelected();
                            if (isSelected) {
                                counter++;
                            }
                        }
                    }
                    review = counter;
                }
            }
            if (type == 'f' && review > 0){
                Food food = new Food(name, type, review);
                ratedProducts.add(food);
            }else if (type == 'b' && review > 0){
                Beverage beverage = new Beverage(name, type, review);
                ratedProducts.add(beverage);
            }
        }
        this.ratedProducts = ratedProducts;
    }

    /**This method populates the grid pane inside the customer order history*/
    private void populateGridPane() {
        List<Product> productList = model.getProductList();
        int column = 0;
        int row = 1;
        try {
            for (Product product : productList) {
                //load first
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/client/order_history_card.fxml"));

                //putting the card on the anchorPane
                Pane card = loader.load();

                if (product instanceof Food food) {
                    OrderHistoryCardController cardOnHistoryPage = new OrderHistoryCardController(new OrderHistoryCardModel(food), loader.getController());
                    cardOnHistoryPage.setData();
                } else if (product instanceof Beverage beverage) {
                    OrderHistoryCardController cardOnHistoryPage = new OrderHistoryCardController(new OrderHistoryCardModel(beverage), loader.getController());
                    cardOnHistoryPage.setData();
                }

                if (column == 1) {
                    column = 0;
                    row++;
                }
                view.getGridPaneOrderHistory().add(card, column++, row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public OrderHistoryPageModel getModel() {
        return model;
    }

    public void setModel(OrderHistoryPageModel model) {
        this.model = model;
    }

    public OrderHistoryPageView getView() {
        return view;
    }

    public void setView(OrderHistoryPageView view) {
        this.view = view;
    }

    public List<Product> getRatedProducts() {
        return ratedProducts;
    }

    public void setRatedProducts(List<Product> ratedProducts) {
        this.ratedProducts = ratedProducts;
    }
}

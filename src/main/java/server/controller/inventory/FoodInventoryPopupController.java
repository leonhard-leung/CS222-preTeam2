package server.controller.inventory;

import server.model.inventory.FoodInventoryPopupModel;
import server.view.inventory.FoodInventoryPopupView;

public class FoodInventoryPopupController {
    FoodInventoryPopupModel model;
    FoodInventoryPopupView view;

    public FoodInventoryPopupController(FoodInventoryPopupModel model, FoodInventoryPopupView view) {
        this.model = model;
        this.view = view;
    }

    public void displayContents() {
        view.setTotalQuantity("total: " + model.getTotalQuantity());
    } // end of displayContents

    public void setComponentAction() {
        view.getIncrementButton().setOnAction(actionEvent -> {
            model.setValue(Integer.parseInt(view.getQuantity().getText()) + 1);
            model.getTotalQuantity().getAndIncrement();
            view.setQuantity(String.valueOf(model.getValue()));
            view.setTotalQuantity("total: " + model.getTotalQuantity());
        });

        view.getDecrementButton().setOnAction(actionEvent -> {
            model.setValue(Integer.parseInt(view.getQuantity().getText()) - 1);
            model.getTotalQuantity().getAndDecrement();
            view.setQuantity(String.valueOf(model.getValue()));
            view.setTotalQuantity("total: " + model.getTotalQuantity());
        });
    } // end of setComponentAction
} // end of FoodInventoryPopupController class

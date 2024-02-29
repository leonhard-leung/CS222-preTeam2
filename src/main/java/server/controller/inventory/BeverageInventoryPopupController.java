package server.controller.inventory;

import server.model.inventory.BeverageInventoryPopupModel;
import server.view.inventory.BeverageInventoryPopupView;

public class BeverageInventoryPopupController {
    BeverageInventoryPopupModel model;
    BeverageInventoryPopupView view;

    public BeverageInventoryPopupController(BeverageInventoryPopupModel model, BeverageInventoryPopupView view) {
        this.model = model;
        this.view = view;
    }

    public void displayContents() {
        view.setTotalSmallQuantityLabel("total: " + model.getSmallTotalQuantity());
        view.setTotalMediumQuantityLabel("total: " + model.getMediumTotalQuantity());
        view.setTotalLargeQuantityLabel("total: " + model.getLargeTotalQuantity());
    } // end of displayContents

    public void setComponentAction() {
        view.getSmallIncrementButton().setOnAction(actionEvent -> {
            model.setSmallValue(Integer.parseInt(view.getSmallQuantity().getText()) + 1);
            model.getSmallTotalQuantity().getAndIncrement();
            view.setSmallQuantity(String.valueOf(model.getSmallValue()));
            view.setTotalSmallQuantityLabel("total: " + model.getSmallTotalQuantity());
        });

        view.getMediumIncrementButton().setOnAction(actionEvent -> {
            model.setMediumValue(Integer.parseInt(view.getMediumQuantity().getText()) + 1);
            model.getMediumTotalQuantity().getAndIncrement();
            view.setMediumQuantity(String.valueOf(model.getMediumValue()));
            view.setTotalMediumQuantityLabel("total: " + model.getMediumTotalQuantity());
        });

        view.getLargeIncrementButton().setOnAction(actionEvent -> {
            model.setLargeValue(Integer.parseInt(view.getLargeQuantity().getText()) + 1);
            model.getLargeTotalQuantity().getAndIncrement();
            view.setLargeQuantity(String.valueOf(model.getLargeValue()));
            view.setTotalLargeQuantityLabel("total: " + model.getLargeTotalQuantity());
        });

        view.getSmallDecrementButton().setOnAction(actionEvent -> {
            model.setSmallValue(Integer.parseInt(view.getSmallQuantity().getText()) - 1);
            model.getSmallTotalQuantity().getAndDecrement();
            view.setSmallQuantity(String.valueOf(model.getSmallValue()));
            view.setTotalSmallQuantityLabel("total: " + model.getSmallTotalQuantity());
        });

        view.getMediumDecrementButton().setOnAction(actionEvent -> {
            model.setMediumValue(Integer.parseInt(view.getMediumQuantity().getText()) - 1);
            model.getMediumTotalQuantity().getAndDecrement();
            view.setMediumQuantity(String.valueOf(model.getMediumValue()));
            view.setTotalMediumQuantityLabel("total: " + model.getMediumTotalQuantity());
        });

        view.getLargeDecrementButton().setOnAction(actionEvent -> {
            model.setLargeValue(Integer.parseInt(view.getLargeQuantity().getText()) - 1);
            model.getLargeTotalQuantity().getAndDecrement();
            view.setLargeQuantity(String.valueOf(model.getLargeValue()));
            view.setTotalLargeQuantityLabel("total: " + model.getLargeTotalQuantity());
        });
    } // end of setComponentAction
} // end of BeverageInventoryPopupController class

package client.controller.fxmlcontroller;

import client.model.fxmlmodel.CartItemCardModel;
import client.view.fxmlview.CartItemCardView;

public class CartItemCardController {
    private CartItemCardView view;
    private CartItemCardModel model;

    public CartItemCardController(CartItemCardModel model, CartItemCardView view){
        this.model = model;
        this.view = view;
    }

    public void setData(){
        this.view.getProductNameLabel().setText(model.getProductName());
        this.view.getQuantityLabel().setText("qty: " + model.getProductQuantity());

        if (model.getSize() != null){
            this.view.getSizeLabel().setText("size: " + model.getSize());
            this.view.getSizeLabel().setVisible(true);
        }
        this.view.getPriceLabel().setText("₱ " + model.getTotalPrice() + "0");
    }

    public CartItemCardView getView() {
        return view;
    }

    public void setView(CartItemCardView view) {
        this.view = view;
    }

    public CartItemCardModel getModel() {
        return model;
    }

    public void setModel(CartItemCardModel model) {
        this.model = model;
    }
}

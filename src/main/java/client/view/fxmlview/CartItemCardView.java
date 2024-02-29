package client.view.fxmlview;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CartItemCardView {

    @FXML
    private Label priceLabel;

    @FXML
    private Label productNameLabel;

    @FXML
    private Label quantityLabel;


    @FXML
    private Label sizeLabel;

    public Label getPriceLabel() {
        return priceLabel;
    }

    public Label getProductNameLabel() {
        return productNameLabel;
    }

    public Label getQuantityLabel() {
        return quantityLabel;
    }


    public Label getSizeLabel() {
        return sizeLabel;
    }
}

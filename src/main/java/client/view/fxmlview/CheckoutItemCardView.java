package client.view.fxmlview;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class CheckoutItemCardView {

    @FXML
    private ImageView itemImage;

    @FXML
    private Label itemName;

    @FXML
    private Label itemPriceLabel;

    @FXML
    private Label itemQuantity;

    @FXML
    private Label itemSizeLabel;

    public ImageView getItemImage() {
        return itemImage;
    }

    public Label getItemName() {
        return itemName;
    }

    public Label getItemPriceLabel() {
        return itemPriceLabel;
    }

    public Label getItemQuantity() {
        return itemQuantity;
    }

    public Label getItemSizeLabel() {
        return itemSizeLabel;
    }
}

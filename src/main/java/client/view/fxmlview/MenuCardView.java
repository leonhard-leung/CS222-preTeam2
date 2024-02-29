package client.view.fxmlview;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MenuCardView {
    @FXML
    private Label productName;
    @FXML
    private Label productDescription;
    @FXML
    private Label productRating;
    @FXML
    private Label productPrice;
    @FXML
    private Label sizeLabel;
    @FXML
    private Button addProductButton;
    @FXML
    private ImageView productImage;

    public void setProductName(String value) {
        productName.setText(value);
    }

    public void setProductDescription(String value) {
        productDescription.setText(value);
    }

    public void setProductRating(String value) {
        productRating.setText(value);
    }

    public void setProductPrice(String value) {
        productPrice.setText(value);
    }

    public void setSizeLabel(String value) {
        sizeLabel.setText(value);
    }

    public void setProductImage(Image image) {
        productImage.setImage(image);
    }

    public Button getAddProductButton() {
        return addProductButton;
    }
} // end of MenuCardNewView

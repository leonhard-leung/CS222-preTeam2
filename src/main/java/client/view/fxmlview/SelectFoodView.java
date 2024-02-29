package client.view.fxmlview;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class SelectFoodView {
    @FXML
    private Label noticeLabel;

    @FXML
    private Button addToCartButton;

    @FXML
    private Button decrementButton;

    @FXML
    private Button incrementButton;

    @FXML
    private Label productAvailabilityLabel;

    @FXML
    private Label productDescriptionLabel;

    @FXML
    private ImageView productImage;

    @FXML
    private Label productNameLabel;

    @FXML
    private Label productPriceLabel;

    @FXML
    private Label quantityLabel;

    @FXML
    private Label totalPriceLabel;


    public void setActionAddToCartButton(EventHandler<ActionEvent> event) {
        addToCartButton.setOnAction(event);
    }

    public void setActionIncrementButton(EventHandler<ActionEvent> event) {
        incrementButton.setOnAction(event);
    }

    public void setActionDecrementButton(EventHandler<ActionEvent> event) {
        decrementButton.setOnAction(event);
    }

    public Button getAddToCartButton() {
        return addToCartButton;
    }

    public Button getDecrementButton() {
        return decrementButton;
    }

    public Button getIncrementButton() {
        return incrementButton;
    }

    public Label getProductAvailabilityLabel() {
        return productAvailabilityLabel;
    }

    public Label getProductDescriptionLabel() {
        return productDescriptionLabel;
    }

    public ImageView getProductImage() {
        return productImage;
    }

    public Label getProductNameLabel() {
        return productNameLabel;
    }

    public Label getProductPriceLabel() {
        return productPriceLabel;
    }

    public Label getQuantityLabel() {
        return quantityLabel;
    }

    public Label getTotalPriceLabel() {
        return totalPriceLabel;
    }

    public Label getNoticeLabel() {
        return noticeLabel;
    }

    public void addToCartButtonEntered() {
        addToCartButton.setStyle("-fx-background-color: #c7a97f;");
    }

    public void addToCartButtonExited() {
        addToCartButton.setStyle("-fx-background-color:  #A38157;");
    }

    public void incrementButtonEntered() {
        incrementButton.setStyle("-fx-background-color: #c7a97f");
    }

    public void incrementButtonExited() {
        incrementButton.setStyle("-fx-background-color: #A38157;");
    }

    public void decrementButtonEntered() {
        decrementButton.setStyle("-fx-background-color: #A38157;");
    }

    public void decrementButtonExited() {
        decrementButton.setStyle("-fx-background-color: #c7a97f");
    }
}

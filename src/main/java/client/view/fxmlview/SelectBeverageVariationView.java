package client.view.fxmlview;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class SelectBeverageVariationView {
    @FXML
    private Label smallTotalPriceLabel;
    @FXML
    private CheckBox smallCheckBox;

    @FXML
    private CheckBox mediumCheckBox;

    @FXML
    private CheckBox largeCheckBox;

    @FXML
    private Label mediumTotalPriceLabel;
    @FXML
    private Label largeTotalPriceLabel;
    @FXML
    private Label noticeLabel;
    @FXML
    private Button addToCartButton;

    @FXML
    private Button largeDecrementButton;

    @FXML
    private Button largeIncrementButton;

    @FXML
    private Label largeQuantityLabel;

    @FXML
    private Label largeStockLabel;

    @FXML
    private Button mediumDecrementButton;

    @FXML
    private Button mediumIncrementButton;

    @FXML
    private Label mediumQuantityLabel;

    @FXML
    private Label mediumStockLabel;

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
    private Button smallDecrementButton;

    @FXML
    private Button smallIncrementButton;


    @FXML
    private Label smallQuantityLabel;

    @FXML
    private Label smallStockLabel;

    public void setActionAddToCartButton(EventHandler<ActionEvent> event){
        addToCartButton.setOnAction(event);
    }

    public void setActionSmallDecrementButton(EventHandler<ActionEvent> event){
        smallDecrementButton.setOnAction(event);
    }
    public void setActionSmallIncrementButton(EventHandler<ActionEvent> event){
        smallIncrementButton.setOnAction(event);
    }

    public void setActionMediumDecrementButton(EventHandler<ActionEvent> event){
        mediumDecrementButton.setOnAction(event);
    }
    public void setActionMediumIncrementButton(EventHandler<ActionEvent> event){
        mediumIncrementButton.setOnAction(event);
    }

    public void setActionLargeDecrementButton(EventHandler<ActionEvent> event){
        largeDecrementButton.setOnAction(event);
    }
    public void setActionLargeIncrementButton(EventHandler<ActionEvent> event){
        largeIncrementButton.setOnAction(event);
    }


    public Button getAddToCartButton() {
        return addToCartButton;
    }

    public Button getLargeDecrementButton() {
        return largeDecrementButton;
    }

    public Button getLargeIncrementButton() {
        return largeIncrementButton;
    }

    public Label getLargeQuantityLabel() {
        return largeQuantityLabel;
    }

    public Label getLargeStockLabel() {
        return largeStockLabel;
    }

    public Button getMediumDecrementButton() {
        return mediumDecrementButton;
    }

    public Button getMediumIncrementButton() {
        return mediumIncrementButton;
    }

    public Label getMediumQuantityLabel() {
        return mediumQuantityLabel;
    }

    public Label getMediumStockLabel() {
        return mediumStockLabel;
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

    public Button getSmallDecrementButton() {
        return smallDecrementButton;
    }

    public Button getSmallIncrementButton() {
        return smallIncrementButton;
    }

    public Label getSmallQuantityLabel() {
        return smallQuantityLabel;
    }

    public Label getSmallStockLabel() {
        return smallStockLabel;
    }
    public Label getNoticeLabel() {
        return noticeLabel;
    }

    public Label getSmallTotalPriceLabel() {
        return smallTotalPriceLabel;
    }

    public Label getMediumTotalPriceLabel() {
        return mediumTotalPriceLabel;
    }
    public Label getLargeTotalPriceLabel() {
        return largeTotalPriceLabel;
    }

    public CheckBox getSmallCheckBox() {
        return smallCheckBox;
    }

    public CheckBox getMediumCheckBox() {
        return mediumCheckBox;
    }

    public CheckBox getLargeCheckBox() {
        return largeCheckBox;
    }

    public void smallIncrementButtonEntered() {
        smallIncrementButton.setStyle("-fx-background-color: #c7a97f");
    }

    public void smallIncrementButtonExited() {
        smallIncrementButton.setStyle("-fx-background-color: #A38157;");
    }

    public void mediumIncrementButtonEntered() {
        mediumIncrementButton.setStyle("-fx-background-color: #c7a97f");
    }

    public void mediumIncrementButtonExited() {
        mediumIncrementButton.setStyle("-fx-background-color: #A38157;");
    }

    public void largeIncrementButtonEntered() {
        largeIncrementButton.setStyle("-fx-background-color: #c7a97f");
    }

    public void largeIncrementButtonExited() {
        largeIncrementButton.setStyle("-fx-background-color: #A38157;");
    }

    public void smallDecrementButtonEntered() {
        smallDecrementButton.setStyle("-fx-background-color: #A38157;");
    }

    public void smallDecrementButtonExited() {
        smallDecrementButton.setStyle("-fx-background-color: #c7a97f");
    }

    public void mediumDecrementButtonEntered() {
        mediumDecrementButton.setStyle("-fx-background-color: #A38157;");
    }

    public void mediumDecrementButtonExited() {
        mediumDecrementButton.setStyle("-fx-background-color: #c7a97f");
    }

    public void largeDecrementButtonEntered() {
        largeDecrementButton.setStyle("-fx-background-color: #A38157;");
    }

    public void largeDecrementButtonExited() {
        largeDecrementButton.setStyle("-fx-background-color: #c7a97f");
    }


    public void addToCartButtonEntered() {
        addToCartButton.setStyle("-fx-background-color:#c7a97f;");
    }

    public void addToCartButtonExited() {
        addToCartButton.setStyle("-fx-background-color:  #A38157;");
    }
}

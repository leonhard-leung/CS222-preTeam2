package client.view.fxmlview;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainMenuClientPageView {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Button logoutButton;
    @FXML
    private Label dateLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private Label accountNameLabel;
    @FXML
    private ImageView cartImage;
    @FXML
    private Button clearCartButton;

    @FXML
    private Label cartLabel1;

    @FXML
    private Label cartLabel2;

    @FXML
    private Button checkoutButton;

    @FXML
    private GridPane gridPaneCart;

    @FXML
    private ScrollPane scrollPaneCart;
    @FXML
    private GridPane gridPaneMenu;
    @FXML
    private ScrollPane scrollPaneMenu;

    @FXML
    private Button mainMenuBeveragesButton;

    @FXML
    private Button mainMenuFoodButton;

    @FXML
    private Button orderHistoryMenuButton;

    @FXML
    private Label priceLabel;

    @FXML
    private Label productTypeLabel;

    @FXML
    private TextField productSearchBar;

    @FXML
    private Pane loadingIndicatorPanel;

    public void setActionClearCartButton(EventHandler<ActionEvent> event){
        clearCartButton.setOnAction(event);
    }

    public void setUpActionCheckoutButton(EventHandler<ActionEvent> event){
        checkoutButton.setOnAction(event);
    }

    public void setUpActionOrderHistoryButton(EventHandler<ActionEvent> event){
        orderHistoryMenuButton.setOnAction(event);
    }

    public void showOrderHistoryUI () throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/client/order_history_page.fxml"));
        root = loader.load();

        Stage selectVariationStage = new Stage();
        Scene selectVariationScene = new Scene(root);
        selectVariationStage.getIcons().add(new Image(getClass().getResource("/images/client/client_app_logo.png").toExternalForm()));

        selectVariationStage.initOwner(stage);

        selectVariationStage.initModality(Modality.APPLICATION_MODAL);

        selectVariationStage.initStyle(StageStyle.DECORATED);

        selectVariationStage.setScene(selectVariationScene);
        selectVariationStage.show();
    }

    public void showCheckoutUI (ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/client/checkout_page.fxml"));
        root = loader.load();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResource("/images/client/client_app_logo.png").toExternalForm()));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void checkoutButtonEntered(){
        checkoutButton.setStyle("-fx-background-color: lightgray;");
        checkoutButton.setTextFill(Paint.valueOf("Black"));
    }
    public void checkoutButtonExited(){
        checkoutButton.setStyle("-fx-background-color:  #A38157;");
        checkoutButton.setTextFill(Paint.valueOf("White"));
    }

    public void orderHistoryMenuButtonEntered(){
        orderHistoryMenuButton.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-radius: 3;");
        orderHistoryMenuButton.setTextFill(Paint.valueOf("black"));
    }
    public void orderHistoryMenuButtonExited(){
        orderHistoryMenuButton.setStyle("-fx-background-color:  #FFFFFF; -fx-border-color: #A38157; -fx-border-radius: 3;");
        orderHistoryMenuButton.setTextFill(Paint.valueOf("#A38157"));
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Parent getRoot() {
        return root;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }

    public ImageView getCartImage() {
        return cartImage;
    }

    public void setCartImage(ImageView cartImage) {
        this.cartImage = cartImage;
    }

    public Label getCartLabel1() {
        return cartLabel1;
    }

    public void setCartLabel1(Label cartLabel1) {
        this.cartLabel1 = cartLabel1;
    }

    public Label getCartLabel2() {
        return cartLabel2;
    }

    public GridPane getGridPaneCart() {
        return gridPaneCart;
    }

    public GridPane getGridPaneMenu() {
        return gridPaneMenu;
    }

    public Button getMainMenuBeveragesButton() {
        return mainMenuBeveragesButton;
    }

    public Button getMainMenuFoodButton() {
        return mainMenuFoodButton;
    }

    public Label getPriceLabel() {
        return priceLabel;
    }

    public Label getProductTypeLabel() {
        return productTypeLabel;
    }


    public Label getAccountNameLabel() {
        return accountNameLabel;
    }

    public void setDateLabel(String value) {
        dateLabel.setText(value);
    }

    public void setTimeLabel(String value) {
        timeLabel.setText(value);
    }

    public TextField getProductSearchBar() {
        return productSearchBar;
    }

    public Pane getLoadingIndicatorPanel() {
        return loadingIndicatorPanel;
    }

    public Button getLogoutButton() {
        return logoutButton;
    }
}

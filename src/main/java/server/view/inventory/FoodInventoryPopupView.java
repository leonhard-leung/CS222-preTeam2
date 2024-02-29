package server.view.inventory;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class FoodInventoryPopupView {

    @FXML
    private Label quantity;
    @FXML
    private Label totalQuantity;
    @FXML
    private Button incrementButton;
    @FXML
    private Button decrementButton;
    @FXML
    private Button acceptButton;
    private static Stage popupStage;

    public static <T> T loadFoodInventoryPopup() {
        try {
            FXMLLoader loader = new FXMLLoader(FoodInventoryPopupView.class.getResource("/fxml/server/inventory/food_inventory_popup.fxml"));
            popupStage = new Stage();
            popupStage.getIcons().add(new Image(FoodInventoryPopupView.class.getResource("/images/server/server_app_logo.png").toExternalForm()));
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Edit Food Quantity");
            popupStage.setScene(new Scene(loader.load()));
            popupStage.show();
            return loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    } // end of loadFoodInventoryPopup

    public void closePopupStage() {
        popupStage.close();
    } // end of closePopupStage

    public Label getQuantity() {
        return quantity;
    }

    public void setQuantity(String value) {
        quantity.setText(value);
    }

    public void setTotalQuantity(String value) {
        totalQuantity.setText(value);
    }

    public Button getIncrementButton() {
        return incrementButton;
    }

    public Button getDecrementButton() {
        return decrementButton;
    }

    public Button getAcceptButton() {
        return acceptButton;
    }

    public void setIncrementButtonMouseEntered() {
        incrementButton.setStyle("-fx-background-color: #9a7133;");
    }

    public void setDecrementButtonMouseEntered() {
        decrementButton.setStyle("-fx-background-color: #9a7133;");
    }

    public void setAcceptButtonMouseEntered() {
        acceptButton.setStyle("-fx-background-color: #71d079");
    }

    public void setIncrementButtonMouseExited() {
        incrementButton.setStyle("-fx-background-color: #634921;");
    }

    public void setDecrementButtonMouseExited() {
        decrementButton.setStyle("-fx-background-color: #634921;");
    }

    public void setAcceptButtonMouseExited() {
        acceptButton.setStyle("-fx-background-color: #5dae65");
    }
} // end of FoodInventoryPopupController class

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

public class BeverageInventoryPopupView {
    @FXML
    private Button acceptButton;
    @FXML
    private Button smallIncrementButton;
    @FXML
    private Button smallDecrementButton;
    @FXML
    private Button mediumIncrementButton;
    @FXML
    private Button mediumDecrementButton;
    @FXML
    private Button largeIncrementButton;
    @FXML
    private Button largeDecrementButton;
    @FXML
    private Label smallQuantity;
    @FXML
    private Label mediumQuantity;
    @FXML
    private Label largeQuantity;
    @FXML
    private Label totalSmallQuantityLabel;
    @FXML
    private Label totalMediumQuantityLabel;
    @FXML
    private Label totalLargeQuantityLabel;
    private static Stage popupStage;

    public static <T> T loadBeverageInventoryPopup() {
        try {
            FXMLLoader loader = new FXMLLoader(BeverageEditDetailsPopupView.class.getResource("/fxml/server/inventory/beverage_inventory_popup.fxml"));
            popupStage = new Stage();
            popupStage.getIcons().add(new Image(BeverageInventoryPopupView.class.getResource("/images/server/server_app_logo.png").toExternalForm()));
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Edit Beverage Quantity");
            popupStage.setScene(new Scene(loader.load()));
            popupStage.show();
            return loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    } // end of loadBeverageInventoryPopup

    public void closePopupStage() {
        popupStage.close();
    } // end of closePopupStage

    public Label getSmallQuantity() {
        return smallQuantity;
    }

    public Label getMediumQuantity() {
        return mediumQuantity;
    }

    public Label getLargeQuantity() {
        return largeQuantity;
    }

    public void setTotalSmallQuantityLabel(String value) {
        totalSmallQuantityLabel.setText(value);
    }

    public void setTotalMediumQuantityLabel(String value) {
        totalMediumQuantityLabel.setText(value);
    }

    public void setTotalLargeQuantityLabel(String value) {
        totalLargeQuantityLabel.setText(value);
    }

    public Button getAcceptButton() {
        return acceptButton;
    }

    public Button getSmallIncrementButton() {
        return smallIncrementButton;
    }

    public Button getMediumIncrementButton() {
        return mediumIncrementButton;
    }

    public Button getLargeIncrementButton() {
        return largeIncrementButton;
    }

    public Button getSmallDecrementButton() {
        return smallDecrementButton;
    }

    public Button getMediumDecrementButton() {
        return mediumDecrementButton;
    }

    public Button getLargeDecrementButton() {
        return largeDecrementButton;
    }

    public void setSmallQuantity(String amount) {
        smallQuantity.setText(amount);
    }

    public void setMediumQuantity(String amount) {
        mediumQuantity.setText(amount);
    }

    public void setLargeQuantity(String amount) {
        largeQuantity.setText(amount);
    }

    public void setSmallIncrementButtonMouseEntered() {
        smallIncrementButton.setStyle("-fx-background-color: #9a7133;");
    }

    public void setSmallDecrementButtonMouseEntered() {
        smallDecrementButton.setStyle("-fx-background-color: #9a7133;");
    }

    public void setMediumIncrementButtonMouseEntered() {
        mediumIncrementButton.setStyle("-fx-background-color: #9a7133;");
    }

    public void setMediumDecrementButtonMouseEntered() {
        mediumDecrementButton.setStyle("-fx-background-color: #9a7133;");
    }

    public void setLargeIncrementButtonMouseEntered() {
        largeIncrementButton.setStyle("-fx-background-color: #9a7133;");
    }

    public void setLargeDecrementButtonMouseEntered() {
        largeDecrementButton.setStyle("-fx-background-color: #9a7133;");
    }

    public void setAcceptButtonMouseEntered() {
        acceptButton.setStyle("-fx-background-color: #71d079");
    }

    public void setSmallIncrementButtonMouseExited() {
        smallIncrementButton.setStyle("-fx-background-color: #634921;");
    }

    public void setSmallDecrementButtonMouseExited() {
        smallDecrementButton.setStyle("-fx-background-color: #634921;");
    }

    public void setMediumIncrementButtonMouseExited() {
        mediumIncrementButton.setStyle("-fx-background-color: #634921;");
    }

    public void setMediumDecrementButtonMouseExited() {
        mediumDecrementButton.setStyle("-fx-background-color: #634921;");
    }

    public void setLargeIncrementButtonMouseExited() {
        largeIncrementButton.setStyle("-fx-background-color: #634921;");
    }

    public void setLargeDecrementButtonMouseExited() {
        largeDecrementButton.setStyle("-fx-background-color: #634921;");
    }

    public void setAcceptButtonMouseExited() {
        acceptButton.setStyle("-fx-background-color: #5dae65");
    }
} // end of BeverageInventorySizesPopupController

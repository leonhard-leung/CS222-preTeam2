package server.view.orders;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class OrderDetailsPopupView {
    @FXML
    private Label dateAndTimeLabel;
    @FXML
    private Label customerNameLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label totalPriceLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private TextArea orderSummaryTextArea;
    @FXML
    private Button closeButton;
    private static Stage popupStage;

    public static <T> T loadOrderDetailsPopup() {
        try {
            FXMLLoader loader = new FXMLLoader(OrderDetailsPopupView.class.getResource("/fxml/server/order/order_details_popup.fxml"));
            popupStage = new Stage();
            popupStage.getIcons().add(new Image(OrderDetailsPopupView.class.getResource("/images/server/server_app_logo.png").toExternalForm()));
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Order Details");
            popupStage.setScene(new Scene(loader.load()));
            popupStage.show();
            return loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    } // end of loadOrderDetailsPopup

    public void closePopupStage() {
        popupStage.close();
    } // end of closePopupStage

    public void setDateAndTimeLabel(String value) {
        dateAndTimeLabel.setText(value);
    }

    public void setCustomerNameLabel(String value) {
        customerNameLabel.setText(value);
    }

    public void setAddressLabel(String value) {
        addressLabel.setText(value);
    }

    public void setTotalPriceLabel(String value) {
        totalPriceLabel.setText(value);
    }

    public void setStatusLabel(String value) {
        statusLabel.setText(value);
    }

    public void setOrderSummaryTextArea(String value) {
        orderSummaryTextArea.setText(value);
    }

    public void setCloseButtonMouseEntered() {
        closeButton.setStyle("-fx-background-color: #71d079");
    }

    public void setCloseButtonMouseExited() {
        closeButton.setStyle("-fx-background-color: #5dae65");
    }

    public Button getCloseButton() {
        return closeButton;
    }
} // end of OrderInformationPopupView

package client.view.fxmlview;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class CheckoutPageView {
    @FXML
    private RadioButton cashOnDelivery;

    @FXML
    private Label clientAddress;

    @FXML
    private Label clientName;

    @FXML
    private Label deliveryFeeLabel;

    @FXML
    private GridPane gridPaneCartOnCheckOut;

    @FXML
    private Label noticeLabel;

    @FXML
    private RadioButton onlinePayment;

    @FXML
    private Button placeOrderButton;

    @FXML
    private Label subtotalPriceLabel;

    @FXML
    private Label totalAmountLabel;
    private static Stage popupStage;

    public static <T> T loadCheckoutPage() {
        try {
            FXMLLoader loader = new FXMLLoader(CheckoutPageView.class.getResource("/fxml/client/checkout_page.fxml"));
            popupStage = new Stage();
            popupStage.getIcons().add(new Image(CheckoutPageView.class.getResource("/images/client/client_app_logo.png").toExternalForm()));
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Checkout");
            popupStage.setScene(new Scene(loader.load()));
            popupStage.show();
            return loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    } // end of loadCheckoutPage

    public void closeCheckoutView() {
        popupStage.close();
    }

    public void placeOrderButtonEntered(){
        placeOrderButton.setStyle("-fx-background-color: #c7a97f;");
    }
    public void placeOrderButtonExited(){
        placeOrderButton.setStyle("-fx-background-color:  #A38157;");
    }

    public RadioButton getCashOnDelivery() {
        return cashOnDelivery;
    }

    public Label getClientAddress() {
        return clientAddress;
    }

    public Label getClientName() {
        return clientName;
    }

    public Label getDeliveryFeeLabel() {
        return deliveryFeeLabel;
    }

    public GridPane getGridPaneCartOnCheckOut() {
        return gridPaneCartOnCheckOut;
    }

    public RadioButton getOnlinePayment() {
        return onlinePayment;
    }

    public Button getPlaceOrderButton() {
        return placeOrderButton;
    }

    public Label getSubtotalPriceLabel() {
        return subtotalPriceLabel;
    }

    public Label getTotalAmountLabel() {
        return totalAmountLabel;
    }

    public Label getNoticeLabel() {
        return noticeLabel;
    }

    public void setNoticeLabel(String value) {
        noticeLabel.setText(value);
    }
}

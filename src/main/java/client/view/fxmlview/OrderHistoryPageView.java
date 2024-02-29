package client.view.fxmlview;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class OrderHistoryPageView {

    @FXML
    private Button cancelButton;
    @FXML
    private GridPane gridPaneOrderHistory;
    @FXML
    private ScrollPane scrollPaneOrderHistory;

    @FXML
    private Button submitReviewButton;
    private static Stage popupStage;

    public static <T> T loadCheckoutPage() {
        try {
            FXMLLoader loader = new FXMLLoader(CheckoutPageView.class.getResource("/fxml/client/order_history_page.fxml"));
            popupStage = new Stage();
            popupStage.getIcons().add(new Image(CheckoutPageView.class.getResource("/images/client/client_app_logo.png").toExternalForm()));
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Order History");
            popupStage.setScene(new Scene(loader.load()));
            popupStage.show();
            return loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    } // end of loadCheckoutPage

    public void closeCheckoutView() {
        this.getGridPaneOrderHistory().getChildren().clear(); //make sure to clear all the items in the pane before closing
        popupStage.close();
    }


    public GridPane getGridPaneOrderHistory() {
        return gridPaneOrderHistory;
    }

    public Button getSubmitReviewButton() {
        return submitReviewButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public static Stage getPopupStage() {
        return popupStage;
    }

    public static void setPopupStage(Stage popupStage) {
        OrderHistoryPageView.popupStage = popupStage;
    }


    public void submitReviewButtonEntered() {
        submitReviewButton.setStyle("-fx-background-color: #71d079;");
    }

    public void submitReviewButtonExited() {
        submitReviewButton.setStyle("-fx-background-color: #5dae65;");
    }

    public void cancelButtonEntered() {
        cancelButton.setStyle("-fx-background-color:  #D07179;");
    }

    public void cancelButtonExited() {
        cancelButton.setStyle("-fx-background-color:  #FF5A65;");
    }
}

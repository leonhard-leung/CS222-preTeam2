package server.view.accounts;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import shared.Order;
import shared.Product;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AccountInformationPopupView implements Initializable {
    @FXML
    private TableView<Order> orderTableView;
    @FXML
    private TableColumn<Order, String> ordersColumn;
    @FXML
    private TableColumn<Order, String> timestampColumn;
    @FXML
    private Button closeTabButton;
    @FXML
    private Label name;
    @FXML
    private Label username;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label total;
    private static Stage popupStage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ordersColumn.setCellValueFactory(cellData -> toStringOrder(cellData.getValue()));
        timestampColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTimeStamp()));
    } // end of initialize

    public static <T> T loadAccountInformationPopup() {
        try {
            FXMLLoader loader = new FXMLLoader(AccountInformationPopupView.class.getResource("/fxml/server/account/account_information_popup.fxml"));
            popupStage = new Stage();
            popupStage.getIcons().add(new Image(AccountInformationPopupView.class.getResource("/images/server/server_app_logo.png").toExternalForm()));
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Customer Information");
            popupStage.setScene(new Scene(loader.load()));
            popupStage.show();
            return loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    } // end of loadAccountInformationPopup

    public void closePopupStage() {
        popupStage.close();
    } // end of closePopupStage

    public void setName(String value) {
        name.setText(value);
    }

    public void setUsername(String value) {
        username.setText(value);
    }

    public void setAddress(String value) {
        address.setText(value);
    }

    public void setEmail(String value) {
        email.setText(value);
    }

    public void setTotal(String value) {
        total.setText(value);
    }

    public Button getCloseTabButton() {
        return closeTabButton;
    }

    public void populateTableFromList(List<Order> orderHistory) {
        ObservableList<Order> orderList = FXCollections.observableArrayList();

        orderList.addAll(orderHistory);

        orderTableView.setItems(orderList);
    } // end of populateTableFromList

    public StringProperty toStringOrder(Order order) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Product product : order.getOrders()) {
            stringBuilder.append(product.getName()).append(" (").append(product.getQuantity()).append(")").append("\n");
        }
        return new SimpleStringProperty(stringBuilder.toString());
    }

    public void setCloseTabButtonMouseEntered() {
        closeTabButton.setStyle("-fx-background-color: #a18a6d");
    }

    public void setCloseTabButtonMouseExited() {
        closeTabButton.setStyle("-fx-background-color: #b59c7a");
    }
} // end of ViewAccountInformationPopupController

package server.view.orders;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import shared.Order;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class OrderListPageView implements Initializable {
    @FXML
    private TableView<Order> orderTableView;
    @FXML
    private TableColumn<Order, Integer> orderIDColumn;
    @FXML
    private TableColumn<Order, String> dateAndTimeColumn;
    @FXML
    private TableColumn<Order, String> customerColumn;
    @FXML
    private TableColumn<Order, String> addressColumn;
    @FXML
    private TableColumn<Order, Double> totalColumn;
    @FXML
    private TableColumn<Order, String> statusColumn;
    @FXML
    private TableColumn<Object, Void> viewOrderColumn;
    @FXML
    private TableColumn<Object, Void> setStatusColumn;
    @FXML
    private TextField searchOrderTextField;
    private FilteredList<Order> filteredList;
    private ObservableList<Order> orderList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderIDColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getID()).asObject());
        dateAndTimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTimeStamp()));
        customerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getName()));
        addressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getAddress()));
        totalColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getTotalPrice()).asObject());
        statusColumn.setCellValueFactory(cellData -> {
            boolean status = cellData.getValue().isStatus();
            return new SimpleStringProperty(status ? "Confirmed" : "Pending");
        });

        searchOrderTextField.textProperty().addListener((observable, oldValue, newValue) -> filterTable(newValue));
    } // end of initialize

    public static <T> T loadOrderListPage(BorderPane borderPane) {
        try {
            FXMLLoader loader = new FXMLLoader(OrderListPageView.class.getResource("/fxml/server/order/order_list_page.fxml"));
            borderPane.setCenter(loader.load());
            return loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    } // end of loadOrderListPage

    public void populateTableFromList(List<Order> list) {
        orderList = FXCollections.observableArrayList(list);

        orderList.sort(Comparator.comparingInt(Order::getID));

        filteredList = new FilteredList<>(orderList, item -> true);
        orderTableView.setItems(filteredList);
    } // end of populateTableFromList

    private void filterTable(String searchText) {
        filteredList.setPredicate(item -> {
            if (searchText == null || searchText.isEmpty()) {
                return true;
            }

            return item.getCustomer().getName().toLowerCase().contains(searchText.toLowerCase()) || String.valueOf(item.getID()).contains(searchText.toLowerCase());
        });
        orderTableView.setItems(filteredList);
    } // end of filterTable

    public ObservableList<Order> getOrderList() {
        return orderList;
    }

    public FilteredList<Order> getFilteredList() {
        return filteredList;
    }

    public TableColumn<Object, Void> getViewOrderColumn() {
        return viewOrderColumn;
    }

    public TableColumn<Object, Void> getSetStatusColumn() {
        return setStatusColumn;
    }
} // end of OrderListPageView class

package server.view.analytics;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import shared.Beverage;
import shared.Food;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.HashMap;
import java.util.ResourceBundle;

public class AnalyticsPageView implements Initializable {
    @FXML
    private Label dateLabel;
    @FXML
    private Label salesLabel;
    @FXML
    private Label totalOrdersLabel;
    @FXML
    private TableView<Food> foodAnalyticsTableView;
    @FXML
    private TableView<Beverage> beverageAnalyticsTableView;
    @FXML
    private TableColumn<Food, String> foodProductsColumn;
    @FXML
    private TableColumn<Food, Integer> foodProductsOrderCountColumn;
    @FXML
    private TableColumn<Beverage, String > beverageProductsColumn;
    @FXML
    private TableColumn<Beverage, Integer> beverageProductsOrderCountColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        foodProductsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        foodProductsOrderCountColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAmountSold()).asObject());
        beverageProductsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        beverageProductsOrderCountColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAmountSold()).asObject());

    } // end of initialize

    public static <T> T loadAnalyticsPage(BorderPane borderPane) {
        try {
            FXMLLoader loader = new FXMLLoader(AnalyticsPageView.class.getResource("/fxml/server/analytics/analytics_page.fxml"));
            borderPane.setCenter(loader.load());
            return loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    } // end of loadAnalyticsPage

    public void populateTableFromMap(HashMap<String, Food> foodHashMap, HashMap<String, Beverage> beverageHashMap) {
        ObservableList<Food> foodList = FXCollections.observableArrayList(foodHashMap.values());
        ObservableList<Beverage> beverageList = FXCollections.observableArrayList(beverageHashMap.values());

        FilteredList<Food> filteredFoodList = new FilteredList<>(foodList, item -> true);
        FilteredList<Beverage> filteredBeverageList = new FilteredList<>(beverageList, item -> true);

        SortedList<Food> sortedFoodList = new SortedList<>(filteredFoodList, Comparator.comparingInt(Food::getAmountSold).reversed());
        SortedList<Beverage> sortedBeverageList = new SortedList<>(filteredBeverageList, Comparator.comparingInt(Beverage::getAmountSold).reversed());

        foodAnalyticsTableView.setItems(sortedFoodList);
        beverageAnalyticsTableView.setItems(sortedBeverageList);
    } // end of populateTableFromMap

    public void setDateLabel(String value) {
        dateLabel.setText(value);
    }

    public void setSalesLabel(String value) {
        salesLabel.setText(value);
    }

    public void setTotalOrdersLabel(String value) {
        totalOrdersLabel.setText(value);
    }
} // end of AnalyticsPageView

package server.view.inventory;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import shared.Beverage;
import shared.Food;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.HashMap;
import java.util.ResourceBundle;

public class InventoryPageView implements Initializable {
    @FXML
    private TableColumn<Object, String> productNameColumn;
    @FXML
    private TableColumn<Object, String> typeColumn;
    @FXML
    private TableColumn<Object, Integer> quantityColumn;
    @FXML
    private TableColumn<Object, Void> editQuantityColumn;
    @FXML
    private TableColumn<Object, Void> editDetailsColumn;
    @FXML
    private TableColumn<Object, Void> deleteProductColumn;
    @FXML
    private TableView<Object> inventoryTableView;
    @FXML
    private TextField searchInventoryTextField;
    @FXML
    private Button saveChangesButton;
    private FilteredList<Object> filteredList;
    private ObservableList<Object> productList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productNameColumn.setCellValueFactory(cellData -> {
            Object object = cellData.getValue();

            String name = "";
            if (object instanceof Food) {
                name = ((Food) object).getName();

            } else if (object instanceof Beverage) {
                name = ((Beverage) object).getName();
            }
            return new SimpleStringProperty(name);
        });

        typeColumn.setCellValueFactory(cellData -> {
            Object object = cellData.getValue();

            char type = ' ';
            if (object instanceof Food) {
                type = ((Food) object).getType();
            } else if (object instanceof Beverage) {
                type = ((Beverage) object).getType();
            }
            return new SimpleStringProperty(type == 'f' ? "food" : "beverage");
        });

        quantityColumn.setCellValueFactory(cellData -> {
            Object object = cellData.getValue();

            int quantity = 0;
            if (object instanceof Food) {
                quantity = ((Food) object).getQuantity();
            } else if (object instanceof Beverage) {
                quantity = ((Beverage) object).getQuantity();
            }
            return new SimpleIntegerProperty(quantity).asObject();
        });

        searchInventoryTextField.textProperty().addListener((observable, oldValue, newValue) -> filterTable(newValue));
    } // end of initialize

    public static <T> T loadInventoryPage(BorderPane borderPane) {
        try {
            FXMLLoader loader = new FXMLLoader(InventoryPageView.class.getResource("/fxml/server/inventory/inventory_page.fxml"));
            borderPane.setCenter(loader.load());
            return loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    } // end of loadInventoryPage

    public void populateTableFromMap(HashMap<String, Food> foodMenu, HashMap<String, Beverage> beverageMenu) {
        productList = FXCollections.observableArrayList();
        productList.addAll(foodMenu.values());
        productList.addAll(beverageMenu.values());

        productList.sort(Comparator.comparing(o -> {
            if (o instanceof Food food) {
                return food.getName();
            } else if (o instanceof Beverage beverage) {
                return beverage.getName();
            } else {
                return "";
            }
        }));

        filteredList = new FilteredList<>(productList, item -> true);

        inventoryTableView.setItems(filteredList);
    } // end of populateTableFromMap

    private void filterTable(String searchText) {
        filteredList.setPredicate(item -> {
            if (searchText == null || searchText.isEmpty()) {
                return true;
            }

            if (item instanceof Food food) {
                return food.getName().toLowerCase().contains(searchText.toLowerCase());
            } else if (item instanceof Beverage beverage) {
                return beverage.getName().toLowerCase().contains(searchText.toLowerCase());
            }
            return false;
        });
        inventoryTableView.setItems(filteredList);
    } // end of filterTable

    public Button getSaveChangesButton() {
        return saveChangesButton;
    }

    public ObservableList<Object> getProductList() {
        return productList;
    }

    public FilteredList<Object> getFilteredList() {
        return filteredList;
    }

    public TableColumn<Object, Void> getEditQuantityColumn() {
        return editQuantityColumn;
    }

    public TableColumn<Object, Void> getEditDetailsColumn() {
        return editDetailsColumn;
    }

    public TableColumn<Object, Void> getDeleteProductColumn() {
        return deleteProductColumn;
    }

    public void saveChangesButtonEntered() {
        saveChangesButton.setStyle("-fx-background-color: #c7a97f;");
    }

    public void saveChangesButtonExited() {
        saveChangesButton.setStyle("-fx-background-color: #A38157;");
    }
} // end of InventoryPageController class

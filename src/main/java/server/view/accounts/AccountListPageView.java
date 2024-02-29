package server.view.accounts;

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
import shared.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class AccountListPageView implements Initializable {
    @FXML
    private TableView<Customer> accountsTableView;
    @FXML
    private TableColumn<Customer, String> usernameColumn;
    @FXML
    private TableColumn<Customer, String> nameColumn;
    @FXML
    private TableColumn<Customer, String> addressColumn;
    @FXML
    private TableColumn<Customer, String> emailColumn;
    @FXML
    private TableColumn<Object, Void> viewInformationColumn;
    @FXML
    private TextField searchAccountTextField;
    private FilteredList<Customer> filteredList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        addressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));

        searchAccountTextField.textProperty().addListener((observable, oldValue, newValue) -> filterTable(newValue));
    } // end of initialize

    public static <T> T loadAccountListPage(BorderPane borderPane) {
        try {
            FXMLLoader loader = new FXMLLoader(AccountListPageView.class.getResource("/fxml/server/account/account_list_page.fxml"));
            borderPane.setCenter(loader.load());
            return loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    } // end of loadAccountListPage

    public void populateTableFromList(List<Customer> list) {
        ObservableList<Customer> accountList = FXCollections.observableArrayList(list);

        accountList.sort(Comparator.comparing(Customer::getName));

        filteredList = new FilteredList<>(accountList, item -> true);
        accountsTableView.setItems(filteredList);
    } // end of populateTableFromList

    private void filterTable(String searchText) {
        filteredList.setPredicate(item -> {
            if (searchText == null || searchText.isEmpty()) {
                return true;
            }

            return item.getName().toLowerCase().contains(searchText.toLowerCase());
        });
        accountsTableView.setItems(filteredList);
    } // end of filterTable

    public TableColumn<Object, Void> getViewInformationColumn() {
        return viewInformationColumn;
    }
} // end of AccountsListPageController class

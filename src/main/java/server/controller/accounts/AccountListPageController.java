package server.controller.accounts;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import server.model.accounts.AccountInformationPopupModel;
import server.model.accounts.AccountListPageModel;
import server.view.accounts.AccountInformationPopupView;
import server.view.accounts.AccountListPageView;
import shared.Customer;

public class AccountListPageController {
    AccountListPageModel model;
    AccountListPageView view;

    public AccountListPageController(AccountListPageModel model, AccountListPageView view) {
        this.model = model;
        this.view = view;
    }

    public void start() {
        view.populateTableFromList(model.getCustomerAccountList());
        setViewAccountInformationColumn();
    } // end of start

    private Button createButton() {
        Button button = new Button("view");
        button.setOnAction(this::handleViewAccountInformationButtonClick);
        return button;
    } // end of createButton

    private void setViewAccountInformationColumn() {
        Callback<TableColumn<Object, Void>, TableCell<Object, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Object, Void> call(final TableColumn<Object, Void> param) {
                return new TableCell<>() {
                    private final Button button = createButton();

                    {
                        button.setStyle("-fx-background-color:#b59c7a; -fx-text-fill: white;");
                        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #a18a6d; -fx-text-fill: white;"));
                        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #b59c7a; -fx-text-fill: white;"));
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(button);
                            setAlignment(Pos.CENTER);
                        }
                    }
                };
            }
        };
        view.getViewInformationColumn().setCellFactory(cellFactory);
    } // end of setViewAccountInformationColumn

    private void handleViewAccountInformationButtonClick(ActionEvent event) {
        Button button = (Button) event.getSource();
        TableCell<Customer, Void> cell = (TableCell<Customer, Void>) button.getParent();

        Customer customer = cell.getTableRow().getItem();

        AccountInformationPopupModel popupModel = new AccountInformationPopupModel();
        popupModel.setCustomer(customer);

        AccountInformationPopupView popupView = AccountInformationPopupView.loadAccountInformationPopup();

        AccountInformationPopupController popupController = new AccountInformationPopupController(popupModel, popupView);
        popupController.start();
    } // end of handleViewAccountInformationButtonClick
} // end of AccountListPageController

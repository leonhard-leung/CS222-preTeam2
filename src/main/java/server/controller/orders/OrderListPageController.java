package server.controller.orders;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import server.model.orders.OrderDetailsPopupModel;
import server.model.orders.OrderListPageModel;
import server.view.orders.OrderDetailsPopupView;
import server.view.orders.OrderListPageView;
import shared.Order;
import util.PushNotification;

public class OrderListPageController {
    private final OrderListPageModel model;
    private final OrderListPageView view;

    public OrderListPageController(OrderListPageModel model, OrderListPageView view) {
        this.model = model;
        this.view = view;
    }

    public void start() {
        view.populateTableFromList(model.getOrderList());
        setComponentActions();
    } // end of start

    private void setComponentActions() {
        setColumns("details", view.getViewOrderColumn(), 1);
        setColumns("status", view.getSetStatusColumn(), 2);
    } // end of setComponentActions

    private Button createButton(String label, int tableColumn) {
        Button button = new Button(label);
        if (tableColumn == 1) {
            button.setOnAction(this::handleViewOrderButtonClick);
        } else if (tableColumn == 2) {
            button.setOnAction(this::handleSetOrderStatusButtonClick);
        }
        return button;
    } // end of createButton

    private void setColumns(String label, TableColumn<Object, Void> tableColumn, int columnNumber) {
        Callback<TableColumn<Object, Void>, TableCell<Object, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Object, Void> call(final TableColumn<Object, Void> param) {
                return new TableCell<>() {
                    private final Button button = createButton(label, columnNumber);

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
        tableColumn.setCellFactory(cellFactory);
    } // end of setColumns

    private void handleViewOrderButtonClick(ActionEvent event) {
        Button button = (Button) event.getSource();
        TableCell<Object, Void> cell = (TableCell<Object, Void>) button.getParent();

        Order order = (Order) cell.getTableRow().getItem();

        OrderDetailsPopupModel popupModel = new OrderDetailsPopupModel();
        popupModel.setOrder(order);

        OrderDetailsPopupView popupView = OrderDetailsPopupView.loadOrderDetailsPopup();

        OrderDetailsPopupController popupController = new OrderDetailsPopupController(popupModel, popupView);
        popupController.start();
    } // end of handleViewOrderButtonClick

    private void handleSetOrderStatusButtonClick(ActionEvent event) {
        Button checkBox = (Button) event.getSource();
        TableCell<Object, Void> cell = (TableCell<Object, Void>) checkBox.getParent();

        int filteredIndex = cell.getTableRow().getIndex();
        int originalIndex = view.getFilteredList().getSourceIndex(filteredIndex);

        model.updateStatus(!model.getOrderList().get(originalIndex).isStatus(), originalIndex);
        view.getOrderList().set(originalIndex, model.getOrderList().get(originalIndex));

        model.setStatusChange(true);
        model.notifyObservers();
        model.setStatusChange(false);
    } // end of handleSetOrderStatusButtonClick
} // end of OrderListPageController

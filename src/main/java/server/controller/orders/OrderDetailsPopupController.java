package server.controller.orders;

import server.model.orders.OrderDetailsPopupModel;
import server.view.orders.OrderDetailsPopupView;
import shared.Product;

public class OrderDetailsPopupController {
    private final OrderDetailsPopupModel model;
    private final OrderDetailsPopupView view;

    public OrderDetailsPopupController(OrderDetailsPopupModel model, OrderDetailsPopupView view) {
        this.model = model;
        this.view = view;
    }

    public void start() {
        loadOrderDetails();
        readOrders();
        setComponentActions();
    } // end of start

    public void loadOrderDetails() {
        view.setDateAndTimeLabel(model.getOrder().getTimeStamp());
        view.setCustomerNameLabel(model.getOrder().getCustomer().getName());
        view.setAddressLabel(model.getOrder().getCustomer().getAddress());
        view.setTotalPriceLabel("₱" + model.getOrder().getTotalPrice());
        view.setStatusLabel(model.getOrder().isStatus() ? "Confirmed" : "Pending");
    } // end of loadOrderDetails

    public void readOrders() {
        StringBuilder orders = new StringBuilder();
        for (Product product : model.getOrder().getOrders()) {
            orders.append(product.getName()).append(" (").append(product.getQuantity()).append(") ").append("\n");
        }
        view.setOrderSummaryTextArea(orders.toString());
    } // end of readOrders

    public void setComponentActions() {
        view.getCloseButton().setOnAction(ActionEvent -> view.closePopupStage());
    } // end of setComponentActions
} // end of OrderInformationPopupController

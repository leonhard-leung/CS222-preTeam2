package server.controller.analytics;

import server.model.analytics.AnalyticsPageModel;
import server.view.analytics.AnalyticsPageView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AnalyticsPageController {
    private final AnalyticsPageModel model;
    private final AnalyticsPageView view;

    public AnalyticsPageController(AnalyticsPageModel model, AnalyticsPageView view) {
        this.model = model;
        this.view = view;
    }

    public void start() {
        setDate();
        model.computeTotalOrders();
        model.computeSales();

        view.setSalesLabel("₱" + model.getSales());
        view.setTotalOrdersLabel(String.valueOf(model.getTotalOrders()));

        view.populateTableFromMap(model.getFoodList(), model.getBeverageList());
    } // end of start

    public void setDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formattedDate = currentDate.format(formatter);
        view.setDateLabel(formattedDate);
    } // end of setDate
} // end of AnalyticsPageController

package server.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.util.Duration;
import server.controller.accounts.AccountListPageController;
import server.controller.addproducts.AddProductPageController;
import server.controller.analytics.AnalyticsPageController;
import server.controller.inventory.InventoryPageController;
import server.controller.orders.OrderListPageController;
import server.model.MainMenuAdminModel;
import server.model.accounts.AccountListPageModel;
import server.model.addproducts.AddProductPageModel;
import server.model.analytics.AnalyticsPageModel;
import server.model.inventory.InventoryPageModel;
import server.model.listeners.AddProductPageObserver;
import server.model.listeners.InventoryPageObserver;
import server.model.listeners.OrderListPageObserver;
import server.model.orders.OrderListPageModel;
import server.view.MainMenuAdminView;
import server.view.accounts.AccountListPageView;
import server.view.addproducts.AddProductPageView;
import server.view.analytics.AnalyticsPageView;
import server.view.inventory.InventoryPageView;
import server.view.orders.OrderListPageView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MainMenuAdminController implements InventoryPageObserver, OrderListPageObserver, AddProductPageObserver {
    private final MainMenuAdminModel model;
    private final MainMenuAdminView view;

    public MainMenuAdminController(MainMenuAdminModel model, MainMenuAdminView view) {
        this.model = model;
        this.view = view;
    }

    public void start() {
        setupClock();
        setupDate();
        setMainMenuButtons();
    } // end of start

    public void setMainMenuButtons() {

        view.getHomeButton().setOnMouseClicked(actionEvent -> view.returnToHomePage());

        view.getViewOrderButton().setOnMouseClicked(actionEvent -> {
            OrderListPageModel orderListPageModel = new OrderListPageModel();
            orderListPageModel.setOrderList(model.getOrderList());
            orderListPageModel.registerObserver(this);

            OrderListPageView orderListPageView = OrderListPageView.loadOrderListPage(view.getBorderPane());
            OrderListPageController orderListPageController = new OrderListPageController(orderListPageModel, orderListPageView);
            orderListPageController.start();
        });

        view.getViewAccountsButton().setOnMouseClicked(actionEvent -> {
            AccountListPageModel accountListPageModel = new AccountListPageModel();
            accountListPageModel.setCustomerAccountList(model.getCustomerAccountList());

            AccountListPageView accountListPageView = AccountListPageView.loadAccountListPage(view.getBorderPane());
            AccountListPageController accountListPageController = new AccountListPageController(accountListPageModel, accountListPageView);
            accountListPageController.start();
        });

        view.getViewInventoryButton().setOnMouseClicked(actionEvent -> Platform.runLater(() -> {
            InventoryPageModel inventoryPageModel = new InventoryPageModel();
            inventoryPageModel.setFoodList(model.getFoodMenu());
            inventoryPageModel.setBeverageList(model.getBeverageMenu());
            inventoryPageModel.registerObserver(this);

            InventoryPageView inventoryPageView = InventoryPageView.loadInventoryPage(view.getBorderPane());
            InventoryPageController inventoryPageController = new InventoryPageController(inventoryPageModel, inventoryPageView);
            inventoryPageController.start();
        }));

        view.getAddProductsPageButton().setOnMouseClicked(actionEvent -> {
            AddProductPageModel addProductPageModel = new AddProductPageModel();
            addProductPageModel.setFoodMenu(model.getFoodMenu());
            addProductPageModel.setBeverageMenu(model.getBeverageMenu());
            addProductPageModel.registerObserver(this);

            AddProductPageView addProductPageView = AddProductPageView.loadAddProductPage(view.getBorderPane());
            AddProductPageController addProductPageController = new AddProductPageController(addProductPageModel, addProductPageView);
            addProductPageController.start();
        });

        view.getViewAnalyticsButton().setOnMouseClicked(actionEvent -> {
            AnalyticsPageModel analyticsPageModel = new AnalyticsPageModel();
            analyticsPageModel.setOrderList(model.getOrderList());
            analyticsPageModel.setFoodList(model.getFoodMenu());
            analyticsPageModel.setBeverageList(model.getBeverageMenu());

            AnalyticsPageView analyticsPageView = AnalyticsPageView.loadAnalyticsPage(view.getBorderPane());
            AnalyticsPageController analyticsPageController = new AnalyticsPageController(analyticsPageModel, analyticsPageView);
            analyticsPageController.start();
        });
    } // end of setMainMenuButtons

    public void setupClock() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> updateClock()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    } // end of setupClock

    public void updateClock() {
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
        String formattedTime = currentTime.format(formatter);
        view.setTimeLabel(formattedTime);
    } // end of updateClock

    public void setupDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formattedDate = currentDate.format(formatter);
        view.setDateLabel(formattedDate);
    } // end of setupDate

    @Override
    public void notifyInventoryChanges(boolean inventoryChanges) {
        if (inventoryChanges) {
            model.setMenuChanges(true);
            model.notifyObservers("INVENTORY_CHANGE");
            model.setMenuChanges(false);
        }
    } // end of notifyInventoryChanges

    @Override
    public void notifyOrderStatusChange(boolean statusChanges) {
        if (statusChanges) {
            model.setMenuChanges(true);
            model.notifyObservers("STATUS_CHANGE");
            model.setMenuChanges(false);
        }
    } // end of notifyOrderStatusChange

    @Override
    public void notifyNewProduct(boolean newProduct, char type) {
        String code = null;
        if (newProduct) {
            model.setMenuChanges(true);
            if ('f' == type) {
                code = "NEW_FOOD_PRODUCT";
                System.out.println(model.getFoodMenu());
            } else if ('b' == type) {
                code = "NEW_BEVERAGE_PRODUCT";
                System.out.println(model.getBeverageMenu());
            }
            model.notifyObservers(code);
            model.setMenuChanges(false);
        }
    } // end of notifyNewProduct
} // end of MainMenuAdminController class

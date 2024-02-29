package client.controller.fxmlcontroller;


import client.model.fxmlmodel.*;
import client.view.fxmlview.CheckoutPageView;
import client.view.fxmlview.MainMenuClientPageView;
import client.view.fxmlview.MenuCardView;
import client.view.fxmlview.OrderHistoryPageView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import shared.Beverage;
import shared.Food;
import shared.Order;
import shared.Product;
import util.ImageUtility;
import util.LoadingScreenUtility;
import util.PushNotification;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MainMenuClientPageController {
    private Stage primaryStage;
    private char currentLoadedMenu = 'f';
    private final MainMenuClientPageView mainMenuView;
    private final MainMenuClientPageModel mainMenuModel;
    private LoginPageController loginPageController;
    private FXMLLoader loader;
    private Parent root;
    private Object[] serverResponse;
    private Socket socket; // to be passed to client
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private int cartColumn = 0; //for cart scrollPane
    private int cartRow = 1; //for cart scrollPane
    private double cartTotalPrice = 0; //for cart purposes
    private static final long DEBOUNCE_DELAY = 500;
    private Timer debounceTimer;

    public MainMenuClientPageController(MainMenuClientPageModel mainMenuModel, MainMenuClientPageView mainMenuView) {
        this.mainMenuView = mainMenuView;
        this.mainMenuModel = mainMenuModel;

        // setting up the time and date labels
        setupClock();
        setupDate();

        //initialize the account name
        String accountName = this.mainMenuModel.getClientModel().getCustomer().getName();
        this.mainMenuView.getAccountNameLabel().setText(accountName);

        // initial menu
        loadFoodMenu();

        setComponentActions();

        debounceTimer = new Timer();
    }

    private void setComponentActions() {
        // set up action listener for food category button
        mainMenuView.getMainMenuFoodButton().setOnAction(actionEvent -> {
            mainMenuView.getGridPaneMenu().getChildren().clear(); // remove existing menu from the grid before switching menus
            currentLoadedMenu = 'f';
            loadFoodMenu();
        });

        // set up action listener for beverages category button
        mainMenuView.getMainMenuBeveragesButton().setOnAction(actionEvent -> {
            mainMenuView.getGridPaneMenu().getChildren().clear(); // remove existing menu from the grid before switching menus
            currentLoadedMenu = 'b';
            loadBeverageMenu();
        });

        //setting up the action for setUpActionClearCartButtonButton
        setUpActionClearCartButton();

        //set up the action for checking out
        setUpActionCheckoutButton();

        mainMenuView.getProductSearchBar().textProperty().addListener((observable, oldValue, newValue) -> debounceFilterMenuItems(newValue));

        //set up action for logout button
        setUpActionLogoutButton();

        setUpActionOrderHistoryButton();
    } // end of setComponentActions

    /**This method implements the order history button*/
    private void setUpActionOrderHistoryButton() {
        //data to be sent to server

        this.mainMenuView.setUpActionOrderHistoryButton((ActionEvent event) ->{
            if (this.mainMenuModel.getClientModel().getCustomer().getOrderHistory().isEmpty()){
                PushNotification.toastSuccess("Order History Empty", "Your history is empty, try ordering first!");
            }else{
                try {
                    List<Product> distinctProductsOnOrderHistory = getProductsOnOrderHistory();
                    OrderHistoryPageModel orderHistoryPageModel = new OrderHistoryPageModel(distinctProductsOnOrderHistory);
                    OrderHistoryPageView orderHistoryPageView = OrderHistoryPageView.loadCheckoutPage();
                    OrderHistoryPageController orderHistoryPageController = new OrderHistoryPageController(orderHistoryPageModel, orderHistoryPageView);

                    orderHistoryPageView.getSubmitReviewButton().setOnAction(actionEvent ->{
                        orderHistoryPageController.submitRatedProducts();
                        List<Product> ratedProducts = orderHistoryPageController.getRatedProducts();
                        orderHistoryPageController.getView().closeCheckoutView();
                        if (!ratedProducts.isEmpty()){
                            String clientId = String.valueOf(this.mainMenuModel.getClientModel().getCustomer().getName().hashCode());
                            sendData(clientId, "PROCESS_REVIEW", ratedProducts);
                            PushNotification.toastSuccess("Review Sent", "Thank You for your reviews!");
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
                //sendData shit make requests to the server and handle the response properly.
            }
        });
    }

    /**This method gets all the distinct product from the customer's order history*/
    private List<Product> getProductsOnOrderHistory() {
        Set<Product> uniqueProducts = new HashSet<>();

        List<Order> orderHistory = this.mainMenuModel.getClientModel().getCustomer().getOrderHistory();
        for (Order order: orderHistory) {
            List<Product> products= order.getOrders();
            // Add each product to the HashSet
            uniqueProducts.addAll(products);
        }
        return new ArrayList<>(uniqueProducts);
    }

    public void run() {
        try {
            primaryStage.setOnCloseRequest(event -> {
                String clientID = String.valueOf(this.mainMenuModel.getClientModel().getCustomer().getUsername().hashCode());
                sendData(clientID, "LOGOUT", null);
                closeResources();
                System.exit(0);
            });

            listenToHost();
        } catch (IOException | ClassNotFoundException ioException) {
            ioException.printStackTrace();
        }
    } // end of run

    private void listenToHost() throws IOException, ClassNotFoundException {
        try {
            while (true) {
                Object[] data = (Object[]) in.readObject();
                if (data != null) {
                    handleIncomingData(data);
                }
            }
        } catch (SocketException e) {
            System.out.println("Socket closed by the server.");
        }
    } // end of listenToHost

    private void handleIncomingData(Object[] data) {
        String dataCode = (String) data[1];
        System.out.println("received data from server");
        System.out.println(data[0]);
        System.out.println(data[1]);
        System.out.println(data[2]);
        switch (dataCode) {
            case "PRODUCT_CHANGES" -> {
            }
            case "PROCESS_ORDER_SUCCESSFUL" -> {
                mainMenuModel.getClientModel().orderProcessSuccessful((Order) data[2]);
                PushNotification.toastSuccess("Checkout Status", "Your order has been placed");
                Platform.runLater(() -> clearCart(false));
            }
            case "PROCESS_ORDER_FAILED" -> {
                clearCart(false);
                PushNotification.toastError("Checkout Status", "Order unsuccessful due to stock shortage");
            }
            case "DATA_UPDATE" -> {
                System.out.println("Obtained Updated Products");
                Object[] bundledData = (Object[]) data[2];
                System.out.println(bundledData[0]);
                System.out.println(bundledData[1]);
                mainMenuModel.getClientModel().setFoodMenu((HashMap<String, Food>) bundledData[0]);
                mainMenuModel.getClientModel().setBeverageMenu((HashMap<String, Beverage>) bundledData[1]);
            }
        }
    } // end of handleIncomingData

    private void sendData(String clientID, String code, Object data) {
        Object[] response = {clientID, code, data};
        try {
            out.writeObject(response);
            out.flush();
            out.reset();
        } catch (IOException e) {
            showServerErrorUI();
            throw new RuntimeException(e);
        }
    } // end of sendData

    public void setupClock() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> updateClock()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    } // end of setDateAndTime

    public void updateClock() {
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
        String formattedTime = currentTime.format(formatter);
        mainMenuView.setTimeLabel(formattedTime);
    } // end of updateClock

    private void setupDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formattedDate = currentDate.format(formatter);
        mainMenuView.setDateLabel(formattedDate);
    } // end of setupDate

    private void loadFoodMenu() {
        mainMenuView.getProductTypeLabel().setText("Food Category");

        LoadingScreenUtility.showLoadingIndicator(mainMenuView.getLoadingIndicatorPanel());

        HashMap<String, Food> foodMenu = mainMenuModel.getClientModel().getFoodMenu();
        List<Food> foodProducts = new ArrayList<>(foodMenu.values());

        Task<Void> loadingTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(2000);

                Platform.runLater(() -> {
                    int columnIndex = 0;
                    int rowIndex = 1;

                    for (Food food : foodProducts) {
                        Node productCard = createProductCard(food);

                        mainMenuView.getGridPaneMenu().getChildren().add(productCard);
                        GridPane.setConstraints(productCard, columnIndex, rowIndex);

                        if (columnIndex == 1) {
                            columnIndex = 0;
                            rowIndex++;
                        } else {
                            columnIndex++;
                        }
                    }

                    LoadingScreenUtility.hideLoadingIndicator(mainMenuView.getLoadingIndicatorPanel());
                });
                return null;
            }
        };

        Thread loadingThread = new Thread(loadingTask);
        loadingThread.start();
    } // end of loadFoodMenu

    private void loadBeverageMenu() {
        mainMenuView.getProductTypeLabel().setText("Beverage Category");

        LoadingScreenUtility.showLoadingIndicator(mainMenuView.getLoadingIndicatorPanel());

        HashMap<String, Beverage> beverageMenu = mainMenuModel.getClientModel().getBeverageMenu();
        List<Beverage> beverageProducts = new ArrayList<>(beverageMenu.values());

        Task<Void> loadingTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(2000);

                Platform.runLater(() -> {
                    int columnIndex = 0;
                    int rowIndex = 1;

                    for (Beverage beverage : beverageProducts) {
                        Node productCard = createProductCard(beverage);

                        mainMenuView.getGridPaneMenu().getChildren().add(productCard);
                        GridPane.setConstraints(productCard, columnIndex, rowIndex);

                        if (columnIndex == 1) {
                            columnIndex = 0;
                            rowIndex++;
                        } else {
                            columnIndex++;
                        }
                    }

                    LoadingScreenUtility.hideLoadingIndicator(mainMenuView.getLoadingIndicatorPanel());
                });
                return null;
            }
        };

        Thread loadingThread = new Thread(loadingTask);
        loadingThread.start();
    } // end of loadBeverageMenu

    private Node createProductCard(Product product) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/client/menu_card.fxml"));
            Node productCard = loader.load();

            MenuCardModel menuCardModel = new MenuCardModel();
            MenuCardView menuCardView = loader.getController();
            MenuCardController menuCardController = new MenuCardController(menuCardModel, menuCardView);
            menuCardController.setProductData(product);

            //this code setups up add to cart button of each card
            menuCardView.getAddProductButton().setOnAction(actionEvent -> {
                MenuCardModel updatedMenuCardModel = new MenuCardModel();
                MenuCardView updatedMenuModelCardView = loader.getController();
                MenuCardController updatedMenuCardController = new MenuCardController(menuCardModel, menuCardView);

                Product product1 = menuCardModel.getProduct();
                if (product1 instanceof Food food) {
                    for (Map.Entry<String, Food> entry : mainMenuModel.getClientModel().getFoodMenu().entrySet()) {
                        if (entry.getValue().getName().equals(food.getName())) {
                            updatedMenuCardController.setProductData(entry.getValue());
                            break;
                        }
                    }
                } else if (product1 instanceof Beverage beverage) {
                    for (Map.Entry<String, Beverage> entry : mainMenuModel.getClientModel().getBeverageMenu().entrySet()) {
                        if (entry.getValue().getName().equals(beverage.getName())) {
                            updatedMenuCardController.setProductData(entry.getValue());
                            break;
                        }
                    }
                }

                addToCart(menuCardModel.getProduct());
            });

            return productCard;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    } // end of createProductCard

    private void debounceFilterMenuItems(String searchText) {
        debounceTimer.cancel();
        debounceTimer = new Timer();

        LoadingScreenUtility.showLoadingIndicator(mainMenuView.getLoadingIndicatorPanel());
        debounceTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                Platform.runLater(() -> {
                    filterMenuItems(searchText);
                    LoadingScreenUtility.hideLoadingIndicator(mainMenuView.getLoadingIndicatorPanel());
                });
            }
        }, DEBOUNCE_DELAY);

    } // end of debounceFilterMenuItems

    private void filterMenuItems(String searchText) {
        mainMenuView.getGridPaneMenu().getChildren().clear();

        List<Node> filteredProductCards = new ArrayList<>();
        int columnIndex = 0;
        int rowIndex = 1;
        if (currentLoadedMenu == 'f') {
            HashMap<String, Food> foodMenu = mainMenuModel.getClientModel().getFoodMenu();
            List<Food> filteredFoodProducts = foodMenu.values().stream()
                    .filter(food -> food.getName().toLowerCase().contains(searchText.toLowerCase()))
                    .toList();

            for (Food food : filteredFoodProducts) {
                Node productCard = createProductCard(food);
                filteredProductCards.add(productCard);
            }
        } else {
            HashMap<String, Beverage> beverageMenu = mainMenuModel.getClientModel().getBeverageMenu();
            List<Beverage> filteredBeverageProducts = beverageMenu.values().stream()
                    .filter(beverage -> beverage.getName().toLowerCase().contains(searchText.toLowerCase()))
                    .toList();

            for (Beverage beverage : filteredBeverageProducts) {
                Node productCard = createProductCard(beverage);
                filteredProductCards.add(productCard);
            }
        }

        for (Node productCard : filteredProductCards) {
            mainMenuView.getGridPaneMenu().getChildren().add(productCard);
            GridPane.setConstraints(productCard, columnIndex, rowIndex);
            if (columnIndex == 1) {
                columnIndex = 0;
                rowIndex++;
            } else {
                columnIndex++;
            }
        }
    }

    /**
     * This method clears up all the contents of the cart.
     * It implements the setUpActionClearCartButtonButton from the mainMenuClientPageView
     */
    private void setUpActionClearCartButton() {
        this.mainMenuView.setActionClearCartButton((ActionEvent event) -> clearCart(true));
    }

    /*This method sets up the logout button*/
    private void setUpActionLogoutButton() {
        this.mainMenuView.getLogoutButton().setOnAction(event -> {
            String clientID = String.valueOf(this.mainMenuModel.getClientModel().getCustomer().getUsername().hashCode());
            sendData(clientID, "LOGOUT", null);
            showLoginPage(event);
        });
    }

    private void clearCart(boolean isUpdateModel) {
        ObservableList<Node> cartItems = this.mainMenuView.getGridPaneCart().getChildren();

        if (cartItems.isEmpty()) {
            return;
        }

        //for each card
        for (Node cartItem : cartItems) {
            String productName = null;
            int productQuantity = 0;
            char productType = ' ';
            String productSize = null;
            double productPrice = 0;

            AnchorPane cartItemPane = (AnchorPane) cartItem;
            for (Node label : cartItemPane.getChildren()) {
                if (label instanceof Label) {
                    switch (label.getId()) {
                        case "productNameLabel":
                            productName = ((Label) label).getText();
                            break;
                        case "quantityLabel":
                            String quantityLabel = ((Label) label).getText();
                            String cleanedQuantity = quantityLabel.replaceAll("[qty:\\s]", "");
                            productQuantity = Integer.parseInt(cleanedQuantity);
                            break;
                        case "sizeLabel":
                            String sizeLabel = ((Label) label).getText();
                            productSize = sizeLabel.replaceAll("[size:\\s]", "");

                            switch (productSize) {
                                case "S" -> {
                                    productSize = "small";
                                    productType = 'b';
                                }
                                case "M" -> {
                                    productSize = "medium";
                                    productType = 'b';
                                }
                                case "L" -> {
                                    productSize = "large";
                                    productType = 'b';
                                }
                                default -> {
                                    productSize = "";
                                    productType = 'f';
                                }
                            }
                            break;
                        case "priceLabel":
                            String priceLabel = ((Label) label).getText();
                            String cleanedPrice = priceLabel.replaceAll("[₱\\s]", ""); //cleaning
                            productPrice = Double.parseDouble(cleanedPrice);
                    }
                }
            }

            try {
                if (isUpdateModel) {
                    //update the models
                    if (productType == 'f') {
                        this.mainMenuModel.getClientModel().getFoodMenu().get(productName).updateQuantity(-productQuantity); //negative because we want to add/revert back the subtracted from the menu
                    } else if (productType == 'b') {
                        this.mainMenuModel.getClientModel().getBeverageMenu().get(productName).updateQuantity(productSize, -productQuantity);
                    }

                    //clear the cart of the customer
                    this.mainMenuModel.getClientModel().getCart().clear();
                }

                //update the price label
                cartTotalPrice -= productPrice;

                //update the UI
                //set visible the labels
                mainMenuView.getCartLabel1().setVisible(true);
                mainMenuView.getCartLabel2().setVisible(true);
                mainMenuView.getCartImage().setVisible(true);

                //add to grid pane
                mainMenuView.getGridPaneCart().setVisible(false);
                mainMenuView.getPriceLabel().setText("₱ " + cartTotalPrice + "0");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //after clearing, remove all the contents of the pane
        this.mainMenuView.getGridPaneCart().getChildren().clear();
    }

    /**
     * this method sets up the action for checking out button
     */
    private void setUpActionCheckoutButton() {
        this.mainMenuView.setUpActionCheckoutButton((ActionEvent event) -> {
            if (this.mainMenuModel.getClientModel().getCart().isEmpty()) {
                PushNotification.toastSuccess("Cart Empty", "No items to be checked out. Add items to cart.");
            } else {
                try {
                    CheckoutPageModel checkoutPageModel = new CheckoutPageModel(mainMenuModel.getClientModel().getCustomer(), mainMenuModel.getClientModel().getCart(), cartTotalPrice, mainMenuModel.getClientModel().placeOrder());
                    CheckoutPageView checkoutPageView = CheckoutPageView.loadCheckoutPage();
                    CheckoutPageController checkoutPageController = new CheckoutPageController(checkoutPageModel, checkoutPageView);

                    checkoutPageView.getPlaceOrderButton().setOnAction(actionEvent -> {
                        if (!checkoutPageView.getOnlinePayment().isSelected() && !checkoutPageView.getCashOnDelivery().isSelected()) {
                            checkoutPageView.setNoticeLabel("choose payment option");
                            checkoutPageView.getNoticeLabel().setVisible(true);
                        } else if (checkoutPageView.getOnlinePayment().isSelected() || checkoutPageView.getCashOnDelivery().isSelected()) {
                            String clientId = String.valueOf(checkoutPageModel.getCustomer().getName().hashCode());
                            Order order = checkoutPageModel.getOrderFromClient();
                            sendData(clientId, "PROCESS_ORDER",order);

                            checkoutPageView.closeCheckoutView();

                            PushNotification.toastSuccess("Order", "Order uploaded to the system");
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Adds a product into a cart. It also loads the selection panels
     */
    private void addToCart(Product product) {
        SelectFoodController selectFoodController = null;
        SelectBeverageVariationController selectBeverageVariationController = null;

        //load the UIs for selection
        try {
            //set up first the select variation
            if (product.getType() == 'f') {
                FXMLLoader selectFoodLoader = new FXMLLoader(getClass().getResource("/fxml/client/select_food.fxml"));
                Parent root = selectFoodLoader.load();

                selectFoodController = new SelectFoodController(new SelectFoodModel(product), selectFoodLoader.getController());
                Scene scene = new Scene(root);
                Stage popupStage = new Stage();
                popupStage.getIcons().add(new Image(getClass().getResource("/images/client/client_app_logo.png").toExternalForm()));
                popupStage.setScene(scene);
                popupStage.initModality(Modality.APPLICATION_MODAL);
                popupStage.showAndWait(); //wait until the popup stops
            } else {
                FXMLLoader selectBeverageVariationLoader = new FXMLLoader(getClass().getResource("/fxml/client/select_beverage_variation.fxml"));
                Parent root = selectBeverageVariationLoader.load();

                selectBeverageVariationController = new SelectBeverageVariationController(new SelectBeverageVariationModel(product), selectBeverageVariationLoader.getController());
                Scene scene = new Scene(root);
                Stage popupStage = new Stage();
                popupStage.getIcons().add(new Image(getClass().getResource("/images/client/client_app_logo.png").toExternalForm()));
                popupStage.setScene(scene);
                popupStage.initModality(Modality.APPLICATION_MODAL);
                popupStage.showAndWait(); //wait until the popup stops
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //check whether what order type was produced
        if (product instanceof Food) { //means the product is food being added to cart is food
            if (selectFoodController.getFinalOrderedQuantity() != 0) {
                //update first the models
                updateModelsData(product, selectFoodController);

                //add to cart grid pane
                addToCartGridPane(product, selectFoodController.getFinalOrderedQuantity(), selectFoodController.getFinalOrderedPrice(), null);

                //update the cart totals and the view
                updateCartTotalPrice(selectFoodController.getFinalOrderedPrice());
            }
        } else { //means the product being added is food
            //process the small
            if (selectBeverageVariationController.getFinalSmallOrderedQuantity() != 0) {
                //update the models first
                updateModelsData(product, selectBeverageVariationController.getFinalSmallOrderedQuantity(), "small", selectBeverageVariationController.getFinalSmallOrderedPrice());

                //update the cart grid pane
                addToCartGridPane(product, selectBeverageVariationController.getFinalSmallOrderedQuantity(), selectBeverageVariationController.getFinalSmallOrderedPrice(), "S");

                //update the cart totals and the view
                updateCartTotalPrice(selectBeverageVariationController.getFinalSmallOrderedPrice());

            }

            //process the medium
            if (selectBeverageVariationController.getFinalMediumOrderedQuantity() != 0) {
                //update the models first
                updateModelsData(product, selectBeverageVariationController.getFinalMediumOrderedQuantity(), "medium", selectBeverageVariationController.getFinalMediumOrderedPrice());

                //update the cart grid pane
                addToCartGridPane(product, selectBeverageVariationController.getFinalMediumOrderedQuantity(), selectBeverageVariationController.getFinalMediumOrderedPrice(), "M");

                //update the cart totals and the view
                updateCartTotalPrice(selectBeverageVariationController.getFinalMediumOrderedPrice());
            }

            //process the medium
            if (selectBeverageVariationController.getFinalLargeOrderedQuantity() != 0) {
                //update the models first
                updateModelsData(product, selectBeverageVariationController.getFinalLargeOrderedQuantity(), "large", selectBeverageVariationController.getFinalLargeOrderedPrice());

                //update the cart grid pane
                addToCartGridPane(product, selectBeverageVariationController.getFinalLargeOrderedQuantity(), selectBeverageVariationController.getFinalLargeOrderedPrice(), "L");

                //update the cart totals and the view
                updateCartTotalPrice(selectBeverageVariationController.getFinalLargeOrderedPrice());
            }
        }
    }

    /**
     * This method updates the total price being shown in the cart display.
     */
    private void updateCartTotalPrice(double productPrice) {
        cartTotalPrice += productPrice;
        this.mainMenuView.getPriceLabel().setText("₱ " + cartTotalPrice + "0");
    }

    /**
     * This method updates the models for beverage
     */
    private void updateModelsData(Product product, int count, String size, double totalPrice) {
        try {
            //update the quantity of the main menu food
            mainMenuModel.getClientModel().getBeverageMenu().get(product.getName()).updateQuantity(size, count);

            int sQuantity = 0;
            int mQuantity = 0;
            int lQuantity = 0;
            double sPrice = 0;
            double mPrice = 0;
            double lPrice = 0;
            switch (size) {
                case "small" -> {
                    sQuantity = count;
                    sPrice = totalPrice;
                }
                case "medium" -> {
                    mQuantity = count;
                    mPrice = totalPrice;
                }
                case "large" -> {
                    lQuantity = count;
                    lPrice = totalPrice;
                }
            }

            Object[] imageData = {product.getImageName(), ImageUtility.getImageBytes(product.getImageName())};
            Beverage beverage = new Beverage(product.getName(), product.getType(), product.getReview(), product.getReviewCount(), imageData, product.getDescription(), sQuantity, mQuantity, lQuantity, sPrice, mPrice, lPrice);
            //update first the cart of the client model which resides in MainMenuModel.getClientModel()
            mainMenuModel.getClientModel().getCart().add(beverage);

            //set visible the labels
            mainMenuView.getCartLabel1().setVisible(false);
            mainMenuView.getCartLabel2().setVisible(false);
            mainMenuView.getCartImage().setVisible(false);

            //add to grid pane
            mainMenuView.getGridPaneCart().setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method updates the models for food
     */
    private void updateModelsData(Product product, SelectFoodController selectFoodController) {
        try {
            //update the quantity of the main menu food
            mainMenuModel.getClientModel().getFoodMenu().get(product.getName()).updateQuantity(selectFoodController.getFinalOrderedQuantity());

            //cast to create a new Food object to be passed on the cart
            Object[] imageData = {product.getImageName(), ImageUtility.getImageBytes(product.getImageName())};
            Food food = new Food(product.getName(), product.getType(), product.getReview(), product.getReviewCount(), imageData, product.getDescription(), selectFoodController.getFinalOrderedQuantity(), selectFoodController.getFinalOrderedPrice());
            //update first the cart of the client model which resides in MainMenuModel.getClientModel()
            mainMenuModel.getClientModel().getCart().add(food);


            //set visible the labels
            mainMenuView.getCartLabel1().setVisible(false);
            mainMenuView.getCartLabel2().setVisible(false);
            mainMenuView.getCartImage().setVisible(false);

            //add to grid pane
            mainMenuView.getGridPaneCart().setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * A helper method that creates a new cards in the cart grid view
     */
    private void addToCartGridPane(Product product, int finalOrderedQuantity, double finalOrderedPrice, String size) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/client/cart_item_card.fxml"));

            //putting the card on the anchorPane
            AnchorPane card = loader.load();

            CartItemCardController cardCart = new CartItemCardController(new CartItemCardModel(product, finalOrderedQuantity, finalOrderedPrice, size), loader.getController());
            cardCart.setData();

            if (cartColumn == 1) {
                cartColumn = 0;
                cartRow++;
            }
            mainMenuView.getGridPaneCart().add(card, cartColumn++, cartRow);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FXMLLoader getLoader() {
        return loader;
    }

    public void setLoader(FXMLLoader loader) {
        this.loader = loader;
    }

    public Parent getRoot() {
        return root;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }

    public Object[] getServerResponse() {
        return serverResponse;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public void setIn(ObjectInputStream in) {
        this.in = in;
    }

    private void showLoginPage(ActionEvent event) {
        try {
            loader = new FXMLLoader(getClass().getResource("/fxml/client/login_page.fxml"));
            root = loader.load(); //load

            loginPageController = new LoginPageController(loader.getController(), new LoginPageModel()); //get controller

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showServerErrorUI() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/client/server_error.fxml"));
            Scene serverErrorScene = new Scene(root);
            Stage popUpStage = new Stage();
            popUpStage.setScene(serverErrorScene);
            popUpStage.show();
        }catch (IOException exception){
            exception.printStackTrace();
        }
    }

    private void closeResources() {
        try {
            socket.close();
            in.close();
            out.close();
            if (in == null && out == null) {
                System.out.println("closed stream resources");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    } // end of closeResources

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
} // end of MainMenuClientPageController class
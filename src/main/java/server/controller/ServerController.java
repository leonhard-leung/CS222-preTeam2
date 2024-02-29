package server.controller;

import javafx.application.Platform;
import server.model.MainMenuAdminModel;
import server.model.ServerModel;
import server.model.listeners.MainMenuAdminObserver;
import server.view.MainMenuAdminView;
import server.view.ServerView;
import shared.Customer;
import shared.Order;
import shared.Product;
import util.PushNotification;
import util.exception.AccountAlreadyLoggedIn;
import util.exception.AccountExistsException;
import util.exception.InvalidCredentialsException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ServerController implements MainMenuAdminObserver {
    private final ServerModel model;
    private final ServerView view;
    private Socket clientSocket;
    private ObjectInputStream streamReader;
    private ObjectOutputStream streamWriter;

    private MainMenuAdminModel mainMenuAdminModel;

    public ServerController(ServerModel model, ServerView view) {
        this.model = model;
        this. view = view;
    } // end of constructor

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    } // end of setClientSocket

    // TODO
    private void setComponentActions() {
        Platform.runLater(() -> {
            mainMenuAdminModel = new MainMenuAdminModel();
            mainMenuAdminModel.setCustomerAccountList(model.getCustomerAccountList());
            mainMenuAdminModel.setOrderList(model.getOrderList());
            mainMenuAdminModel.setFoodMenu(model.getFoodMenu());
            mainMenuAdminModel.setBeverageMenu(model.getBeverageMenu());
            mainMenuAdminModel.registerObserver(this);
            MainMenuAdminView mainMenuAdminView = view.getLoader().getController();

            MainMenuAdminController mainMenuAdminController = new MainMenuAdminController(mainMenuAdminModel, mainMenuAdminView);
            mainMenuAdminController.start();
        });
    } // end of setComponentActions

    public void run() {
        try {
            streamReader = new ObjectInputStream(clientSocket.getInputStream());
            streamWriter = new ObjectOutputStream(clientSocket.getOutputStream());

            model.registerServerController(this);

            listenToClient();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    } // end of run

    public void initializeAdminInterface() {
        Platform.runLater(() -> {
            System.out.println("Obtained Main Menu Controller");
            setComponentActions();
            System.out.println("Successfully added actions");
        });
    } // end of initializeAdminInterface

    private void listenToClient() throws IOException, ClassNotFoundException {
        while (!clientSocket.isClosed()) {
            Object[] data = (Object[]) streamReader.readObject();
            if (data != null) {
                handleClientRequest(data);
            }
        }
    } // end of listenToClient

    // TODO: guide from a client request Object[]{string clientID, string requestType, Object[] data}
    private void handleClientRequest(Object[] message) {
        String requestCode = (String) message[1];
        System.out.println("\nServer received request from client id: " + message[0]);
        System.out.println("Request Code: " + message[1]);
        switch (requestCode) {
            case "LOGIN" -> {
                try {
                    String[] information = (String[]) message[2];
                    String username = information[0];
                    String password = information[1];
                    Object[] client = model.processLogin(username, password);

                    sendData(String.valueOf(((Customer) client[0]).getUsername().hashCode()), "LOGIN_SUCCESSFUL", client);
                } catch (InvalidCredentialsException exception) {
                    sendData("", "LOGIN_FAILED", null);
                }catch (AccountAlreadyLoggedIn exception) {
                    sendData("", "ALREADY_LOGGED_IN", false);
                }catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            case "SIGN_UP" -> {
                try {
                    Customer client = (Customer) message[2];
                    model.processSignUp(client);
                    sendData(String.valueOf(client.getUsername().hashCode()), "SIGN_UP_SUCCESSFUL", true);
                } catch (AccountExistsException accountExistsException) {
                    sendData("", "SIGN_UP_FAILED", false);
                } catch (Exception e){
                    throw new RuntimeException();
                }
            }
            case "PROCESS_ORDER" -> {
                try {
                    Order order = model.processOrder((Order) message[2]);
                    sendData(String.valueOf(message[0]), "PROCESS_ORDER_SUCCESSFUL", order);
                    model.notifyObservers();
                } catch (Exception exception) {
                    sendData(String.valueOf(message[0]), "PROCESS_ORDER_FAILED", null);
                    exception.printStackTrace();
                    System.err.println("Error during the order processing");
                }
            }
            case "PROCESS_REVIEW" -> {
                try {
                    List<Product> ratedProducts = (List<Product>) message[2];
                    System.out.println("PROCESSING REVIEW");
                    model.processReview(ratedProducts);
                    model.notifyObservers();
                } catch (Exception exception) {
                    System.err.println("Error during the review processing");
                }
            }
            case "LOGOUT" ->{
                String clientID = (String) message[0];
                this.model.processLogout(clientID);
            }
        }
    } // end of handleClientRequest

    @Override
    public void notifyMenuChanges(String code, boolean menuChanges) {
        if (menuChanges) {
            if ("STATUS_CHANGE".equals(code)) {
                model.setOrderList(mainMenuAdminModel.getOrderList());
            } else if ("INVENTORY_CHANGE".equals(code)) {
                model.setFoodMenu(mainMenuAdminModel.getFoodMenu());
                model.setBeverageMenu(mainMenuAdminModel.getBeverageMenu());
                PushNotification.toastSuccess("Inventory Status", "Updated inventory stocks and details");
            } else if ("NEW_FOOD_PRODUCT".equals(code)) {
                model.setFoodMenu(mainMenuAdminModel.getFoodMenu());
                PushNotification.toastSuccess("New Product", "Food added to the list");
            } else if ("NEW_BEVERAGE_PRODUCT".equals(code)) {
                model.setBeverageMenu(mainMenuAdminModel.getBeverageMenu());
                PushNotification.toastSuccess("New Product", "Beverage added to the list");
            }
            model.notifyObservers();
        }
    } // end of notifyMenuChanges

    public void sendData(String clientID, String code, Object data) {
        Object[] response = {clientID, code, data};
        try {
            streamWriter.writeObject(response);
            streamWriter.flush();
            streamWriter.reset();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    } // end of sendData
} // end of ServerController

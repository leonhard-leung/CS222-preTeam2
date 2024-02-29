package server;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import server.controller.ServerController;
import server.model.ServerModel;
import server.model.listeners.ClientObserver;
import server.view.ServerView;
import util.XMLUtility;

import java.io.IOException;
import java.net.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server extends Application implements ClientObserver {
    private static final int PORT = 2000;
    private static final int THREAD_POOL_SIZE = 20;
    private static final int BROADCAST_PORT = 12345;
    private ServerModel model;
    private ServerView view;

    public static void main(String[] args) {
        launch(args);
    } // end of main

    @Override
    public void start(Stage stage) {
        stage.getIcons().add(new Image(getClass().getResource("/images/server/server_app_logo.png").toExternalForm()));

        model = new ServerModel();
        model.addObserver(this);
        view = new ServerView(stage);
        view.runInterface();

        ServerController adminController = new ServerController(model, view);
        adminController.initializeAdminInterface();

        //implement an action when the window is closed
        view.getStage().setOnCloseRequest(windowEvent -> {
            XMLUtility.saveFoodMenu(model.getFoodMenu());
            XMLUtility.saveBeverageMenu(model.getBeverageMenu());
            XMLUtility.saveOrders(model.getOrderList());
            XMLUtility.saveCustomerAccounts(model.getCustomerAccountList());
        });

        // launch the server
        startServer();

        // broadcast the server to all machines connected in the local network
        //startBroadcastingServer();
    } // end of start

    private void startServer() {
        new Thread(() -> {
            try (ServerSocket server = new ServerSocket(PORT);
                 ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE)) {
                System.out.println("Server listening on port " + PORT);

                ServerController controller;

                while (true) {
                    Socket client = server.accept();
                    System.out.println("Client connected: " + client.getInetAddress().getHostAddress());
                    controller = new ServerController(model, view);
                    controller.setClientSocket(client);
                    executorService.submit(controller::run);
                }
            } catch (IOException e) {
                System.err.println("Error during server launch: " + e.getMessage());
            }
        }).start();
    } // end of startServer

    /**
     * Starts broadcasting the server's presence to all machines connected in the local network.
     */
    private void startBroadcastingServer() {
        new Thread(() -> {
            try (DatagramSocket broadcastSocket = new DatagramSocket(BROADCAST_PORT)) {
                System.out.println("Server broadcasting its presence on port " + BROADCAST_PORT);

                while (true) {
                    byte[] receiveData = new byte[1024];
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    broadcastSocket.receive(receivePacket);
                    System.out.println("Connection request received from: " + receivePacket.getAddress());

                    String serverIP = InetAddress.getLocalHost().getHostAddress();
                    byte[] sendData = serverIP.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
                    broadcastSocket.send(sendPacket);
                }
            } catch (IOException e) {
                System.err.println("Error during broadcast startup: " + e.getMessage());
            }
        }).start();
    } // end of startBroadcastingServer

    @Override
    public synchronized void onDataChanged() {
        broadcastDataToClients();
    }

    private void broadcastDataToClients() {
        System.out.println("Broadcasting update to all clients");
        List<ServerController> controllers = model.getActiveServerControllers();
        System.out.println(model.getActiveServerControllers().size());
        for (ServerController controller : controllers) {
            System.out.println(model.getFoodMenu());
            System.out.println(model.getBeverageMenu());

            String foodString = model.getFoodMenu().toString();
            String beverageString = model.getBeverageMenu().toString();

            Object[] data = new Object[]{model.getFoodMenu(), model.getBeverageMenu(), foodString, beverageString};

            controller.sendData("", "DATA_UPDATE", data);
        }
    }
} // end of Server class

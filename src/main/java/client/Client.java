package client;

import client.controller.ClientControllerNew;
import client.model.ClientModel;
import client.view.ClientView;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client extends Application {
    public static String IP_ADDRESS;
    public static final int PORT = 2000;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        requestServerAddress();

        stage.getIcons().add(new Image(getClass().getResource("/images/client/client_app_logo.png").toExternalForm()));
        ClientModel model = new ClientModel();
        ClientView view = new ClientView(stage);
        view.runInterface();

        new ClientControllerNew(model, view);
    } //

    /**
     * Requests the IP address of the server by broadcasting a request message to the network.
     */
    public void requestServerAddress() {
        Thread thread = new Thread(() -> {
            try (DatagramSocket socket = new DatagramSocket()) {
                System.out.println("Requesting server address");
                socket.setBroadcast(true);
                byte[] sendData = "DISCOVER_SERVER_REQUEST".getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("255.255.255.255"), 12345);
                socket.send(sendPacket);

                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);
                IP_ADDRESS = receivePacket.getAddress().getHostAddress();
                System.out.println("Server found at IP: " + IP_ADDRESS);
            } catch (IOException e) {
                System.err.println("Error during server discovery: " + e.getMessage());
            }
        });
        thread.setDaemon(true);
        thread.start();
    } // end of requestServerAddress
} // end of Client class

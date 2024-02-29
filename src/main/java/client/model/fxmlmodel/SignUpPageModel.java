package client.model.fxmlmodel;

import client.Client;
import shared.Customer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/*This model represents the model for signup. The model will hold all the processes for the application*/
public class SignUpPageModel {
    private String username;
    private String address;
    private String email;
    private String password;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Object[] serverResponse;

    /**This method tries to make connection to the server
     * @throws ClassNotFoundException
     *
     * Exceptions will be handled by the LoginPage Controller*/
    public void authenticate(String fullName, String username, String address, String email, String password) throws RuntimeException, IOException, ClassNotFoundException {
        //Guide Object[] {"clientID", "SIGN_UP", customer}
        Customer signingUpCustomer= new Customer(fullName, username, address, email, password);
        String clientID = String.valueOf(username.hashCode());
        String requestType = "SIGN_UP";

        socket = new Socket(Client.IP_ADDRESS, Client.PORT);
        out = new ObjectOutputStream(socket.getOutputStream());
        in  = new ObjectInputStream(socket.getInputStream());

        sendData(clientID, requestType, signingUpCustomer);
        Object[] response = (Object[]) in.readObject();

        in.close();
        out.close();
        socket.close();

        serverResponse = response;
    }

    /**Helper method that sends data to server*/
    private void sendData(String clientID, String requestType, Object data) throws IOException{
        Object[] request = new Object[]{clientID, requestType, data};
        out.writeObject(request);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Object[] getServerResponse() {
        return serverResponse;
    }
}
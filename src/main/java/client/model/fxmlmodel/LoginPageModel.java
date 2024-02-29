package client.model.fxmlmodel;


import client.Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/*This model represents the model for login. The model will hold all the processes for the application*/
public class LoginPageModel {
    private String username;
    private String password;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private Object[] serverResponse;

    /**This method tries to make connection to the server
     * @throws ClassNotFoundException
     *
     * Exceptions will be handled by the LoginPage Controller*/
    public void authenticate(String username, String password) throws RuntimeException, IOException, ClassNotFoundException {
            String clientID = String.valueOf(username.hashCode());
            String requestType = "LOGIN";
            String[] credentials = {username, password};

            socket = new Socket(Client.IP_ADDRESS, Client.PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in  = new ObjectInputStream(socket.getInputStream());

            sendData(clientID, requestType, credentials);
            Object[] response = (Object[]) in.readObject();

            //Close the connection when it is not login successful
            if (!response[1].equals("LOGIN_SUCCESSFUL")){ //todo: if login is successful, set the socket for the main menu client page
                socket.close();
                in.close();
                out.close();
            }
            serverResponse = response;
    }

    /**Helper method that sends data to server*/
    private void sendData(String clientID, String requestType, Object data) throws IOException{
        Object[] request = new Object[]{clientID, requestType, data};
        out.writeObject(request);
    }

    public Socket getSocket() {
        return socket;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
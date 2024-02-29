package client.model.fxmlmodel;

import client.model.ClientModel;
import shared.Beverage;
import shared.Customer;
import shared.Food;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class MainMenuClientPageModel {
    //data to be accessed after login by the user
    private final ClientModel clientModel;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public MainMenuClientPageModel(Object[] clientModelData) {
        //the serverResponse[2] responds Object[] {customer, foodMenu, beverageMenu}

        Customer customer = ((Customer) clientModelData[0]);
        HashMap<String, Food> foodMenu = (HashMap<String, Food>) clientModelData[1];
        HashMap<String, Beverage> beverageMenu = ((HashMap<String, Beverage>) clientModelData[2]);

        clientModel = new ClientModel(customer, foodMenu, beverageMenu);
    }


    public ClientModel getClientModel() {
        return clientModel;
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
}
package client.controller;

import client.controller.fxmlcontroller.LandingPageController;
import client.model.ClientModel;
import client.view.ClientView;
import javafx.application.Platform;

public class ClientControllerNew {
    //Client Model and Views
    private final ClientModel model;
    private final ClientView view;
    private LandingPageController landingPageController;
    public ClientControllerNew(ClientModel model, ClientView view){
        this.model = model;
        this.view = view;

        Platform.runLater(() -> {
            System.out.println("controller for landing page acquired");
            //initially, the ClientView view is loads the landing page fxml controller, so we need to acquire it's FXML loader
            landingPageController = new LandingPageController(view.getLoader().getController());
        });
    }
}

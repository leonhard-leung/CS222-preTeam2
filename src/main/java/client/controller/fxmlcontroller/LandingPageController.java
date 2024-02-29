package client.controller.fxmlcontroller;

import client.model.fxmlmodel.LoginPageModel;
import client.model.fxmlmodel.SignUpPageModel;
import client.view.fxmlview.LandingPageView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LandingPageController {
    private FXMLLoader loader;
    private Parent root;
    private LoginPageController loginPageController;
    private SignUpPageController signUpPageController;

    /**Constructor*/
    public LandingPageController(LandingPageView view){

        //setting up action for login button. this action loads up the login view
        view.setActionLoginButton((ActionEvent event)->{
            //load first the view before getting its controller
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
        });

        //setting up signup button listeners
        view.setActionSignUpButton((ActionEvent event)->{
            //load first the view before getting its controller
            try {
                loader = new FXMLLoader(getClass().getResource("/fxml/client/signup_page.fxml"));
                root = loader.load();

                signUpPageController = new SignUpPageController(loader.getController(), new SignUpPageModel());

                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}

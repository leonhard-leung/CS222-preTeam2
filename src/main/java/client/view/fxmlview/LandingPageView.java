package client.view.fxmlview;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

public class LandingPageView {
    @FXML
    private Button loginButtonFrontPage;
    @FXML
    private Button signupButtonFrontPage;
    @FXML
    private LoginPageView loginPageView;
    @FXML
    private SignUpPageView signUpPageView;

    private FXMLLoader loader;
    private Parent root;

    public void setActionLoginButton(EventHandler<ActionEvent> event){
        loginButtonFrontPage.setOnAction(event);
    }

    public void setActionSignUpButton(EventHandler<ActionEvent> event){
        signupButtonFrontPage.setOnAction(event);
    }

    public void loginFrontPageButtonEntered(){
        loginButtonFrontPage.setStyle("-fx-background-color: #c7a97f;");
    }
    public void loginFrontPageButtonExited(){
        loginButtonFrontPage.setStyle("-fx-background-color:  #A38157;");
    }
    public void signupFrontPageButtonEntered(){
        signupButtonFrontPage.setStyle("-fx-background-color: #c7a97f;");
    }
    public void signupFrontPageButtonExited(){
        signupButtonFrontPage.setStyle("-fx-background-color:  #A38157;");
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
}

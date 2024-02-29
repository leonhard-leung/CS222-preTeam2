package client.view.fxmlview;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginPageView {
    @FXML
    private Button homeButton;

    @FXML
    private Button loginButton;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label noticeLabel;
    private FXMLLoader loader;
    private Parent root;

    /**This method receives action implementation from the login controller*/
    public void setActionHomeButton(EventHandler<ActionEvent> event){
        homeButton.setOnAction(event);
    }

    /**This method receives action implementation from the login controller*/
    public void setActionLoginButton(EventHandler<ActionEvent> event){
        loginButton.setOnAction(event);
    }


    public void loginPageButtonEntered(){
        loginButton.setStyle("-fx-background-color: #c7a97f;");
    }
    public void loginPageButtonExited(){
        loginButton.setStyle("-fx-background-color:  #A38157;");
    }

    public void homePageButtonEntered(){
        homeButton.setStyle("-fx-background-color: #8d5a47;");
    }

    public void homePageButtonExited(){
        homeButton.setStyle("-fx-background-color:  #66382a;");
    }

    public TextField getUsernameTextField() {
        return usernameTextField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public Label getNoticeLabel() {
        return noticeLabel;
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

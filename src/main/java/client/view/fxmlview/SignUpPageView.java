package client.view.fxmlview;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class SignUpPageView {
    @FXML
    private Button homeButton;
    @FXML
    private Button createAccountButton;
    @FXML
    private TextField fullNameTextField;
    @FXML
    private TextField userNameTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private CheckBox termsAndServicesCheckBox;
    @FXML
    private Label noticeLabel;
    private FXMLLoader loader;
    private Parent root;
    private Stage stage;

    public void setActionSignUpButton(EventHandler<ActionEvent> event){
        createAccountButton.setOnAction(event);
    }

    public void setActionHomeButton(EventHandler<ActionEvent> event){
        homeButton.setOnAction(event);
    }


    public void createAccountButtonEntered(){
        createAccountButton.setStyle("-fx-background-color: #c7a97f;");
    }
    public void createAccountButtonExited(){
        createAccountButton.setStyle("-fx-background-color:  #A38157;");
    }

    public void homePageButtonEntered(){
        homeButton.setStyle("-fx-background-color: #8d5a47;");
    }

    public void homePageButtonExited(){
        homeButton.setStyle("-fx-background-color:  #66382a;");
    }

    public TextField getFullNameTextField() {
        return fullNameTextField;
    }

    public TextField getUserNameTextField() {
        return userNameTextField;
    }

    public TextField getAddressTextField() {
        return addressTextField;
    }

    public TextField getEmailTextField() {
        return emailTextField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public CheckBox getTermsAndServicesCheckBox() {
        return termsAndServicesCheckBox;
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

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}

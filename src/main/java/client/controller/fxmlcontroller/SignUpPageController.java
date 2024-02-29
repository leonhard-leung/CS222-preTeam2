package client.controller.fxmlcontroller;

import client.model.fxmlmodel.LoginPageModel;
import client.model.fxmlmodel.SignUpPageModel;
import client.view.fxmlview.SignUpPageView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpPageController {
    private final SignUpPageView signUpView;
    private final SignUpPageModel signUpModel;
    private LandingPageController landingPageController;
    private LoginPageController loginPageController;
    private FXMLLoader loader;
    private Parent root;
    private Object[] serverResponse;

    public SignUpPageController(SignUpPageView signUpView, SignUpPageModel signUpModel){
        this.signUpView = signUpView;
        this.signUpModel = signUpModel;

        //setting up action for login button
        this.signUpView.setActionSignUpButton((ActionEvent event)->{
            String fullName = signUpView.getFullNameTextField().getText();
            String username = signUpView.getUserNameTextField().getText();
            String address = signUpView.getAddressTextField().getText();
            String email = signUpView.getEmailTextField().getText();
            String password = signUpView.getPasswordField().getText();

            if (fullName.isEmpty() || username.isEmpty() || address.isEmpty() || email.isEmpty() || password.isEmpty()){
                signUpView.getNoticeLabel().setText("fill out all details");
                signUpView.getNoticeLabel().setVisible(true);
            }else if (!fullName.isEmpty() && !username.isEmpty() && !address.isEmpty() && !email.isEmpty() && !password.isEmpty() && !signUpView.getTermsAndServicesCheckBox().isSelected()) {
                signUpView.getNoticeLabel().setText("accept terms and policies");
                signUpView.getNoticeLabel().setVisible(true);
            }else {
                signUpView.getNoticeLabel().setVisible(false);
                try {
                    signUpModel.authenticate(fullName, username, address, email, password); //call the model for authentication
                    serverResponse = signUpModel.getServerResponse();

                    //parse the server response
                    parseServerResponse(serverResponse, event);

                } catch (IOException e) { //load a new popup when failed to connect to server
                   showServerErrorUI();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        //setting up action for home button. the button will load the Landing Page
        this.signUpView.setActionHomeButton((ActionEvent event) ->{
            //load the view before getting its controller
            try {
                loader = new FXMLLoader(getClass().getResource("/fxml/client/landing_page.fxml"));
                root = loader.load();

                landingPageController = new LandingPageController(loader.getController());

                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }catch (IOException e){
                e.printStackTrace();
            }
        });
    }

    /**This method parses the response from the server. If Login in successful, load the main menu client page*/
    private void parseServerResponse(Object[] serverResponse, ActionEvent event) {
        //load login UI if successful
        try {
            if (serverResponse[1].equals("SIGN_UP_SUCCESSFUL")){
                loader = new FXMLLoader(getClass().getResource("/fxml/client/login_page.fxml"));
                root = loader.load();

                loginPageController = new LoginPageController(loader.getController(), new LoginPageModel());

                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }else {
                this.signUpView.getNoticeLabel().setText("account exists");
                this.signUpView.getNoticeLabel().setVisible(true);
                this.signUpView.getUserNameTextField().clear();
                this.signUpView.getUserNameTextField().setPromptText("try another one");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**This shows the server error UI*/
    private void showServerErrorUI() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/client/server_error.fxml"));
            Scene serverErrorScene = new Scene(root);
            Stage popUpStage = new Stage();
            popUpStage.setScene(serverErrorScene);
            popUpStage.show();
        }catch (IOException exception){
            exception.printStackTrace();
        }
    }
}

package server.view.misc;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class YesNoPopupView {
    @FXML
    private TextArea questionPromptLabel;
    @FXML
    private Button yesButton;
    @FXML
    private Button noButton;
    private static Stage popupStage;

    public static <T> T loadYesNoPopupView() {
        try {
            FXMLLoader loader = new FXMLLoader(YesNoPopupView.class.getResource("/fxml/server/misc/yes_no_popup.fxml"));
            popupStage = new Stage();
            popupStage.getIcons().add(new Image(YesNoPopupView.class.getResource("/images/server/server_app_logo.png").toExternalForm()));
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setScene(new Scene(loader.load()));
            popupStage.show();
            return loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    } // end of loadYesNoPopupView

    public void closePopupStage() {
        popupStage.close();
    } // end of closePopupStage

    public void setTitle(String value) {
        popupStage.setTitle(value);
    }

    public void setQuestionPromptLabel(String value) {
        questionPromptLabel.setText(value);
    }

    public Button getYesButton() {
        return yesButton;
    }

    public Button getNoButton() {
        return noButton;
    }

    public void setYesButtonMouseEntered() {
        yesButton.setStyle("-fx-background-color: #71d079");
    }

    public void setYesButtonMouseExited() {
        yesButton.setStyle("-fx-background-color: #5dae65");
    }

    public void setNoButtonMouseEntered() {
        noButton.setStyle("-fx-background-color: #d07171");
    }

    public void setNoButtonMouseExited() {
        noButton.setStyle("-fx-background-color: #ae5d5d");
    }
} // end of YesNoPopupController class

package server.view.inventory;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;

public class BeverageEditDetailsPopupView implements Initializable {
    @FXML
    private Label imageLabel;
    @FXML
    private Button acceptButton;
    @FXML
    private Button chooseImageButton;
    @FXML
    private TextField productNameTextField;
    @FXML
    private TextField smallPriceTextField;
    @FXML
    private TextField mediumPriceTextField;
    @FXML
    private TextField largePriceTextField;
    @FXML
    private TextField imageTextField;
    @FXML
    private TextArea productDescriptionTextArea;
    private static Stage popupStage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        smallPriceTextField.setTextFormatter(createNumberTextFormatter());
        mediumPriceTextField.setTextFormatter(createNumberTextFormatter());
        largePriceTextField.setTextFormatter(createNumberTextFormatter());

        imageTextField.setEditable(false);
    } // end of initialize

    public static <T> T loadBeverageDetailsPopup() {
        try {
            FXMLLoader loader = new FXMLLoader(BeverageEditDetailsPopupView.class.getResource("/fxml/server/inventory/beverage_edit_details_popup.fxml"));
            popupStage = new Stage();
            popupStage.getIcons().add(new Image(BeverageEditDetailsPopupView.class.getResource("/images/server/server_app_logo.png").toExternalForm()));
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Edit Beverage Details");
            popupStage.setScene(new Scene(loader.load()));
            popupStage.show();
            return loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    } // end of loadBeverageDetailsPopup

    public void closePopupStage() {
        popupStage.close();
    } // end of closePopupStage

    private TextFormatter<Double> createNumberTextFormatter() {
        NumberFormat format = NumberFormat.getInstance();
        format.setGroupingUsed(false);
        DoubleStringConverter converter = new DoubleStringConverter();
        return new TextFormatter<>(converter, null, c ->
                c.getControlNewText().matches("-?\\d*\\.?\\d*") ? c : null);
    } // end of createNumberTextFormatter

    public void setImageLabel(String value) {
        imageLabel.setText(value);
    }

    public void setProductNameTextField(String value) {
        productNameTextField.setText(value);
    }

    public void setSmallPriceTextField(String value) {
        smallPriceTextField.setText(value);
    }

    public void setMediumPriceTextField(String value) {
        mediumPriceTextField.setText(value);
    }

    public void setLargePriceTextField(String value) {
        largePriceTextField.setText(value);
    }

    public void setProductDescriptionTextArea(String value) {
        productDescriptionTextArea.setText(value);
    }

    public Label getImageLabel() {
        return imageLabel;
    }

    public Button getAcceptButton() {
        return acceptButton;
    }

    public Button getChooseImageButton() {
        return chooseImageButton;
    }

    public TextField getProductNameTextField() {
        return productNameTextField;
    }

    public TextField getSmallPriceTextField() {
        return smallPriceTextField;
    }

    public TextField getMediumPriceTextField() {
        return mediumPriceTextField;
    }

    public TextField getLargePriceTextField() {
        return largePriceTextField;
    }

    public TextField getImageTextField() {
        return imageTextField;
    }

    public TextArea getProductDescriptionTextArea() {
        return productDescriptionTextArea;
    }

    public void setAcceptButtonMouseEntered() {
        acceptButton.setStyle("-fx-background-color: #71d079");
    }

    public void setAcceptButtonMouseExited() {
        acceptButton.setStyle("-fx-background-color: #5dae65");
    }
} // end of BeverageEditDetailsPopupController class

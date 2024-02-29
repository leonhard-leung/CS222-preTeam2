package server.view.inventory;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;

public class FoodEditDetailsPopupView implements Initializable {
    @FXML
    private Label imageLabel;
    @FXML
    private Button acceptButton;
    @FXML
    private Button chooseImageButton;
    @FXML
    private TextField productNameTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextField imageTextField;
    @FXML
    private TextArea productDescriptionTextArea;
    private static Stage popupStage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        priceTextField.setTextFormatter(createNumberTextFormatter());
        imageTextField.setEditable(false);
    } // end if initialize

    public static <T> T loadFoodDetailsPopup() {
        try {
            FXMLLoader loader = new FXMLLoader(FoodEditDetailsPopupView.class.getResource("/fxml/server/inventory/food_edit_details_popup.fxml"));
            popupStage = new Stage();
            popupStage.getIcons().add(new Image(FoodEditDetailsPopupView.class.getResource("/images/server/server_app_logo.png").toExternalForm()));
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Edit Food Details");
            popupStage.setScene(new Scene(loader.load()));
            popupStage.show();
            return loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    } // end of loadFoodDetailsPopup

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

    public void setPriceTextField(String value) {
        priceTextField.setText(value);
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

    public TextField getPriceTextField() {
        return priceTextField;
    }

    public TextField getImageTextField() {
        return imageTextField;
    }

    public TextArea getProductDescriptionTextArea() {
        return productDescriptionTextArea;
    }

    public void chooseImage(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.svg")
        );

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            imageTextField.setText(selectedFile.getAbsolutePath());
        }

        if (imageTextField.getText() == null || imageTextField.getText().isEmpty()) {
            imageLabel.setText("No image chosen");
            imageLabel.setTextFill(Paint.valueOf("RED"));
        } else {
            imageLabel.setText("Image chosen");
            imageLabel.setTextFill(Paint.valueOf("GREEN"));
        }
    } // end of chooseImage

    public void setAcceptButtonMouseEntered() {
        acceptButton.setStyle("-fx-background-color: #71d079");
    }

    public void setAcceptButtonMouseExited() {
        acceptButton.setStyle("-fx-background-color: #5dae65");
    }
} // end of FoodEditDetailsPopupController

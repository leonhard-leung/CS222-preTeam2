package server.controller.inventory;

import javafx.scene.control.Button;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import server.model.inventory.FoodEditDetailsPopupModel;
import server.view.inventory.FoodEditDetailsPopupView;

import java.io.File;

public class FoodEditDetailsPopupController {
    FoodEditDetailsPopupModel model;
    FoodEditDetailsPopupView view;

    public FoodEditDetailsPopupController(FoodEditDetailsPopupModel model, FoodEditDetailsPopupView view) {
        this.model = model;
        this.view = view;
    }

    public void displayContents() {
        view.setProductNameTextField(model.getFood().getName());
        view.setPriceTextField(String.valueOf(model.getFood().getPrice()));
        view.setProductDescriptionTextArea(model.getFood().getDescription());
    } // end of displayContents

    public void setComponentActions() {
        view.getChooseImageButton().setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose Image File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.svg")
            );

            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            File selectedFile = fileChooser.showOpenDialog(stage);

            if (selectedFile != null) {
                view.getImageTextField().setText(selectedFile.getAbsolutePath());
            }

            if (view.getImageTextField().getText() == null || view.getImageTextField().getText().isEmpty()) {
                view.setImageLabel("No image chosen");
                view.getImageLabel().setTextFill(Paint.valueOf("RED"));
            } else {
                view.setImageLabel("Image chosen");
                view.getImageLabel().setTextFill(Paint.valueOf("GREEN"));
            }
        });
    } // end of setComponentActions
} // end of FoodEditDetailsPopupController class

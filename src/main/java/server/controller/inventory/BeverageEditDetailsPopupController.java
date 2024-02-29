package server.controller.inventory;

import javafx.scene.control.Button;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import server.model.inventory.BeverageEditDetailsPopupModel;
import server.view.inventory.BeverageEditDetailsPopupView;
import java.io.File;

public class BeverageEditDetailsPopupController {
    BeverageEditDetailsPopupModel model;
    BeverageEditDetailsPopupView view;

    public BeverageEditDetailsPopupController(BeverageEditDetailsPopupModel model, BeverageEditDetailsPopupView view) {
        this.model = model;
        this.view = view;
    }

    public void displayContents() {
        view.setProductNameTextField(model.getBeverage().getName());
        view.setSmallPriceTextField(String.valueOf(model.getBeverage().getSizePrice().get("small")));
        view.setMediumPriceTextField(String.valueOf(model.getBeverage().getSizePrice().get("medium")));
        view.setLargePriceTextField(String.valueOf(model.getBeverage().getSizePrice().get("large")));
        view.setProductDescriptionTextArea(model.getBeverage().getDescription());
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
} // end of BeverageEditDetailsPopupController class

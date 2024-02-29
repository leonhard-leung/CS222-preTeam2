package server.controller.addproducts;

import javafx.scene.control.Button;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import server.model.addproducts.AddProductPageModel;
import server.view.addproducts.AddProductPageView;
import util.PushNotification;

import java.io.File;

public class AddProductPageController {
    private final AddProductPageModel model;
    private final AddProductPageView view;

    public AddProductPageController(AddProductPageModel model, AddProductPageView view) {
        this.model = model;
        this.view = view;
    }

    public void start() {
        setComponentActions();
    } // end of start

    private void setComponentActions() {
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

        view.getAddProductButton().setOnAction(actionEvent -> {
            boolean type = true;

            if (!view.blankFields()) {
                model.setName(view.getProductNameTextField().getText().trim());
                model.setType('f');
                model.setDescription(view.getProductDescriptionTextArea().getText().trim());
                model.setSQuantity(Integer.parseInt(view.getMainQuantityTextField().getText().trim()));
                model.setSPrice(Double.parseDouble(view.getMainPriceTextField().getText().trim()));
                model.createCopy(view.getImageTextField().getText().trim());

                if (view.getTypeOfProductMenuButton().getText().equalsIgnoreCase("beverage")) {
                    type = false;

                    model.setType('b');
                    model.setMQuantity(Integer.parseInt(view.getMediumQuantityTextField().getText().trim()));
                    model.setLQuantity(Integer.parseInt(view.getLargeQuantityTextField().getText().trim()));
                    model.setMPrice(Double.parseDouble(view.getMediumPriceTextField().getText().trim()));
                    model.setLPrice(Double.parseDouble(view.getLargePriceTextField().getText().trim()));
                }

                model.createProduct(type);

                model.setNewProduct(true);
                model.notifyObservers();
                model.setNewProduct(false);
            } else {
                PushNotification.toastWarn("Missing Product Fields", "Please supply all the needed information" + "\n" + "before adding the product");
            }
        });
    } // end of setComponentActions
} // end of AddProductPageController

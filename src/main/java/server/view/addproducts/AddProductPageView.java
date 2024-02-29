package server.view.addproducts;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;

public class AddProductPageView implements Initializable {
    @FXML
    private TextField productNameTextField;
    @FXML
    private TextArea productDescriptionTextArea;
    @FXML
    private MenuButton typeOfProductMenuButton;
    @FXML
    private MenuItem foodMenuItem;
    @FXML
    private MenuItem beverageMenuItem;
    @FXML
    private TextField mainPriceTextField;
    @FXML
    private TextField mediumPriceTextField;
    @FXML
    private TextField largePriceTextField;
    @FXML
    private TextField mainQuantityTextField;
    @FXML
    private TextField mediumQuantityTextField;
    @FXML
    private TextField largeQuantityTextField;
    @FXML
    private Label mainPriceLabel;
    @FXML
    private Label mediumPriceLabel;
    @FXML
    private Label largePriceLabel;
    @FXML
    private Label mainQuantityLabel;
    @FXML
    private Label mediumQuantityLabel;
    @FXML
    private Label largeQuantityLabel;
    @FXML
    private Label imageLabel;
    @FXML
    private TextField imageTextField;

    @FXML
    private Button addProductButton;
    @FXML
    private Button chooseImageButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //set text fields input to only accept integer and double inputs
        setFormatterForTextFields();
    } // end of initialize

    public static <T> T loadAddProductPage(BorderPane borderPane) {
        try {
            FXMLLoader loader = new FXMLLoader(AddProductPageView.class.getResource("/fxml/server/addproduct/add_product_page.fxml"));
            borderPane.setCenter(loader.load());
            return loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    } // end of loadAddProductPage

    private void setFormatterForTextFields(){
        // Restrict price text fields to accept integers and doubles
        mainPriceTextField.setTextFormatter(createNumberTextFormatter());
        mediumPriceTextField.setTextFormatter(createNumberTextFormatter());
        largePriceTextField.setTextFormatter(createNumberTextFormatter());

        // Restrict quantity text fields to accept only integers
        mainQuantityTextField.setTextFormatter(createIntegerTextFormatter());
        mediumQuantityTextField.setTextFormatter(createIntegerTextFormatter());
        largeQuantityTextField.setTextFormatter(createIntegerTextFormatter());
    } // end of setFormatterForTextFields

    private TextFormatter<Double> createNumberTextFormatter() {
        NumberFormat format = NumberFormat.getInstance();
        format.setGroupingUsed(false); // Disables a thousand separator
        DoubleStringConverter converter = new DoubleStringConverter();
        return new TextFormatter<>(converter, null, c ->
                c.getControlNewText().matches("-?\\d*\\.?\\d*") ? c : null);
    } // end of createNumberTextFormatter

    private TextFormatter<Integer> createIntegerTextFormatter() {
        NumberFormat format = NumberFormat.getIntegerInstance();
        format.setGroupingUsed(false); // Disables a thousand separator
        IntegerStringConverter converter = new IntegerStringConverter();
        return new TextFormatter<>(converter, null, c ->
                c.getControlNewText().matches("\\d*") ? c : null);
    } // end of createIntegerTextFormatter

    public void chooseFoodMenuItem() {
        typeOfProductMenuButton.setText("Food");

        //Price TextFields
        mainPriceTextField.setEditable(true);
        mediumPriceTextField.setEditable(false);
        mediumPriceTextField.setText(null);
        largePriceTextField.setEditable(false);
        largePriceTextField.setText(null);
        //Quantity Text Fields
        mainQuantityTextField.setEditable(true);
        mediumQuantityTextField.setEditable(false);
        mediumQuantityTextField.setText(null);
        largeQuantityTextField.setEditable(false);
        largeQuantityTextField.setText(null);

        //Labels
        mainPriceLabel.setText("Price for Food");
        mediumPriceLabel.setText("...");
        largePriceLabel.setText("...");
        mainQuantityLabel.setText("Quantity for Food");
        mediumQuantityLabel.setText("...");
        largeQuantityLabel.setText("...");
    } // end of chooseFoodMenuItem

    public void chooseBeverageMenuItem() {
        typeOfProductMenuButton.setText("Beverage");

        //TextFields
        mainPriceTextField.setEditable(true);
        mediumPriceTextField.setEditable(true);
        largePriceTextField.setEditable(true);
        mainQuantityTextField.setEditable(true);
        mediumQuantityTextField.setEditable(true);
        largeQuantityTextField.setEditable(true);

        //Labels
        mainPriceLabel.setText("Price for Small");
        mediumPriceLabel.setText("Price for Medium");
        largePriceLabel.setText("Price for Large");
        mainQuantityLabel.setText("Quantity for Small");
        mediumQuantityLabel.setText("Quantity for Medium");
        largeQuantityLabel.setText("Quantity for Large");
    } // end of chooseBeverageMenuItem

    public void addProductButtonEntered() {
        addProductButton.setStyle("-fx-background-color: #634950;");
    }

    public void addProductButtonExited() {
        addProductButton.setStyle("-fx-background-color: #634921;");
    }

    public void setImageLabel(String value) {
        imageLabel.setText(value);
    }

    public Label getImageLabel() {
        return imageLabel;
    }

    public Button getAddProductButton() {
        return addProductButton;
    }

    public Button getChooseImageButton() {
        return chooseImageButton;
    }

    public MenuButton getTypeOfProductMenuButton() {
        return typeOfProductMenuButton;
    }

    public TextField getProductNameTextField() {
        return productNameTextField;
    }

    public TextArea getProductDescriptionTextArea() {
        return productDescriptionTextArea;
    }

    public TextField getMainPriceTextField() {
        return mainPriceTextField;
    }

    public TextField getMediumPriceTextField() {
        return mediumPriceTextField;
    }

    public TextField getLargePriceTextField() {
        return largePriceTextField;
    }

    public TextField getMainQuantityTextField() {
        return mainQuantityTextField;
    }

    public TextField getMediumQuantityTextField() {
        return mediumQuantityTextField;
    }

    public TextField getLargeQuantityTextField() {
        return largeQuantityTextField;
    }

    public TextField getImageTextField() {
        return imageTextField;
    }

    public boolean blankFields() {
        if (typeOfProductMenuButton.getText().equalsIgnoreCase("food")) {
            return (productNameTextField.getText().isEmpty() || productDescriptionTextArea.getText().isEmpty()
                    || mainQuantityTextField.getText().isEmpty() || mainPriceTextField.getText().isEmpty()
                    || imageTextField.getText().isEmpty());
        } else if (typeOfProductMenuButton.getText().equalsIgnoreCase("beverage")) {
            return (productNameTextField.getText().isEmpty() || productDescriptionTextArea.getText().isEmpty()
                    || mainQuantityTextField.getText().isEmpty() || mainPriceTextField.getText().isEmpty()
                    || mediumQuantityTextField.getText() == null || mediumPriceTextField.getText() == null
                    || mediumQuantityTextField.getText().isEmpty() || mediumPriceTextField.getText().isEmpty()
                    || largeQuantityTextField.getText() == null || largePriceTextField.getText() == null
                    || largeQuantityTextField.getText().isEmpty() || largePriceTextField.getText().isEmpty()
                    || imageTextField.getText().isEmpty());
        }
        return true;
    }
} // end of AddProductPageView class


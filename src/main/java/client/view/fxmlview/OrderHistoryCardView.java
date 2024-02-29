package client.view.fxmlview;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class OrderHistoryCardView {

    @FXML
    private Label productDescriptionLabel;
    @FXML
    private Label typeLabel;

    @FXML
    private Pane orderHistoryCard;

    @FXML
    private ImageView productImage;

    @FXML
    private Label productNameLabel;

    @FXML
    private ToggleButton star1;

    @FXML
    private ToggleButton star2;

    @FXML
    private ToggleButton star3;

    @FXML
    private ToggleButton star4;

    @FXML
    private ToggleButton star5;

    @FXML
    private HBox starRating;

    public void setActionStar1(){
        star1.setOnAction(event ->{
            resetStars();
            star1.setSelected(true);
        });
    }
    public void setActionStar2(){
        star2.setOnAction(event ->{
            resetStars();
            star1.setSelected(true);
            star2.setSelected(true);
        });
    }
    public void setActionStar3(){
        star3.setOnAction(event ->{
            resetStars();
            star1.setSelected(true);
            star2.setSelected(true);
            star3.setSelected(true);
        });
    }
    public void setActionStar4(){
        star4.setOnAction(event ->{
            resetStars();
            star1.setSelected(true);
            star2.setSelected(true);
            star3.setSelected(true);
            star4.setSelected(true);
        });
    }
    public void setActionStar5(){
        star5.setOnAction(event ->{
            resetStars();
            star1.setSelected(true);
            star2.setSelected(true);
            star3.setSelected(true);
            star4.setSelected(true);
            star5.setSelected(true);
        });
    }

    /**Resets the stars*/
    private void resetStars() {
        star1.setSelected(false);
        star2.setSelected(false);
        star3.setSelected(false);
        star4.setSelected(false);
        star5.setSelected(false);
    }

    public ImageView getProductImage() {
        return productImage;
    }

    public Label getProductNameLabel() {
        return productNameLabel;
    }

    public ToggleButton getStar1() {
        return star1;
    }

    public ToggleButton getStar2() {
        return star2;
    }

    public ToggleButton getStar3() {
        return star3;
    }

    public ToggleButton getStar4() {
        return star4;
    }

    public ToggleButton getStar5() {
        return star5;
    }

    public Label getProductDescriptionLabel() {
        return productDescriptionLabel;
    }

    public Label getTypeLabel() {
        return typeLabel;
    }
}
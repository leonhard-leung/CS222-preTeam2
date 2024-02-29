package client.controller.fxmlcontroller;

import client.model.fxmlmodel.OrderHistoryCardModel;
import client.view.fxmlview.OrderHistoryCardView;
import shared.Beverage;
import shared.Food;
import shared.Product;

public class OrderHistoryCardController {
    private final OrderHistoryCardView view;
    private final OrderHistoryCardModel model;

    public OrderHistoryCardController(OrderHistoryCardModel model, OrderHistoryCardView view){
        this.model = model;
        this.view = view;
        this.view.setActionStar1();
        this.view.setActionStar2();
        this.view.setActionStar3();
        this.view.setActionStar4();
        this.view.setActionStar5();
    }

    public void setData(){
        Product product = model.getProduct();
        if (product instanceof Food){
            setFoodDataOnCard();
        }else if (product instanceof Beverage){
            setBeverageDataOnCard();
        }
    }

    private void setBeverageDataOnCard() {
        view.getProductNameLabel().setText(this.model.getProduct().getName());
        view.getProductImage().setImage(this.model.getProduct().getImage());
        view.getProductDescriptionLabel().setText(this.model.getProduct().getDescription());

        if (this.model.getProduct() instanceof Beverage){
            view.getTypeLabel().setText("Type: Beverage");
        }
    }

    private void setFoodDataOnCard() {
        view.getProductNameLabel().setText(this.model.getProduct().getName());
        view.getProductImage().setImage(this.model.getProduct().getImage());
        view.getProductDescriptionLabel().setText(this.model.getProduct().getDescription());

        if (this.model.getProduct() instanceof Food){
            view.getTypeLabel().setText("Type: Food");
        }
    }

    public double getProductRating(){
        if (view.getStar5().isSelected()){
            return 5.0;
        } else if (view.getStar4().isSelected() && !view.getStar5().isSelected()) {
            return 4.0;
        }else if (view.getStar3().isSelected() && !view.getStar4().isSelected() && !view.getStar5().isSelected()){
            return 3.0;
        } else if (view.getStar2().isSelected() && !view.getStar3().isSelected() && !view.getStar4().isSelected() && !view.getStar5().isSelected()) {
            return 2.0;
        } else if (view.getStar1().isSelected() && !view.getStar2().isSelected() && !view.getStar3().isSelected() && !view.getStar4().isSelected() && !view.getStar5().isSelected()) {
            return 1;
        }
        return 0;
    }
}

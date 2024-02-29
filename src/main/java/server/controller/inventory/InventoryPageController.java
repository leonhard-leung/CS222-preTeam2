package server.controller.inventory;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import server.controller.misc.YesNoPopupController;
import server.model.inventory.*;
import server.view.misc.YesNoPopupView;
import server.view.inventory.*;
import shared.Beverage;
import shared.Food;
import shared.Product;

import java.util.HashMap;
import java.util.List;

public class InventoryPageController {
    private final InventoryPageModel model;
    private final InventoryPageView view;

    public InventoryPageController(InventoryPageModel model, InventoryPageView view) {
        this.model = model;
        this.view = view;
    }

    public void start() {
        view.populateTableFromMap(model.getFoodList(), model.getBeverageList());
        setComponentActions();
    } // end of start

    private void setComponentActions() {
        setColumns("quantity", view.getEditQuantityColumn(), 1);
        setColumns("details", view.getEditDetailsColumn(), 2);
        setColumns("delete", view.getDeleteProductColumn(), 3);

        view.getSaveChangesButton().setOnAction(actionEvent -> {
            model.updateInventory(view.getProductList());
            model.setInventoryChanges(true);
            model.notifyObservers();
            model.setInventoryChanges(false);
        });
    } // end of setComponentActions

    private Button createButton(String label, int buttonColumn) {
        Button button = new Button(label);
        if (buttonColumn == 1) {
            button.setOnAction(this::handleEditQuantityButtonClick);
        } else if (buttonColumn == 2) {
            button.setOnAction(this::handleEditDetailsButtonClick);
        } else if (buttonColumn == 3) {
            button.setOnAction(this::handleDeleteProductButtonClick);
        }
        return button;
    } // end of createButton

    private void setColumns(String label, TableColumn<Object, Void> columnName, int buttonColumn) {
        Callback<TableColumn<Object, Void>, TableCell<Object, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Object, Void> call(final TableColumn<Object, Void> param) {
                return new TableCell<>() {
                    private final Button button = createButton(label, buttonColumn);

                    {
                        button.setStyle("-fx-background-color:#b59c7a; -fx-text-fill: white;");
                        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #a18a6d; -fx-text-fill: white;"));
                        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #b59c7a; -fx-text-fill: white;"));
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(button);
                            setAlignment(Pos.CENTER);
                        }
                    }
                };
            }
        };
        columnName.setCellFactory(cellFactory);
    } // end of setColumns

    private void handleEditQuantityButtonClick(ActionEvent event) {
        Button button = (Button) event.getSource();
        TableCell<Object, Void> cell = (TableCell<Object, Void>) button.getParent();

        Object product = cell.getTableRow().getItem();

        int filteredIndex = cell.getTableRow().getIndex();
        int originalIndex = view.getFilteredList().getSourceIndex(filteredIndex);

        if (product instanceof Food food) {
            FoodInventoryPopupModel popupModel = new FoodInventoryPopupModel();
            popupModel.setFood(food);

            FoodInventoryPopupView popupView = FoodInventoryPopupView.loadFoodInventoryPopup();

            FoodInventoryPopupController popupController = new FoodInventoryPopupController(popupModel, popupView);
            popupController.displayContents();
            popupController.setComponentAction();

            popupView.getAcceptButton().setOnAction(actionEvent -> {
                int counter = Integer.parseInt(popupView.getQuantity().getText());
                popupModel.updateQuantity(counter);

                if (originalIndex != -1) {
                    if (originalIndex >= 0 && originalIndex < view.getProductList().size()) {
                        view.getProductList().set(originalIndex, food);
                    }
                }
                popupView.closePopupStage();
            });
        } else if (product instanceof Beverage beverage) {
            BeverageInventoryPopupModel popupModel = new BeverageInventoryPopupModel();
            popupModel.setBeverage(beverage);

            BeverageInventoryPopupView popupView = BeverageInventoryPopupView.loadBeverageInventoryPopup();

            BeverageInventoryPopupController popupController = new BeverageInventoryPopupController(popupModel,popupView);
            popupController.displayContents();
            popupController.setComponentAction();

            popupView.getAcceptButton().setOnAction(actionEvent -> {
                int smallCounter = Integer.parseInt(popupView.getSmallQuantity().getText());
                int mediumCounter = Integer.parseInt(popupView.getMediumQuantity().getText());
                int largeCounter = Integer.parseInt(popupView.getLargeQuantity().getText());

                popupModel.updateQuantity(smallCounter, mediumCounter, largeCounter);

                if (originalIndex != -1) {
                    if (originalIndex >= 0 && originalIndex < view.getProductList().size()) {
                        view.getProductList().set(originalIndex, beverage);
                    }
                }
                popupView.closePopupStage();
            });
        }
    } // end of handleEditQuantityButtonClick

    private void handleEditDetailsButtonClick(ActionEvent event) {
        Button button = (Button) event.getSource();
        TableCell<Object, Void> cell = (TableCell<Object, Void>) button.getParent();

        Object product = cell.getTableRow().getItem();

        int filteredIndex = cell.getTableRow().getIndex();
        int originalIndex = view.getFilteredList().getSourceIndex(filteredIndex);

        if (product instanceof Food food) {
            FoodEditDetailsPopupModel popupModel = new FoodEditDetailsPopupModel();
            popupModel.setFood(food);

            FoodEditDetailsPopupView popupView = FoodEditDetailsPopupView.loadFoodDetailsPopup();

            FoodEditDetailsPopupController popupController = new FoodEditDetailsPopupController(popupModel, popupView);
            popupController.displayContents();
            popupController.setComponentActions();

            popupView.getAcceptButton().setOnAction(actionEvent -> {
                popupModel.getFood().setName(popupView.getProductNameTextField().getText().trim());
                popupModel.getFood().setDescription(popupView.getProductDescriptionTextArea().getText().trim());
                popupModel.getFood().setPrice(Double.parseDouble(popupView.getPriceTextField().getText().trim()));

                if (!popupView.getImageTextField().getText().isEmpty()) {
                    popupModel.getFood().setImage(popupModel.processImageChosen(popupView.getImageTextField().getText().trim()));
                }

                if (originalIndex != -1) {
                    if (originalIndex >= 0 && originalIndex < view.getProductList().size()) {
                        view.getProductList().set(originalIndex, food);
                    }
                }
                popupView.closePopupStage();
            });
        } else if (product instanceof Beverage beverage) {
            BeverageEditDetailsPopupModel popupModel = new BeverageEditDetailsPopupModel();
            popupModel.setBeverage(beverage);

            BeverageEditDetailsPopupView popupView = BeverageEditDetailsPopupView.loadBeverageDetailsPopup();

            BeverageEditDetailsPopupController popupController = new BeverageEditDetailsPopupController(popupModel, popupView);
            popupController.displayContents();
            popupController.setComponentActions();

            popupView.getAcceptButton().setOnAction(actionEvent -> {
                popupModel.getBeverage().setName(popupView.getProductNameTextField().getText().trim());
                popupModel.getBeverage().setDescription(popupView.getProductDescriptionTextArea().getText().trim());

                HashMap<String, Double> sizePrice = new HashMap<>();
                sizePrice.put("small", Double.parseDouble(popupView.getSmallPriceTextField().getText().trim()));
                sizePrice.put("medium", Double.parseDouble(popupView.getMediumPriceTextField().getText().trim()));
                sizePrice.put("large", Double.parseDouble(popupView.getLargePriceTextField().getText().trim()));

                popupModel.getBeverage().setSizePrice(sizePrice);

                if (!popupView.getImageTextField().getText().isEmpty()) {
                    popupModel.getBeverage().setImage(popupModel.processImageChosen(popupView.getImageTextField().getText().trim()));
                }

                if (originalIndex != -1) {
                    if (originalIndex >= 0 && originalIndex < view.getProductList().size()) {
                        view.getProductList().set(originalIndex, beverage);
                    }
                }
                popupView.closePopupStage();
            });
        }
    } // end of handleEditDetailsButtonClick

    private void handleDeleteProductButtonClick(ActionEvent event) {
        Button button = (Button) event.getSource();
        TableCell<Object, Void> cell = (TableCell<Object, Void>) button.getParent();

        Object product = cell.getTableRow().getItem();

        int filteredIndex = cell.getTableRow().getIndex();
        int originalIndex = view.getFilteredList().getSourceIndex(filteredIndex);

        YesNoPopupView popupView = YesNoPopupView.loadYesNoPopupView();
        popupView.setTitle("Delete Product");

        YesNoPopupController popupController = new YesNoPopupController(popupView);
        popupController.setQuestionPromptMessage("Are you sure you want to delete this product?");
        popupController.setComponentActions();

        popupView.getYesButton().setOnAction(actionEvent -> {
                if (originalIndex >= 0 && originalIndex < view.getProductList().size()) {
                    view.getProductList().remove(originalIndex);
                    if (product instanceof Food) {
                        model.getFoodList().remove(((Food) product).getName());
                    } else if (product instanceof Beverage) {
                        model.getBeverageList().remove(((Beverage) product).getName());
                    }
                }
            popupView.closePopupStage();
        });
    } // end of handleDeleteProductButtonClick
} // end of InventoryPageController class

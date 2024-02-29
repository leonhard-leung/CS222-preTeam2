package server.controller.accounts;

import server.model.accounts.AccountInformationPopupModel;
import server.view.accounts.AccountInformationPopupView;

public class AccountInformationPopupController {
    private final AccountInformationPopupModel model;
    private final AccountInformationPopupView view;

    public AccountInformationPopupController(AccountInformationPopupModel model, AccountInformationPopupView view) {
        this.model = model;
        this.view = view;
    }

    public void start() {
        loadAccountInformation();
        setComponentActions();
    } // end of start

    public void loadAccountInformation() {
        view.setName(model.getCustomer().getName());
        view.setUsername(model.getCustomer().getUsername());
        view.setAddress(model.getCustomer().getAddress());
        view.setEmail(model.getCustomer().getEmail());
        view.setTotal(String.valueOf(model.getCustomer().getOrderHistory().size()));
        view.populateTableFromList(model.getCustomer().getOrderHistory());
    } // end of loadAccountInformation

    public void setComponentActions() {
        view.getCloseTabButton().setOnAction(actionEvent -> view.closePopupStage());
    } // end of setComponentActions
} // end of AccountInformationPopupController

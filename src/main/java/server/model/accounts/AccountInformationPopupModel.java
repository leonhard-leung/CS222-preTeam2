package server.model.accounts;

import shared.Customer;

public class AccountInformationPopupModel {
    private Customer customer;

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }
} // end of AccountInformationPopupModel

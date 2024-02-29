package server.model.accounts;

import shared.Customer;

import java.util.ArrayList;
import java.util.List;

public class AccountListPageModel {
    private List<Customer> customerAccountList = new ArrayList<>();

    public void setCustomerAccountList(List<Customer> customerAccountList) {
        this.customerAccountList = customerAccountList;
    }

    public List<Customer> getCustomerAccountList() {
        return customerAccountList;
    }
} // end of AccountListPageModel

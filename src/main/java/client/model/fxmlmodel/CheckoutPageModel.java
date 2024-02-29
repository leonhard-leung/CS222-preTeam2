package client.model.fxmlmodel;

import shared.Customer;
import shared.Order;
import shared.Product;

import java.util.List;

public class CheckoutPageModel{
    private Customer customer;
    private final List<Product> cart;
    private final Order orderFromClient;
    private final double subTotal;

    public CheckoutPageModel(Customer customer, List<Product> cart, double subTotal, Order orderFromClient){
        this.customer = customer;
        this.cart = cart;
        this.subTotal = subTotal;
        this.orderFromClient = orderFromClient;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Product> getCart() {
        return cart;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public Order getOrderFromClient() {
        return orderFromClient;
    }

}
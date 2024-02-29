package client.model.fxmlmodel;

import shared.Product;

import java.util.List;

public class OrderHistoryPageModel {
    private final List<Product> productList;
    public OrderHistoryPageModel(List<Product> productList){
        this.productList = productList;
    }

    public List<Product> getProductList() {
        return productList;
    }

}
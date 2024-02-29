package client.model.fxmlmodel;


import shared.Product;

public class CartItemCardModel {
    private final String productName;
    private final int productQuantity;
    private String size;
    private final double totalPrice;

    public CartItemCardModel(Product product, int orderQuantity, double orderTotalPrice, String orderSize){
        productName = product.getName();
        productQuantity = orderQuantity;
        totalPrice = orderTotalPrice;
        size = orderSize;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

}

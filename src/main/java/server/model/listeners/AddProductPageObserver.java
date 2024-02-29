package server.model.listeners;

public interface AddProductPageObserver {
    void notifyNewProduct(boolean newProduct, char type);
} // end of AddProductPageObserver

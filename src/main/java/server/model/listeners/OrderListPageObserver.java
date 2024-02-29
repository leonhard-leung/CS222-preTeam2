package server.model.listeners;

public interface OrderListPageObserver {
    void notifyOrderStatusChange(boolean statusChanges);
} // end of OrderPageObserver interface

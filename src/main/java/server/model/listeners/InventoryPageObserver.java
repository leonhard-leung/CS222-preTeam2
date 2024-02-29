package server.model.listeners;

public interface InventoryPageObserver {
    void notifyInventoryChanges(boolean inventoryChanges);
} // end of InventoryPageObserver

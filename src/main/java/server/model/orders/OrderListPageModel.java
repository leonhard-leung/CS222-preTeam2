package server.model.orders;

import server.model.listeners.OrderListPageObserver;
import shared.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class OrderListPageModel {
    private final AtomicBoolean statusChange = new AtomicBoolean(false);
    private final List<OrderListPageObserver> observers = new ArrayList<>();
    private List<Order> orderList = new ArrayList<>();

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public void setStatusChange(boolean value) {
        statusChange.set(value);
    }

    public void registerObserver(OrderListPageObserver observer) {
        observers.add(observer);
    } // end of registerObserver

    public void notifyObservers() {
        for (OrderListPageObserver observer : observers) {
            observer.notifyOrderStatusChange(statusChange.get());
        }
    } // end of notifyObservers

    public void updateStatus(boolean status, int index) {
        orderList.get(index).setStatus(status);
    }

    public List<Order> getOrderList() {
        return orderList;
    }
} // end of OrderListPageModel class
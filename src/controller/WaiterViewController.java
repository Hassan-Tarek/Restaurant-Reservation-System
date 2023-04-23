package controller;

import model.Order;
import model.Restaurant;
import view.WaiterView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class WaiterViewController {

    private final Restaurant restaurant;
    private final WaiterView waiterView;

    public WaiterViewController() {
        this.restaurant = Restaurant.getInstance();
        this.waiterView = WaiterView.getInstance();
    }

    public ObservableList<Order> getOrderItems() {
        return FXCollections.observableArrayList(restaurant.getOrdersToBeReserved());
    }

    public EventHandler<ActionEvent> deleteOrderFromTable() {
        return event -> {
            Order selectedOrder = waiterView.getOrdersTable().getSelectionModel().getSelectedItem();
            if(selectedOrder != null) {
                int tableNumber = selectedOrder.getTableNumber();
                for (Order order : restaurant.getOrdersToBeReserved()) {
                    if (order.getTableNumber() == tableNumber) {
                        order.setReserved(true);
                        break;
                    }
                }
                // save data
                restaurant.save();

                // update ordersTable
                fillOrdersTableItems();
            }
        };
    }

    private void fillOrdersTableItems() {
        waiterView.getOrdersTable().setItems(getOrderItems());
    }

    public EventHandler<ActionEvent> logout() {
        return event -> saveAndDestroy();
    }

    private void saveAndDestroy() {
        restaurant.save();
        waiterView.getStage().close();
    }

}

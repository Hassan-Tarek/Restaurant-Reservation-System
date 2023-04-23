package controller;

import model.dishes.Dish;
import model.Order;
import model.Restaurant;
import view.CookerView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.util.List;

public class CookerViewController {

    private final Restaurant restaurant;
    private final CookerView cookerView;
    private int lastClickedTableNumber;

    public CookerViewController() {
        this.restaurant = Restaurant.getInstance();
        this.cookerView = CookerView.getInstance();
    }

    public ObservableList<Order> getOrderItems() {
        return FXCollections.observableArrayList(restaurant.getUnPreparedOrders());
    }

    private ObservableList<Dish> getDishItems(int tableNumber) {
        return FXCollections.observableArrayList(restaurant.getUnPreparedDishes(tableNumber));
    }


    private void fillDishesTableItems() {
        Order order = cookerView.getOrdersTable().getSelectionModel().getSelectedItem();
        if (order != null) {
            int tableNumber = order.getTableNumber();
            this.lastClickedTableNumber = tableNumber;
            cookerView.getDishesTable().setItems(getDishItems(tableNumber));
        }
    }

    private void fillOrdersTableItems() {
        cookerView.getOrdersTable().setItems(getOrderItems());
    }

    public EventHandler<ActionEvent> displayOrderDishes() {
        return event -> fillDishesTableItems();
    }

    public EventHandler<ActionEvent> setDishAsPrepared() {
        return event -> {
            Dish selectedDish = cookerView.getDishesTable().getSelectionModel().getSelectedItem();
            if (selectedDish != null) {
                String dishName = selectedDish.getName();
                List<Dish> orderDishes = restaurant.findOrder(lastClickedTableNumber).getUnPreparedDishes();
                for (Dish dish : orderDishes) {
                    if (dish.getName().equals(dishName)) {
                        dish.setPrepared(true);
                        break;
                    }
                }
                // save data into xml file
                restaurant.save();

                // update dishesTableView
                fillDishesTableItems();

                // update ordersTableView
                fillOrdersTableItems();
            }
        };
    }

    public Callback<TableView<Order>, TableRow<Order>> detectedDoubleClicks() {
        return orderTableView -> {
            TableRow<Order> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(isDoubleClicks(event) && !row.isEmpty()) {
                    fillDishesTableItems();
                }
            });

            return row;
        };
    }

    private boolean isDoubleClicks(MouseEvent event) {
        return event.getClickCount() == 2;
    }

    public EventHandler<ActionEvent> logout() {
        return event -> saveAndDestroy();
    }

    private void saveAndDestroy() {
        restaurant.save();
        cookerView.getStage().close();
    }

}
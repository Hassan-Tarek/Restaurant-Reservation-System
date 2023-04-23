package controller;

import model.dishes.Dish;
import model.Order;
import model.Restaurant;
import model.users.Customer;
import view.CustomerView;
import view.MakeOrderView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.util.LinkedList;
import java.util.List;

public class MakeOrderViewController {

    private final Restaurant restaurant;
    private final MakeOrderView makeOrderView;
    private final CustomerView customerView;
    private final List<Dish> selectedDishes;

    public MakeOrderViewController() {
        restaurant = Restaurant.getInstance();
        makeOrderView = MakeOrderView.getInstance();
        customerView = CustomerView.getInstance();
        selectedDishes = new LinkedList<>();
    }

    public EventHandler<ActionEvent> addDish() {
        return event -> {
            if(makeOrderView.getMenuTableView().getSelectionModel().getSelectedItem() != null) {
                this.selectedDishes.add(makeOrderView.getMenuTableView().getSelectionModel().getSelectedItem());

                // update selected dishes tableView items
                makeOrderView.getSelectedDishesTableView().setItems(getTableItems(makeOrderView.getSelectedDishesTableView()));
            }
        };
    }

    public EventHandler<ActionEvent> deleteDish() {
        return event -> {
            if(makeOrderView.getSelectedDishesTableView().getSelectionModel().getSelectedItem() != null) {
                int itemIndex = makeOrderView.getSelectedDishesTableView().getSelectionModel().getSelectedIndex();
                this.selectedDishes.remove(itemIndex);

                // update selected dishes tableView items
                makeOrderView.getSelectedDishesTableView().setItems(getTableItems(makeOrderView.getSelectedDishesTableView()));
            }
        };
    }

    public EventHandler<ActionEvent> makeOrder() {
        return event -> {
            int customerIndex = restaurant.findUser(customerView.getController().getCustomerUsername());
            int customerTableNumber = ((Customer) restaurant.getUsers().get(customerIndex)).getTable().getTableNumber();
            ((Customer) restaurant.getUsers().get(customerIndex)).setOrder(new Order(customerTableNumber, this.selectedDishes, false));
            restaurant.addOrder(((Customer) restaurant.getUsers().get(customerIndex)).getOrder());
            saveAndDestroy();
        };
    }

    public EventHandler<ActionEvent> switchView() {
        return event -> saveAndDestroy();
    }

    public ObservableList<Dish> getTableItems(TableView<Dish> tableView) {
        return switch (tableView.getId()) {
            case "menu" -> FXCollections.observableArrayList(restaurant.getMenu().getDishes());
            case "selected-dishes" -> FXCollections.observableArrayList(this.selectedDishes);
            default -> throw new IllegalStateException("Unexpected value: " + tableView.getId());
        };
    }

    public Callback<TableView<Dish>, TableRow<Dish>> detectedDoubleClicks() {
        return tableViewCallBack -> {
          TableRow<Dish> row = new TableRow<>();
          row.setOnMouseClicked(event -> {
              if(isDoubleClicks(event) && !row.isEmpty()) {
                  this.selectedDishes.add(row.getItem());
              }

              // update selected dishes tableView items
              makeOrderView.getSelectedDishesTableView().setItems(getTableItems(makeOrderView.getSelectedDishesTableView()));
          });

          return row;
        };
    }

    private boolean isDoubleClicks(MouseEvent event) {
        return event.getClickCount() == 2;
    }

    private void saveAndDestroy() {
        restaurant.save();
        makeOrderView.getStage().close();
        customerView.getStage().show();
    }

}

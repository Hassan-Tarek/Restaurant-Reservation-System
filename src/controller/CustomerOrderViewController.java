package controller;

import model.dishes.Dish;
import model.Restaurant;
import model.users.Customer;
import view.CustomerOrderView;
import view.CustomerView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.LinkedList;
import java.util.List;

public class CustomerOrderViewController {

    private final Restaurant restaurant;
    private final CustomerOrderView customerOrderView;
    private final CustomerView customerView;

    public CustomerOrderViewController() {
        restaurant = Restaurant.getInstance();
        customerOrderView = CustomerOrderView.getInstance();
        customerView = CustomerView.getInstance();
    }

    public EventHandler<ActionEvent> switchView() {
        return event -> saveAndDestroyView();
    }

    public String getTotalPrice() {
        int index = restaurant.findUser(customerView.getController().getCustomerUsername());
        boolean hasOrder = checkForCustomerBill(index);
        double totalPrice = hasOrder ? ((Customer) restaurant.getUsers().get(index)).getBill().getTotalPrice() : 0;
        return String.valueOf(totalPrice);
    }

    public boolean checkForCustomerBill(int customerIndex) {
        if(((Customer) restaurant.getUsers().get(customerIndex)).getOrder() != null) {
            if(((Customer) restaurant.getUsers().get(customerIndex)).getBill() == null) {
                ((Customer) restaurant.getUsers().get(customerIndex)).askForBill();
                restaurant.addBill(((Customer) restaurant.getUsers().get(customerIndex)).getBill());
            }
            return true;
        }
        else
            return false;
    }

    public ObservableList<Dish> getTableItems() {
        int index = restaurant.findUser(customerView.getController().getCustomerUsername());
        List<Dish> dishesList = ((Customer) restaurant.getUsers().get(index)).getOrder() == null ?
                new LinkedList<>() : ((Customer) restaurant.getUsers().get(index)).getOrder().getDishes();
        return FXCollections.observableArrayList(dishesList);
    }

    private void saveAndDestroyView() {
        restaurant.save();
        customerOrderView.getStage().close();
        customerView.getStage().show();
    }

}

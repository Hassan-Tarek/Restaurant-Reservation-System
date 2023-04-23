package controller;

import model.Restaurant;
import model.users.Customer;
import view.AlterBox;
import view.CustomerView;
import view.MakeOrderView;
import view.SelectTableView;
import view.CustomerOrderView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class CustomerViewController {

    private final Restaurant restaurant;
    private final CustomerView customerView;
    private String customerUsername;

    public CustomerViewController() {
        this.restaurant = Restaurant.getInstance();
        this.customerView = CustomerView.getInstance();
    }

    public void setCustomerUsername(String username) {
        this.customerUsername = username;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public EventHandler<ActionEvent> switchView(Button button) {
        return event -> {
            switch (button.getId()) {
                case "book-table" -> {
                    customerView.getStage().hide();
                    SelectTableView view = SelectTableView.getInstance();
                    view.buildViewStage();
                }
                case "make-order" -> {
                    int index = restaurant.findUser(customerUsername);
                    if(((Customer) restaurant.getUsers().get(index)).getTable() != null) {
                        MakeOrderView view = MakeOrderView.getInstance();
                        view.buildViewStage();
                    }
                    else {
                        AlterBox.display("ERROR!!", "You Have to Book a Table First!");
                        customerView.getStage().show();
                    }
                }
                case "show-order" -> {
                    customerView.getStage().hide();
                    CustomerOrderView view = CustomerOrderView.getInstance();
                    view.buildViewStage();
                }
            }
        };
    }

    public EventHandler<ActionEvent> logout() {
        return event -> System.exit(0);
    }

}

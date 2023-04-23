package controller;

import model.users.Customer;
import view.CustomerListView;
import view.ManagerView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.util.List;

public class CustomerListViewController {

    private final CustomerListView customerListView;
    private final ManagerView managerView;
    private List<? extends Customer> customerList;

    public CustomerListViewController() {
        this.customerListView = CustomerListView.getInstance();
        this.managerView = ManagerView.getInstance();
    }

    public void setCustomerList(List<? extends Customer> customerList) {
        this.customerList = customerList;
    }

    public ObservableList<Customer> getTableItems() {
        return FXCollections.observableArrayList(customerList);
    }

    public EventHandler<ActionEvent> switchView(Button button) {
        return event -> {
            if(button.getId().equals("back")) {
                customerListView.getStage().close();
                managerView.getStage().show();
            }
        };
    }

}

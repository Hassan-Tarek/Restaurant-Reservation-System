package controller;

import model.Restaurant;
import view.IView;
import view.ManagerView;
import view.BillListView;
import view.CustomerListView;
import view.EmployeeListView;
import view.RaiseSalaryView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ManagerViewController {

    private final Restaurant restaurant;
    private final ManagerView managerView;

    public ManagerViewController() {
        this.restaurant = Restaurant.getInstance();
        this.managerView = ManagerView.getInstance();
    }

    public EventHandler<ActionEvent> switchView(Button button) {
        return event -> {
            switch (button.getId()) {
                case "show-customers" -> {
                    managerView.getStage().hide();
                    CustomerListView customerListView = CustomerListView.getInstance();
                    customerListView.setController();
                    customerListView.getController().setCustomerList(restaurant.getCustomers());
                    customerListView.buildViewStage();
                }
                case "show-employees" -> {
                    managerView.getStage().hide();
                    EmployeeListView employeeListView = EmployeeListView.getInstance();
                    employeeListView.setController();
                    employeeListView.getController().setEmployeeList(restaurant.getEmployees());
                    employeeListView.buildViewStage();
                }
                case "show-bills" -> {
                    managerView.getStage().hide();
                    BillListView billListView = BillListView.getInstance();
                    billListView.buildViewStage();
                }
                case "raise-salary" -> {
                    IView raiseSalaryView = RaiseSalaryView.getInstance();
                    raiseSalaryView.buildViewStage();
                }
                case "logout" -> saveAndDestroy();
            }
        };
    }

    private void saveAndDestroy() {
        restaurant.save();
        managerView.getStage().close();
    }

}

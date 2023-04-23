package controller;

import model.users.Employee;
import view.EmployeeListView;
import view.ManagerView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.util.List;

public class EmployeeListViewController {

    private final EmployeeListView employeeListView;
    private final ManagerView managerView;
    private List<? extends Employee> employeeList;

    public EmployeeListViewController() {
        this.employeeListView = EmployeeListView.getInstance();
        this.managerView = ManagerView.getInstance();
    }

    public void setEmployeeList(List<? extends Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public ObservableList<Employee> getTableItems() {
        return FXCollections.observableArrayList(employeeList);
    }

    public EventHandler<ActionEvent> switchView(Button button) {
        return event -> {
            if(button.getId().equals("back")) {
                employeeListView.getStage().close();
                managerView.getStage().show();
            }
        };
    }

}

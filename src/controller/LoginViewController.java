package controller;

import model.Restaurant;
import model.users.Cooker;
import model.users.Customer;
import model.users.Employee;
import model.users.Manager;
import model.users.User;
import model.users.Waiter;
import view.AlterBox;
import view.CookerView;
import view.CustomerView;
import view.IView;
import view.LoginView;
import view.ManagerView;
import view.RegisterView;
import view.WaiterView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class LoginViewController {

    private final LoginView loginView;
    private final Restaurant restaurant;

    public LoginViewController() {
        this.loginView = LoginView.getInstance();
        this.restaurant = Restaurant.getInstance();
        restaurant.load();
    }

    public EventHandler<ActionEvent> switchStage(Button button) {
        return event -> {
            if (button.getId().equals("log")) {
                String username = loginView.getUsernameTextField().getText().trim();
                String password = loginView.getPasswordTextField().getText().trim();

                int index = checkValidUser(username, password);
                if (index != -1) {
                    // close the login view
                    loginView.getStage().close();

                    User user = restaurant.getUsers().get(index);
                    if (user instanceof Employee) {
                        Employee employee = (Employee) user;
                        if (employee instanceof Manager) {
                            IView managerView = ManagerView.getInstance();
                            managerView.buildViewStage();
                        } else if (employee instanceof Cooker) {
                            IView cookerView = CookerView.getInstance();
                            cookerView.buildViewStage();
                        } else if (employee instanceof Waiter) {
                            IView waiterView = WaiterView.getInstance();
                            waiterView.buildViewStage();
                        }
                    } else if (user instanceof Customer) {
                        CustomerView customerView = CustomerView.getInstance();
                        customerView.buildViewStage();
                        customerView.getController().setCustomerUsername(username);
                    }
                } else {
                    // display the AlterBox
                    AlterBox.display("ERROR!", "Enter a Valid Username and Password!");
                }
            } else if (button.getId().equals("reg")) {
                // show the register stage
                IView registerView = RegisterView.getInstance();
                registerView.buildViewStage();
            }
        };
    }

    private int checkValidUser(String username, String password) {
        return restaurant.findUser(username, password);
    }

}
